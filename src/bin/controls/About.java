package bin.controls;

import bin.controls.funcs.Stager;
import javafx.animation.FadeTransition;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class About {
    @FXML
    private Text ImagineYour, OurFullName, WhatWeDo, LongText, ShortText;

    public static String title1, title2, title3, title4, title5;

    @FXML
    private Button ButtonClose, ButtonHidden;
    @FXML
    private MenuButton MenuButtonNavigation;
    MenuItem AddCrime = new MenuItem(null),
            AddGrouping = new MenuItem(null),
            GrSearch = new MenuItem(null),
            CrSearch = new MenuItem(null);
    @FXML
    private MenuButton MenuLanguage;
    MenuItem ENG = new MenuItem("ENG");
    MenuItem RU = new MenuItem("RU");
    MenuItem UA = new MenuItem("UKR");
    @FXML
    private GridPane Grid;
    public static Stage stage = new Stage();
    private double xOffset, yOffset;
    
    Preferences preferences = new Preferences() {
            @Override
            public void Localization() throws IOException{
            ImagineYour.setText(processFilesFromFolder(LanguageFolder, "ImagineYour1", Preferences.lang)+"\n"+processFilesFromFolder(LanguageFolder, "ImagineYour2", Preferences.lang));
            OurFullName.setText(processFilesFromFolder(LanguageFolder, "OurFullName1", Preferences.lang)+"\n\n"+processFilesFromFolder(LanguageFolder, "OurFullName2", Preferences.lang));
            LongText.setText(processFilesFromFolder(LanguageFolder, "LongText1", Preferences.lang)+"\n\n"+processFilesFromFolder(LanguageFolder, "LongText2", Preferences.lang)+"\n\n"+processFilesFromFolder(LanguageFolder, "LongText3", Preferences.lang));
            ShortText.setText("\n\n"+processFilesFromFolder(LanguageFolder, "ShortText", Preferences.lang)+"\n\n");
            WhatWeDo.setText(processFilesFromFolder(LanguageFolder, "WhatWeDo", Preferences.lang));
            MenuButtonNavigation.setText(processFilesFromFolder(LanguageFolder, "Navigation", Preferences.lang));
            AddCrime.setText(processFilesFromFolder(LanguageFolder, "AddCrime", Preferences.lang));
            AddGrouping.setText(processFilesFromFolder(LanguageFolder, "AddGrouping", Preferences.lang));
            GrSearch.setText(processFilesFromFolder(LanguageFolder, "GroupingSearch", Preferences.lang));
            CrSearch.setText(processFilesFromFolder(LanguageFolder, "CrimeSearch", Preferences.lang));
            title1 = processFilesFromFolder(LanguageFolder, "AddCrime", Preferences.lang);
            title2 = processFilesFromFolder(LanguageFolder, "SearchForCrime", Preferences.lang);
            title3 = processFilesFromFolder(LanguageFolder, "SearchForGrouping", Preferences.lang);
            title4 = processFilesFromFolder(LanguageFolder, "AddGrouping", Preferences.lang);
            title5 = processFilesFromFolder(LanguageFolder, "AboutUs", Preferences.lang);
            MenuLanguage.setText(Preferences.lang.toString());
        }
    };

    @FXML
    void initialize() {
        try {
            preferences.Localization();
        } catch (IOException e) {
            e.printStackTrace();
        }

        MenuLanguage.getItems().remove(0, 2);
        MenuLanguage.getItems().add(0, ENG);
        MenuLanguage.getItems().add(1, RU);
        MenuLanguage.getItems().add(2, UA);
        MenuButtonNavigation.getItems().remove(0, 2);
        MenuButtonNavigation.getItems().add(0, GrSearch);
        MenuButtonNavigation.getItems().add(1, CrSearch);
        MenuButtonNavigation.getItems().add(2, AddCrime);
        MenuButtonNavigation.getItems().add(3, AddGrouping);

        AddCrime.setOnAction(event -> OpenNewScene(this.MenuButtonNavigation, "/bin/View/InterpolAddCrimeUI.fxml", title1, "Add crime"));
        CrSearch.setOnAction(event -> OpenNewScene(this.MenuButtonNavigation, "/bin/View/InterpolSearchCrimeUI.fxml",  title2, "Crime search"));
        GrSearch.setOnAction(event -> OpenNewScene(this.MenuButtonNavigation, "/bin/View/InterpolSearchGroupingUI.fxml", title3, "Grouping search"));
        AddGrouping.setOnAction(event -> OpenNewScene(this.MenuButtonNavigation, "/bin/View/InterpolAddGroupingUI.fxml", title4, "Add grouping"));
        selectedSkin(MenuLanguage);
        ENG.setOnAction(event -> {
            Preferences.lang = Preferences.Language.ENG;
            MenuLanguage.setText(ENG.getText());
            initialize();
        });
        RU.setOnAction(event -> {
            Preferences.lang = Preferences.Language.RU;
            MenuLanguage.setText(RU.getText());
            initialize();
        });
        UA.setOnAction(event -> {
            Preferences.lang = Preferences.Language.UA;
            MenuLanguage.setText(UA.getText());
            initialize();
        });
        selectedSkin(MenuButtonNavigation);
        MenuButtonNavigation.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ESCAPE))
                {
                    OpenNewScene(MenuButtonNavigation, "/bin/View/LoginBaseUI.fxml", "Interpol connect", null);
                }
            }
        });
        ButtonClose.setOnMouseClicked(event -> Grid.getScene().getWindow().hide() );
        ButtonHidden.setOnMouseClicked(event -> stage.setIconified(true) );
        Grid.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        Grid.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
    }

    void selectedSkin(MenuButton button)
    {
        final FadeTransition fadeIn = new FadeTransition(Duration.millis(100));
        fadeIn.setNode(button);
        fadeIn.setToValue(1);
        final FadeTransition fadeOut = new FadeTransition(Duration.millis(100));
        fadeOut.setNode(button);
        fadeOut.setToValue(0.85);

        button.setOnMouseEntered(e -> fadeIn.playFromStart());
        button.setOnMouseExited(e -> fadeOut.playFromStart());

        button.setOpacity(0.85);
    }

    public void OpenNewScene(MenuButton menuButton, String Window, String Title, String key)
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

            Stager.stager.forwarder(key, stage);

            menuButton.getScene().getWindow().hide();
            stage.show();
    }
}
