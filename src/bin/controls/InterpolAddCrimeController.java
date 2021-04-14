package bin.controls;

import bin.controls.funcs.Anim.Select;
import bin.controls.funcs.Stager;
import bin.data.Models.Crime;
import bin.data.dao.CrimeDao;
import bin.data.dao.daoimpl.CrimeDatabaseDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;

public class InterpolAddCrimeController {
    Crime crime;
    CrimeDao crimedb = new CrimeDatabaseDao();
    @FXML
    private Text TextMain, TextLName, TextFName, TextNation, TextAlias, TextBirth, TextCity, TextHeight, TextSex, TextEye, TextHair, TextWanted, TextAbout, TextGrouping;
    @FXML
    private TextField FieldLastname,
            FieldForename,
            FieldAlias,
            FieldCityBirth,
            FieldGrouping;
    @FXML
    private TextArea AreaAboutText;
    @FXML
    private DatePicker DatePickerBirthday;
    @FXML
    private Spinner<Integer> SpinnerHeight;
    SpinnerValueFactory<Integer> gradesValueHeight = new SpinnerValueFactory.IntegerSpinnerValueFactory(50,200);
    @FXML
    private ChoiceBox<String> ChoiceBoxNational,
            ChoiceBoxSex,
            ChoiceBoxEyes,
            ChoiceBoxHair,
            ChoiceBoxCountry;

    private String Ukrainian, Russian, Belarussian;
    private String Ukrain, Russia, Belarussia, USA, Israel, Canada, Moldova, Latvia, Romania;
    private String Male, Female;
    private String Blue, LightBlue, Grey, Amber, Bolotny, Brown, Black, Yellow, Dark, Red, White;
    ObservableList<String> NationalList = FXCollections.observableArrayList();
    ObservableList<String> SexList = FXCollections.observableArrayList();
    ObservableList<String> ColourEyesList = FXCollections.observableArrayList();
    ObservableList<String> ColourHairList = FXCollections.observableArrayList();
    ObservableList<String> CountryList = FXCollections.observableArrayList();

    @FXML
    private Button ButtonAboutInterpol,
            ButtonFindCrime,
            ButtonFindGroup,
            ButtonAddGroup,
            ButtonEnter,
            ButtonClose,
            ButtonHidden,
            ButtonAddCrime,
            ButtonUpdate;
    @FXML
    private AnchorPane AnchorPaneMidle;

    Select selecting = new Select();
    private double xOffset, yOffset;
    public static Stage stage = new Stage();

    Preferences preferences = new Preferences() {
            @Override
            public void Localization() throws IOException {
            Ukrainian = processFilesFromFolder(LanguageFolder, "Ukrainian", Preferences.lang);
            Russian = processFilesFromFolder(LanguageFolder, "Russian", Preferences.lang);
            Belarussian = processFilesFromFolder(LanguageFolder, "Belarussian", Preferences.lang);
            Ukrain = processFilesFromFolder(LanguageFolder, "Ukrain", Preferences.lang);
            Russia = processFilesFromFolder(LanguageFolder, "Russia", Preferences.lang);
            Belarussia = processFilesFromFolder(LanguageFolder, "Belarussia", Preferences.lang);
            USA = processFilesFromFolder(LanguageFolder, "USA", Preferences.lang);
            Israel = processFilesFromFolder(LanguageFolder, "Israel", Preferences.lang);
            Canada = processFilesFromFolder(LanguageFolder, "Canada", Preferences.lang);
            Moldova = processFilesFromFolder(LanguageFolder, "Moldova", Preferences.lang);
            Latvia = processFilesFromFolder(LanguageFolder, "Latvia", Preferences.lang);
            Romania = processFilesFromFolder(LanguageFolder, "Romania", Preferences.lang);
            Male = processFilesFromFolder(LanguageFolder, "Male", Preferences.lang);
            Female = processFilesFromFolder(LanguageFolder, "Female", Preferences.lang);
            Blue = processFilesFromFolder(LanguageFolder, "Blue", Preferences.lang);
            LightBlue = processFilesFromFolder(LanguageFolder, "Light Blue", Preferences.lang);
            Grey = processFilesFromFolder(LanguageFolder, "Grey", Preferences.lang);
            Amber = processFilesFromFolder(LanguageFolder, "Amber", Preferences.lang);
            Bolotny = processFilesFromFolder(LanguageFolder, "Bolotny", Preferences.lang);
            Brown = processFilesFromFolder(LanguageFolder, "Brown", Preferences.lang);
            Black = processFilesFromFolder(LanguageFolder, "Black", Preferences.lang);
            Yellow = processFilesFromFolder(LanguageFolder, "Yellow", Preferences.lang);
            Dark = processFilesFromFolder(LanguageFolder, "Dark", Preferences.lang);
            Red = processFilesFromFolder(LanguageFolder, "Red", Preferences.lang);
            White = processFilesFromFolder(LanguageFolder, "White", Preferences.lang);
            TextLName.setText("* "+processFilesFromFolder(LanguageFolder, "Lastname", Preferences.lang)+" :");
            TextFName.setText("* "+processFilesFromFolder(LanguageFolder, "Firstname", Preferences.lang)+" :");
            TextNation.setText("* "+processFilesFromFolder(LanguageFolder, "Nationality", Preferences.lang)+" :");
            TextAlias.setText("* "+processFilesFromFolder(LanguageFolder, "Alias", Preferences.lang)+" :");
            TextBirth.setText(processFilesFromFolder(LanguageFolder, "Birthday", Preferences.lang)+" :");
            TextCity.setText(processFilesFromFolder(LanguageFolder, "CityOfBirth", Preferences.lang)+" :");
            TextHeight.setText(processFilesFromFolder(LanguageFolder, "HeightCentimeters", Preferences.lang)+" :");
            TextSex.setText(processFilesFromFolder(LanguageFolder, "Sex", Preferences.lang)+" :");
            TextEye.setText(processFilesFromFolder(LanguageFolder, "ColorOfEye", Preferences.lang)+" :");
            TextHair.setText(processFilesFromFolder(LanguageFolder, "ColorOfHair", Preferences.lang)+" :");
            TextWanted.setText(processFilesFromFolder(LanguageFolder, "WantedBy", Preferences.lang)+" :");
            TextAbout.setText(processFilesFromFolder(LanguageFolder, "AboutPerson", Preferences.lang)+" :");
            ButtonEnter.setText(processFilesFromFolder(LanguageFolder, "Enter", Preferences.lang));
            ButtonUpdate.setText(processFilesFromFolder(LanguageFolder, "Update", Preferences.lang));
            TextGrouping.setText(processFilesFromFolder(LanguageFolder, "Grouping", Preferences.lang)+" :");
        }
    };

