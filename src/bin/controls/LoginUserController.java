package bin.controls;

import bin.controls.funcs.Anim.Select;
import bin.controls.funcs.Anim.Shake;
import bin.controls.funcs.log;
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
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class LoginUserController extends About {
    public static User user;
    UserDao userdb = new UserDatabaseDao();
    @FXML
    private TextField FieldUserLogin;
    @FXML
    private PasswordField FieldUserPass;
    @FXML
    private Button ButtonLogin, ButtonClose;
    @FXML
    private AnchorPane AnchorPane1;

    Select selecting = new Select();
    public static Stage stage;
    private double xOffset;
    private double yOffset;

    @FXML
    void initialize() {

        AnchorPane1.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });

        AnchorPane1.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });

        AnchorPane1.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ESCAPE)){
                     AnchorPane1.getScene().getWindow().hide();
                     OpenNewScene("/bin/View/LoginBaseUI.fxml", "Interpol connect");
                }
            }
        });

        selecting.getAnim(ButtonLogin);
        ButtonLogin.setOnMouseClicked(event -> LoginUser(FieldUserLogin, FieldUserPass));
        FieldUserPass.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    LoginUser(FieldUserLogin, FieldUserPass);
                }
            }
        });
        FieldUserLogin.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if (event.getCode().equals(KeyCode.ENTER)){
                    LoginUser(FieldUserLogin, FieldUserPass);
                }
            }
        });

        selecting.getAnim(ButtonClose);
        ButtonClose.setOnMouseClicked(event -> AnchorPane1.getScene().getWindow().hide() );
    }

    private void LoginUser(TextField loginField, PasswordField passwordField){
        if(!FieldUserLogin.getText().equals("") && !FieldUserPass.getText().equals("")){
            user = new User();
            user.setLogin(FieldUserLogin.getText());
            log pas = new log(passwordField.getText());
            user.setPassword(pas.GetPass());

            user = userdb.getByLogin(user).build();
            if (user.getLastname()!=null || user.getFirstname()!=null) {
                ButtonLogin.getScene().getWindow().hide();
                OpenNewScene("/bin/View/AboutUI.fxml", "About Interpol");
            } else {
                Shake userLogAnim = new Shake(FieldUserLogin);
                Shake userPassAnim = new Shake(FieldUserPass);
                userLogAnim.playAnim();
                userPassAnim.playAnim();

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("Wrong login/password");
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.initModality(Modality.APPLICATION_MODAL);

                alert.show();
            }

        } else System.out.println("Error: Enter login/password");
    }

    public void OpenNewScene(String Window, String Title)
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
            stage.setScene(new Scene(root));
            stage.setTitle(Title);
            stage.setResizable(false);
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.getIcons().add(new Image("/bin/interpol.png"));
            stage.centerOnScreen();

            About.stage = stage;

            stage.show();
    }
}