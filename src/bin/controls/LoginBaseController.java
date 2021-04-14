package bin.controls;

import bin.Launcher;
import bin.controls.funcs.Anim.Select;
import bin.controls.funcs.Anim.Shake;
import bin.controls.funcs.log;
import bin.data.Config;
import bin.data.Const;
import bin.data.Models.User;
import bin.data.dao.UserDao;
import bin.data.dao.daoimpl.UserDatabaseDao;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class LoginBaseController extends LoginUserController {
    UserDao userdb = new UserDatabaseDao();
    User user;
    @FXML
    private PasswordField FieldPass;
    @FXML
    private Button ButtonLogin, ButtonClose;
    @FXML
    private Pane PaneTop;


    Select selecting = new Select();

    @FXML
    void initialize() {
        selecting.getAnim(ButtonLogin);
        selecting.getAnim(ButtonClose);

        ButtonLogin.setOnMouseClicked(event -> LogIn(FieldPass) );
        ButtonLogin.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)) {
                    LogIn(FieldPass);
                }
            }
        });
        FieldPass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    LogIn(FieldPass);
                }
            }
        });

        ButtonClose.setOnMouseClicked(event -> Launcher.getStage().hide() );
    }

    private void LogIn(PasswordField passwordField) {
        if(!passwordField.getText().equals("")){
            log lg = new log(passwordField.getText());
            Config config = Config.getInstance();
            config.dbPass = lg.GetPass();

            try {
                Connection connection = new UserDatabaseDao().getDbConnection();

                PreparedStatement preparedStatement =  connection.prepareStatement("SELECT * FROM " + Const.USERS_TABLE + " WHERE " + Const.USERS_PASSWORD + "=?");
                preparedStatement.setString(1, config.dbPass);
                preparedStatement.executeQuery();

                ButtonLogin.getScene().getWindow().hide();
                OpenNewScene("/bin/View/LoginUserUI.fxml");
            } catch (Exception e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setContentText("Wrong password");
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.initModality(Modality.APPLICATION_MODAL);

                alert.show();
            }
        } else {
            System.out.println("Error: Enter password");
            Shake BasePassAnim = new Shake(FieldPass);
            BasePassAnim.playAnim();
        }
    }

    public void OpenNewScene(String Window)
    {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource(Window));

            try {
                loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Parent root = loader.getRoot();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Confirm access");
            stage.setResizable(false);
            stage.initStyle(StageStyle.UNDECORATED);
            stage.getIcons().add(new Image("/bin/interpol.png"));
            stage.centerOnScreen();

            LoginUserController.stage = stage;

            stage.show();
    }
}