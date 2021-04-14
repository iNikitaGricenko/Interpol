package bin;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Launcher extends Application {
    private static Stage primaryStage;
    private void setPrimaryStage(Stage stage){
        Launcher.primaryStage = stage;
    }
    public static Stage getStage()
    {
        return primaryStage;
    }
    private double xOffset;
    private double yOffset;

    @Override
    public void start(Stage primaryStage) throws Exception{
        setPrimaryStage(primaryStage);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("View/LoginBaseUI.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root, 513, 314);
        final int[] counter = {0};

        root.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ESCAPE)){
                    primaryStage.hide();
                }
                else if(event.getCode().equals(KeyCode.F)) {
                    switch (counter[0]) {
                        case 0:
                            primaryStage.setWidth(1026);
                            primaryStage.setHeight(704);
                            counter[0]++;
                            break;
                        case 1:
                            primaryStage.setWidth(513);
                            primaryStage.setHeight(352);
                            counter[0]--;
                            break;
                    }
                }
            }
        });
        scene.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = primaryStage.getX() - event.getScreenX();
                yOffset = primaryStage.getY() - event.getScreenY();
            }
        });
        scene.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                primaryStage.setX(event.getScreenX() + xOffset);
                primaryStage.setY(event.getScreenY() + yOffset);
            }
        });

        primaryStage.setTitle("Interpol connect");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.resizableProperty();
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.getIcons().add(new Image("/bin/interpol.png"));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}


