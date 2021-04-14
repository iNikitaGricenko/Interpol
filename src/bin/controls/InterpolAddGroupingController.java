package bin.controls;

import bin.controls.funcs.Anim.Select;
import bin.controls.funcs.Stager;
import bin.data.Models.Grouping;
import bin.data.dao.GroupingDao;
import bin.data.dao.daoimpl.GroupingDatabaseDao;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class InterpolAddGroupingController {
    Grouping grouping;
    GroupingDao groupingdb = new GroupingDatabaseDao();
    @FXML
    private Text Approximate, CityCreation, NameGrouping, EstimatedLocation, Specification, EstimatedMem, AboutText, Danger, Wanted, TextMain;
    @FXML
    private TextField FieldName,
            FieldSpecification,
            FieldCityCreation;
    @FXML
    private TextArea AreaAboutText;
    @FXML
    private DatePicker DatePickerCreated;
    @FXML
    private ChoiceBox<String> ChoiceBoxLocation,
            ChoiceBoxDangerLvl,
            ChoiceBoxWanted;
    ObservableList<String> LocationList = FXCollections.observableArrayList(
            "Ukrain","Russia","Belarussia","USA","Israel","Canada","Moldova","Latvia","Romain");
    ObservableList<String> DangerList = FXCollections.observableArrayList(
            "Barely noticeable", "Weak", "Medium", "Tangible", "Large", "Dangerous", "Super dangerous");
    ObservableList<String> WantedList = FXCollections.observableArrayList(
            "Ukrain","Russia","Belarussia","USA","Israel","Canada","Moldova","Latvia","Romain");
    @FXML
    private Spinner<Integer> SpinnerMembers;
    SpinnerValueFactory<Integer> gradesValueMembers = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,1000);
    @FXML
    private Button ButtonAboutInterpol,
            ButtonFindCrime,
            ButtonFindGroup,
            ButtonAddCrime,
            ButtonAddGroup,
            ButtonEnter,
            ButtonClose,
            ButtonHidden,
            ButtonUpdate;
    @FXML
    private AnchorPane AnchorPaneMidle;

    Select selecting = new Select();
    public static Stage stage = new Stage();
    private double xOffset, yOffset;

    Preferences preferences = new Preferences() {
        @Override
        public void Localization() throws IOException{
        Approximate.setText(processFilesFromFolder(LanguageFolder, "Approximate", Preferences.lang)+" :");
        CityCreation.setText(processFilesFromFolder(LanguageFolder, "CityCreation", Preferences.lang)+" :");
        NameGrouping.setText(processFilesFromFolder(LanguageFolder, "NameGrouping", Preferences.lang)+" :");
        EstimatedLocation.setText(processFilesFromFolder(LanguageFolder, "EstimatedLoc", Preferences.lang)+" :");
        Specification.setText(processFilesFromFolder(LanguageFolder, "Specification", Preferences.lang)+" :");
        EstimatedMem.setText(processFilesFromFolder(LanguageFolder, "EstimatedMem", Preferences.lang)+" :");
        AboutText.setText(processFilesFromFolder(LanguageFolder, "AboutGrouping", Preferences.lang)+" :");
        Danger.setText(processFilesFromFolder(LanguageFolder, "DangerLvl", Preferences.lang)+" :");
        Wanted.setText(processFilesFromFolder(LanguageFolder, "WantedBy", Preferences.lang)+" :");
        ButtonEnter.setText(processFilesFromFolder(LanguageFolder, "Enter", Preferences.lang));
        ButtonUpdate.setText(processFilesFromFolder(LanguageFolder, "Update", Preferences.lang));
        }
    };

    @FXML
    void initialize() {
        try {
            preferences.Localization();
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextMain.setText(About.title4);

        selecting.getAnim(ButtonAboutInterpol);
        selecting.getAnim(ButtonAddCrime);
        selecting.getAnim(ButtonEnter);
        selecting.getAnim(ButtonFindGroup);
        selecting.getAnim(ButtonFindCrime);
        selecting.getAnim(ButtonUpdate);

        ChoiceBoxLocation.setItems(LocationList);
        ChoiceBoxDangerLvl.setItems(DangerList);
        ChoiceBoxWanted.setItems(WantedList);

        SpinnerMembers.setValueFactory(gradesValueMembers);
        ButtonAddCrime.setText(About.title1);
        ButtonFindCrime.setText(About.title2);
        ButtonFindGroup.setText(About.title3);
        ButtonAddGroup.setText(About.title4);
        ButtonAboutInterpol.setText(About.title5);

        ButtonAddCrime.setOnMouseClicked(event -> OpenNewScene(ButtonAddCrime, "/bin/View/InterpolAddCrimeUI.fxml", About.title1, "Add crime"));
        ButtonFindCrime.setOnMouseClicked(event -> OpenNewScene(ButtonFindCrime, "/bin/View/InterpolSearchCrimeUI.fxml", About.title2, "Crime search"));
        ButtonFindGroup.setOnMouseClicked(event -> OpenNewScene(ButtonFindGroup, "/bin/View/InterpolSearchGroupingUI.fxml", About.title3, "Grouping search"));
        ButtonAddGroup.setOnMouseClicked(event -> OpenNewScene(ButtonAddGroup, "/bin/View/InterpolAddGroupingUI.fxml", About.title4, "Add grouping"));
        ButtonAboutInterpol.setOnMouseClicked(event -> OpenNewScene(ButtonAboutInterpol, "/bin/View/AboutUI.fxml", About.title5, "About"));
        ButtonEnter.setOnAction(event -> AddGrouping() );
        ButtonUpdate.setOnAction(event -> UpdateGrouping());
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

    private void UpdateGrouping()
    {
        grouping = new Grouping();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Status");
        alert.setHeaderText(null);
        alert.setContentText("Something was wrong");
        alert.initModality(Modality.APPLICATION_MODAL);
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date date;
            String dataCreation = null;
            try {
                dataCreation = DatePickerCreated.getValue().toString();
            } catch (NullPointerException nullPointerException){
                nullPointerException.printStackTrace();
            }
            if (dataCreation!=null) {
                dataCreation = DatePickerCreated.getValue().toString();
                date = format.parse(dataCreation);
            }
            else {
                date = format.parse(dataCreation);
            }
            Date sqldate = new Date(date.getTime());

            grouping.setName(FieldName.getText()).setSpecification(FieldSpecification.getText()).setLocation(ChoiceBoxLocation.getValue())
                    .setAboutGrouping(AreaAboutText.getText()).setCreationDate(sqldate).setCountry(FieldCityCreation.getText()).setMember(SpinnerMembers.getValue())
                    .setDangerLvl(ChoiceBoxDangerLvl.getValue()).setWantedBy(ChoiceBoxWanted.getValue());

            sqlUpdateThread sqlupdate = new sqlUpdateThread();
            sqlupdate.start();
            sqlupdate.join();
            alert.setContentText("Updated successful");
            alert.setAlertType(Alert.AlertType.INFORMATION);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            alert.setContentText("Updated failed");
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        finally {
            alert.show();
        }
    }
    private void AddGrouping()
    {
        grouping = new Grouping();
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Status");
        alert.setHeaderText(null);
        alert.setContentText("Something was wrong");
        alert.initModality(Modality.APPLICATION_MODAL);
        try {
            String dataCreation = null;
            try {
                dataCreation = DatePickerCreated.getValue().toString();
            } catch (NullPointerException nullPointerException){
                nullPointerException.printStackTrace();
            }
            SimpleDateFormat format = new SimpleDateFormat("yyyy-mm-dd");
            java.util.Date date;
            if (dataCreation!=null) {
                date = format.parse(dataCreation);
            } else {
                date = format.parse("00-00-0000");
            }
            Date sqldate = new Date(date.getTime());

            grouping.setName(FieldName.getText()).setSpecification(FieldSpecification.getText()).setLocation(ChoiceBoxLocation.getValue())
                    .setAboutGrouping(AreaAboutText.getText()).setCreationDate(sqldate).setCountry(FieldCityCreation.getText()).setMember(SpinnerMembers.getValue())
                    .setDangerLvl(ChoiceBoxDangerLvl.getValue()).setWantedBy(ChoiceBoxWanted.getValue());

            sqlAddThread sqladd = new sqlAddThread();
            sqladd.start();
            sqladd.join();
            alert.setContentText("Adding successful");
            alert.setAlertType(Alert.AlertType.INFORMATION);
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
            alert.setContentText("Adding failed");
            alert.setAlertType(Alert.AlertType.ERROR);
        }
        finally {
            alert.show();
        }
    }

    class sqlAddThread extends Thread{
        @Override
        public void run() {
            try {
                groupingdb.add(grouping);
            }
            catch (SQLException sqlException){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect data");
                alert.setContentText(sqlException.getMessage());
                alert.show();
            }
        }
    }
    class sqlUpdateThread extends Thread{
        @Override
        public void run() {
            groupingdb.update(grouping);
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