    @FXML
    void initialize() {
        try {
            preferences.Localization();
        } catch (IOException e) {
            e.printStackTrace();
        }
        TextMain.setText(About.title1);

        ColourEyesList.add(Blue); ColourEyesList.add(LightBlue); ColourEyesList.add(Grey);
                ColourEyesList.add(Amber); ColourEyesList.add(Bolotny); ColourEyesList.add(Brown);
                ColourEyesList.add(Black); ColourEyesList.add(Yellow); ColourEyesList.add(Dark);
                ColourEyesList.add(Red); ColourEyesList.add(White);
        ColourHairList.add(Blue); ColourHairList.add(LightBlue); ColourHairList.add(Grey);
                ColourHairList.add(Amber); ColourHairList.add(Bolotny); ColourHairList.add(Brown);
                ColourHairList.add(Black); ColourHairList.add(Yellow); ColourHairList.add(Dark);
                ColourHairList.add(Red); ColourHairList.add(White);
        CountryList.add(Ukrain); CountryList.add(Russia); CountryList.add(Belarussia);
                CountryList.add(USA); CountryList.add(Israel); CountryList.add(Canada);
                CountryList.add(Moldova); CountryList.add(Latvia);
        SexList.add(Male); SexList.add(Female);
        NationalList.add(Ukrainian); NationalList.add(Russian); NationalList.add(Belarussian);

        ChoiceBoxNational.setItems(NationalList);
        ChoiceBoxCountry.setItems(CountryList);
        ChoiceBoxEyes.setItems(ColourEyesList);
        ChoiceBoxHair.setItems(ColourHairList);
        ChoiceBoxSex.setItems(SexList);

        SpinnerHeight.setValueFactory(gradesValueHeight);

        selecting.getAnim(ButtonAddGroup);
        selecting.getAnim(ButtonFindCrime);
        selecting.getAnim(ButtonFindGroup);
        selecting.getAnim(ButtonEnter);
        selecting.getAnim(ButtonAboutInterpol);
        selecting.getAnim(ButtonUpdate);

        ButtonAddCrime.setText(About.title1);
        ButtonFindCrime.setText(About.title2);
        ButtonFindGroup.setText(About.title3);
        ButtonAddGroup.setText(About.title4);
        ButtonAboutInterpol.setText(About.title5);

        ButtonAddCrime.setOnMouseClicked(event -> OpenNewScene(ButtonAddCrime, "/bin/View/InterpolAddCrimeUI.fxml", About.title1, "Add crime"));
        ButtonFindCrime.setOnMouseClicked(event -> OpenNewScene(ButtonFindCrime , "/bin/View/InterpolSearchCrimeUI.fxml", About.title2, "Crime search"));
        ButtonFindGroup.setOnMouseClicked(event -> OpenNewScene(ButtonFindGroup , "/bin/View/InterpolSearchGroupingUI.fxml", About.title3, "Grouping search"));
        ButtonAddGroup.setOnMouseClicked(event -> OpenNewScene(ButtonAddGroup , "/bin/View/InterpolAddGroupingUI.fxml", About.title4, "Add grouping"));
        ButtonAboutInterpol.setOnMouseClicked(event -> OpenNewScene(ButtonAboutInterpol , "/bin/View/AboutUI.fxml", About.title5, "About"));
        ButtonEnter.setOnAction(event -> AddCrime());
        ButtonUpdate.setOnAction(event -> UpdateCrime());
        ButtonClose.setOnMouseClicked(event -> ButtonClose.getScene().getWindow().hide() );
        ButtonHidden.setOnMouseClicked(event -> stage.setIconified(true) );
        AnchorPaneMidle.setOnMousePressed(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                xOffset = stage.getX() - event.getScreenX();
                yOffset = stage.getY() - event.getScreenY();
            }
        });
        AnchorPaneMidle.setOnMouseDragged(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                stage.setX(event.getScreenX() + xOffset);
                stage.setY(event.getScreenY() + yOffset);
            }
        });
    }

    public void UpdateCrime(){
        crime = new Crime();
        crime.setLastname(FieldLastname.getText())
                .setFirstname(FieldForename.getText())
                .setNationality(ChoiceBoxNational.getValue())
                .setAlias(FieldAlias.getText())
                .setAbout(AreaAboutText.getText())
                .setBirthday(Date.valueOf(DatePickerBirthday.getValue().toString()))
                .setCountry(FieldCityBirth.getText())
                .setHeight(SpinnerHeight.getValue())
                .setEye(ChoiceBoxEyes.getValue())
                .setHair(ChoiceBoxHair.getValue())
                .setWantedBy(ChoiceBoxCountry.getValue())
                .setSex(ChoiceBoxSex.getValue())
                .setGrouping(FieldGrouping.getText());

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Status");
        alert.setHeaderText(null);
        alert.setContentText(null);
        alert.initModality(Modality.APPLICATION_MODAL);

        sqlUpdateThread sqlupdate = new sqlUpdateThread();
        try {
            sqlupdate.start();
            sqlupdate.join();
            alert.setContentText("Updated successful");
            alert.setAlertType(Alert.AlertType.INFORMATION);
        } catch (InterruptedException e) {
            e.printStackTrace();
            alert.setContentText("Updated failed");
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        finally {
            alert.show();
        }
    }
    public void AddCrime()
    {
        crime = new Crime();
        crime.setLastname(FieldLastname.getText())
                .setFirstname(FieldForename.getText())
                .setNationality(ChoiceBoxNational.getValue())
                .setAlias(FieldAlias.getText())
                .setAbout(AreaAboutText.getText())
                .setCountry(FieldCityBirth.getText())
                .setHeight(SpinnerHeight.getValue())
                .setEye(ChoiceBoxEyes.getValue())
                .setHair(ChoiceBoxHair.getValue())
                .setWantedBy(ChoiceBoxCountry.getValue())
                .setSex(ChoiceBoxSex.getValue())
                .setGrouping(FieldGrouping.getText());
        try {
            crime.setBirthday(Date.valueOf(DatePickerBirthday.getValue().toString()));
        } catch (NullPointerException nullPointerException) {
            crime.setBirthday(Date.valueOf("1111-11-11"));
        }

        Alert alert = new Alert(Alert.AlertType.NONE);
        alert.setTitle("Status");
        alert.setHeaderText(null);
        alert.setContentText(null);
        alert.initModality(Modality.APPLICATION_MODAL);

        sqlAddThread sqladd = new sqlAddThread();
        try {
            sqladd.start();
            sqladd.join();
            alert.setContentText("Adding successful");
            alert.setAlertType(Alert.AlertType.INFORMATION);
            alert.show();
        } catch (InterruptedException e) {
            e.printStackTrace();
            alert.setContentText("Adding failed");
            alert.setAlertType(Alert.AlertType.ERROR);
            alert.show();
        }
    }

    class sqlAddThread extends Thread {
        @Override
        public void run() {
            try {
                crimedb.add(crime);
            } catch (SQLException sqlException) {
                sqlException.printStackTrace();
            }

        }
    }
    class sqlUpdateThread extends Thread{
        @Override
        public void run() {
            crimedb.update(crime);
        }
    }

    public void OpenNewScene(Button button, String Window, String Title, String key)
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
            stage.getIcons().add(new Image("/bin/interpol.png"));
            stage.initStyle(StageStyle.TRANSPARENT);
            stage.centerOnScreen();

            Stager.stager.forwarder(key, stage);

            button.getScene().getWindow().hide();
            stage.show();
    }
}
