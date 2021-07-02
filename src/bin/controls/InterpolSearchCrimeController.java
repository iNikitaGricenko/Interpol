package bin.controls;

import bin.controls.funcs.Anim.Select;
import bin.controls.funcs.Anim.Shake;
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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InterpolSearchCrimeController {
    Crime crime;
    CrimeDao crimedb = new CrimeDatabaseDao();
    @FXML
    private TextField FieldLastname, FieldForename;
    @FXML
    private Text TextMain, TextCounterAge;
    @FXML
    private Text LastName, FirstName, Nationality, Sex, ColorEye, ColorHair, Wanted, CurrentAge,
    TextSLName, TextSFName, TextSHair, TextSEyes, TextSBirth, TextSNation, TextSSex, TextSWanted, TextSCity, TextSAlias, TextSHeight, TextSAbout;
    @FXML
    private Label LabelLastName,
            LabelBirthDate,
            LabelNationality,
            LabelForeName,
            LabelCity,
            LabelAlias,
            LabelSex,
            LabelHeight,
            LabelEye,
            LabelHair,
            LabelWantedBy,
            LabelAbout;
    @FXML
    private ImageView PersonLocked;
    @FXML
    private Slider SliderAge;
    @FXML
    private ChoiceBox<String> ChoiceBoxNational,
            ChoiceBoxSex,
            ChoiceBoxEyes,
            ChoiceBoxHair,
            ChoiceBoxCountry;
    ObservableList<String> NationalList = FXCollections.observableArrayList("Ukrainian", "Russian", "Belarussian");
    ObservableList<String> SexList = FXCollections.observableArrayList("Male","Female");
    ObservableList<String> ColourEyesList = FXCollections.observableArrayList("Blue","Light blue","Grey","Green","Amber","Bolotny","Brown","Black","Yellow");
    ObservableList<String> ColourHairList = FXCollections.observableArrayList("Blue","Dark","Brown","Yellow","Red","White","Grey");
    ObservableList<String> CountryList = FXCollections.observableArrayList("Ukrain","Russia","Belarussia","USA","Israel","Canada","Moldova","Latvia","Romain");
    @FXML
    private Button ButtonFindGroup,
            ButtonAddCrime,
            ButtonAddGroup,
            ButtonAboutInterpol,
            ButtonSearching,
            ButtonFindACriminal,
            ButtonClose,
            ButtonHidden;
    @FXML
    private GridPane GridPaneResult;
    @FXML
    private FlowPane MainFlowPane, SecondFlowPane;
    @FXML
    private TextArea TextAreaAbout;
    @FXML
    private AnchorPane AnchorPaneMidle;

    Select slecting = new Select();
    public static Stage stage = new Stage();
    private double xOffset, yOffset;

    Preferences preferences = new Preferences() {
            @Override
            public void Localization() throws IOException {
                LastName.setText("* "+processFilesFromFolder(LanguageFolder, "Lastname", Preferences.lang)+" :");
                FirstName.setText("* "+processFilesFromFolder(LanguageFolder, "Firstname", Preferences.lang)+" :");
                Nationality.setText("* "+processFilesFromFolder(LanguageFolder, "Nationality", Preferences.lang)+" :");
                Sex.setText(processFilesFromFolder(LanguageFolder, "Sex", Preferences.lang)+" :");
                ColorEye.setText(processFilesFromFolder(LanguageFolder, "ColorOfEye", Preferences.lang)+" :");
                ColorHair.setText(processFilesFromFolder(LanguageFolder, "ColorOfHair", Preferences.lang)+" :");
                Wanted.setText(processFilesFromFolder(LanguageFolder, "WantedBy", Preferences.lang)+" :");
                CurrentAge.setText(processFilesFromFolder(LanguageFolder, "Age", Preferences.lang)+" :");
                TextSLName.setText(processFilesFromFolder(LanguageFolder, "Lastname", Preferences.lang)+" :");
                TextSFName.setText(processFilesFromFolder(LanguageFolder, "Firstname", Preferences.lang)+" :");
                TextSHair.setText(processFilesFromFolder(LanguageFolder, "ColorOfHair", Preferences.lang)+" :");
                TextSEyes.setText(processFilesFromFolder(LanguageFolder, "ColorOfEye", Preferences.lang)+" :");
                TextSBirth.setText(processFilesFromFolder(LanguageFolder, "BirthDate", Preferences.lang)+" :");
                TextSNation.setText(processFilesFromFolder(LanguageFolder, "Nationality", Preferences.lang)+" :");
                TextSSex.setText(processFilesFromFolder(LanguageFolder, "Sex", Preferences.lang)+" :");
                TextSWanted.setText(processFilesFromFolder(LanguageFolder, "WantedBy", Preferences.lang)+" :");
                TextSCity.setText(processFilesFromFolder(LanguageFolder, "CityOfBirth", Preferences.lang)+" :");
                TextSAlias.setText(processFilesFromFolder(LanguageFolder, "Alias", Preferences.lang)+" :");
                TextSHeight.setText(processFilesFromFolder(LanguageFolder, "Height", Preferences.lang)+" :");
                TextSAbout.setText(processFilesFromFolder(LanguageFolder, "AboutPerson", Preferences.lang)+" :");
                ButtonSearching.setText(processFilesFromFolder(LanguageFolder, "Search", Preferences.lang));
            }
        };

    @FXML
    void initialize() {
        PersonLocked.setVisible(true);

        try {
            preferences.Localization();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ButtonAddCrime.setText(About.title1);
        ButtonFindACriminal.setText(About.title2);
        ButtonFindGroup.setText(About.title3);
        ButtonAddGroup.setText(About.title4);
        ButtonAboutInterpol.setText(About.title5);

        MainFlowPane.setVisible(true);
        TextMain.setText(About.title2);

        LabelLastName.setText("");
        LabelBirthDate.setText("");
        LabelNationality.setText("");
        LabelForeName.setText("");
        LabelCity.setText("");
        LabelAlias.setText("");
        LabelSex.setText("");
        LabelHeight.setText("");
        LabelEye.setText("");
        LabelHair.setText("");
        LabelWantedBy.setText("");

        TextAreaAbout.setText("");

        ChoiceBoxNational.setItems(NationalList);
        ChoiceBoxCountry.setItems(CountryList);
        ChoiceBoxEyes.setItems(ColourEyesList);
        ChoiceBoxHair.setItems(ColourHairList);
        ChoiceBoxSex.setItems(SexList);

        slecting.getAnim(ButtonAboutInterpol);
        slecting.getAnim(ButtonAddCrime);
        slecting.getAnim(ButtonAddGroup);
        slecting.getAnim(ButtonSearching);
        slecting.getAnim(ButtonFindGroup);
        slecting.getAnim(ButtonFindACriminal);

        SliderAge.setOnMouseReleased(event -> {
            String s;
            Double temp = SliderAge.getValue();
            Integer tempi = temp.intValue();
            s = Double.toString(tempi);
            TextCounterAge.setText(s);
        });

        ButtonAddCrime.setOnMouseClicked(event -> OpenNewScene(ButtonAddCrime, "/bin/View/InterpolAddCrimeUI.fxml", About.title1, "Add crime"));
        ButtonFindGroup.setOnMouseClicked(event -> OpenNewScene(ButtonFindGroup, "/bin/View/InterpolSearchGroupingUI.fxml", About.title2, "Grouping search"));
        ButtonFindACriminal.setOnMouseClicked(event -> OpenNewScene(ButtonFindACriminal, "/bin/View/InterpolSearchCrimeUI.fxml", About.title3, "Crime search"));
        ButtonAddGroup.setOnMouseClicked(event -> OpenNewScene(ButtonAddGroup, "/bin/View/InterpolAddGroupingUI.fxml", About.title4, "Add grouping"));
        ButtonAboutInterpol.setOnMouseClicked(event -> OpenNewScene(ButtonAboutInterpol, "/bin/View/AboutUI.fxml", About.title5, "About"));
        ButtonSearching.setOnAction(event -> SearchingCrime());
        ButtonClose.setOnMouseClicked(event -> ButtonClose.getScene().getWindow().hide());
        ButtonHidden.setOnMouseClicked(event -> {
            stage.setIconified(true);
        });
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

    private void SearchingCrime()
    {
        crime = new Crime();

        try {
            String birthDay = String.valueOf(SliderAge.getValue());
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date date = format.parse("0000-00-00");
            Date sqldate = new Date(date.getTime());
            crime = crime.setLastname(FieldLastname.getText()).setFirstname(FieldForename.getText()).setNationality(ChoiceBoxNational.getValue())
                .setBirthday(sqldate).setSex(ChoiceBoxSex.getValue()).setEye(ChoiceBoxEyes.getValue()).setHair(ChoiceBoxHair.getValue())
                .setWantedBy(ChoiceBoxCountry.getValue());
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (!FieldLastname.getText().equals("") && !FieldForename.getText().equals("")) {
            sqlThread thread = new sqlThread();
            try {
                thread.start();
                thread.join();
                ShowResult(crime);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("The person with such data was not found." +
                        " Make sure that the data is entered correctly and all the required fields are filled in!");
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.initModality(Modality.APPLICATION_MODAL);

                alert.show();
            }
        }
        else {
            Shake lastname = new Shake(FieldLastname);
            Shake forename = new Shake(FieldForename);
            lastname.playAnim();
            forename.playAnim();
        }
    }
    class sqlThread extends Thread{
        @Override
        public synchronized void run() {
            crime = crimedb.getByPrimaryInfo(crime).build();
        }
    }

    public void ShowResult(Crime crime)
    {
        MainFlowPane.setVisible(false);
        SecondFlowPane.setVisible(false);
        GridPaneResult.setVisible(true);
        ButtonSearching.setVisible(false);
        TextCounterAge.setVisible(false);
        SliderAge.setVisible(false);
        CurrentAge.setVisible(false);

        TextMain.setText("Result: ");

        LabelLastName.setText(crime.getLastname());
        LabelBirthDate.setText(String.valueOf(crime.getBirthday()));
        LabelNationality.setText(crime.getNationality());
        LabelForeName.setText(crime.getFirstname());
        LabelCity.setText(crime.getCountry());
        LabelAlias.setText(crime.getAlias());
        LabelSex.setText(crime.getSex());
        LabelHeight.setText(String.valueOf(crime.getHeight()));
        LabelEye.setText(crime.getEye());
        LabelHair.setText(crime.getHair());
        LabelWantedBy.setText(crime.getWantedBy());
        System.out.println(crime.getPhoto().length);

        BufferedImage image5 = null;
        try {
            image5 = ImageIO.read(new ByteArrayInputStream(crime.getPhoto()));
            ImageIO.write(image5, "jpg", new File("temp/temp.jpg"));
            File file = new File("temp/temp.jpg");
            System.out.println(file.getPath());
            PersonLocked.setImage(new Image(String.valueOf(file.toURL())));
            PersonLocked.setFitHeight(200);
            PersonLocked.setFitWidth(250);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }

        TextAreaAbout.setText(crime.getAbout());
        TextAreaAbout.setWrapText(true);
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