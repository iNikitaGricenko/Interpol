package bin.controls;

import bin.controls.funcs.Anim.Select;
import bin.controls.funcs.Anim.Shake;
import bin.controls.funcs.Stager;
import bin.data.Models.Crime;
import bin.data.Models.Grouping;
import bin.data.dao.CrimeDao;
import bin.data.dao.GroupingDao;
import bin.data.dao.daoimpl.CrimeDatabaseDao;
import bin.data.dao.daoimpl.GroupingDatabaseDao;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
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

import java.io.IOException;
import java.util.List;

public class InterpolSearchGroupingController {
    Grouping grouping;
    GroupingDao groupingdb = new GroupingDatabaseDao();
    @FXML
    private TextField FieldName,
            FieldSpecification,
            FieldCityCreation,
            FieldGrouping;
    @FXML
    private Text TextMain, TextName, TextSpecification, TextCity, TextDangerLvl, TextWantedBy, TextLocation,
    TextSName, TextSSpec, TextSLocation, TextSAbout, TextSDate, TextSCity, TextSNumMem, TextSDanger, TextSWanted, TextSMemList;
    @FXML
    private ListView<String> ListViewMemList;
    @FXML
    private Label LabelName,
            LabelName2,
            LabelName1,
            LabelName4,
            LabelName5,
            LabelName6,
            LabelName7,
            LabelName8;
    @FXML
    private ImageView LockedGroup;
    @FXML
    private ChoiceBox<String> ChoiceBoxLocation,
            ChoiceBoxDangerLvl,
            ChoiceBoxWanted;
    ObservableList<String> LocationList = FXCollections.observableArrayList("Ukrain","Russia","Belarussia","USA","Israel","Canada","Moldova","Latvia","Romain");
    ObservableList<String> DangerList = FXCollections.observableArrayList("Barely noticeable", "Weak", "Medium", "Tangible", "Large", "Dangerous", " Super dangerous");
    ObservableList<String> WantedList = FXCollections.observableArrayList("Ukrain","Russia","Belarussia","USA","Israel","Canada","Moldova","Latvia","Romain");
    @FXML
    private Button ButtonFindCrime,
            ButtonAddCrime,
            ButtonAddGroup,
            ButtonAboutInterpol,
            ButtonSearching,
            ButtonFindAGroup,
            ButtonClose,
            ButtonHidden;
    @FXML
    private GridPane GridPaneResult;
    @FXML
    private FlowPane MainFlowPane;
    @FXML
    private TextArea TextAreaAbout;
    @FXML
    private AnchorPane AnchorPaneMidle;

    Select selecting = new Select();
    public static Stage stage = new Stage();
    private double xOffset, yOffset;

    Preferences preferences = new Preferences() {
            @Override
            public void Localization() throws IOException {
        TextMain.setText(processFilesFromFolder(LanguageFolder, "SearchDataGrouping", Preferences.lang));
        TextName.setText("* "+processFilesFromFolder(LanguageFolder, "NameGrouping", Preferences.lang)+" :");
        TextSpecification.setText("* "+processFilesFromFolder(LanguageFolder, "Specification", Preferences.lang)+" :");
        TextLocation.setText("* "+processFilesFromFolder(LanguageFolder, "Location", Preferences.lang)+" :");
        TextCity.setText(processFilesFromFolder(LanguageFolder, "CityCreation", Preferences.lang)+" :");
        TextDangerLvl.setText(processFilesFromFolder(LanguageFolder, "DangerLvl", Preferences.lang)+" :");
        TextWantedBy.setText(processFilesFromFolder(LanguageFolder, "WantedBy", Preferences.lang)+" :");
        TextSName.setText(processFilesFromFolder(LanguageFolder, "NameGrouping", Preferences.lang)+" :");
        TextSSpec.setText(processFilesFromFolder(LanguageFolder, "Specification", Preferences.lang)+" :");
        TextSLocation.setText(processFilesFromFolder(LanguageFolder, "Location", Preferences.lang)+" :");
        TextSAbout.setText(processFilesFromFolder(LanguageFolder, "AboutGrouping", Preferences.lang)+" :");
        TextSDate.setText(processFilesFromFolder(LanguageFolder, "DateCreation", Preferences.lang)+" :");
        TextSCity.setText(processFilesFromFolder(LanguageFolder, "CityCreation", Preferences.lang)+" :");
        TextSNumMem.setText(processFilesFromFolder(LanguageFolder, "EstimatedMem", Preferences.lang)+" :");
        TextSDanger.setText(processFilesFromFolder(LanguageFolder, "DangerLvl", Preferences.lang)+" :");
        TextSWanted.setText(processFilesFromFolder(LanguageFolder, "WantedBy", Preferences.lang)+" :");
        ButtonSearching.setText(processFilesFromFolder(LanguageFolder, "Search", Preferences.lang));
        }
    };

    @FXML
    void initialize() {
        LockedGroup.setVisible(true);

        try {
            preferences.Localization();
        } catch (IOException e) {
            e.printStackTrace();
        }

        ButtonAddCrime.setText(About.title1);
        ButtonFindCrime.setText(About.title2);
        ButtonFindAGroup.setText(About.title3);
        ButtonAddGroup.setText(About.title4);
        ButtonAboutInterpol.setText(About.title5);

        TextMain.setText(About.title3);
        MainFlowPane.setVisible(true);

        LabelName.setText("");
        LabelName1.setText("");
        LabelName2.setText("");
        LabelName4.setText("");
        LabelName5.setText("");
        LabelName6.setText("");
        LabelName7.setText("");
        LabelName8.setText("");

        TextAreaAbout.setText("");

        ChoiceBoxLocation.setItems(LocationList);
        ChoiceBoxDangerLvl.setItems(DangerList);
        ChoiceBoxWanted.setItems(WantedList);

        selecting.getAnim(ButtonAboutInterpol);
        selecting.getAnim(ButtonAddCrime);
        selecting.getAnim(ButtonAddGroup);
        selecting.getAnim(ButtonSearching);
        selecting.getAnim(ButtonFindAGroup);
        selecting.getAnim(ButtonFindCrime);

        ButtonAddCrime.setOnMouseClicked(event -> OpenNewScene(ButtonAddCrime, "/bin/View/InterpolAddCrimeUI.fxml", About.title1, "Add crime"));
        ButtonFindAGroup.setOnMouseClicked(event -> OpenNewScene(ButtonFindAGroup, "/bin/View/InterpolSearchGroupingUI.fxml", About.title2, "Grouping search"));
        ButtonFindCrime.setOnMouseClicked(event -> OpenNewScene(ButtonFindCrime, "/bin/View/InterpolSearchCrimeUI.fxml", About.title3, "Crime search"));
        ButtonAddGroup.setOnMouseClicked(event -> OpenNewScene(ButtonAddGroup, "/bin/View/InterpolAddGroupingUI.fxml", About.title4, "Add grouping"));
        ButtonAboutInterpol.setOnMouseClicked(event -> OpenNewScene(ButtonAboutInterpol, "/bin/View/AboutUI.fxml", About.title5, "About"));
        ButtonSearching.setOnAction(event -> SearchingGrouping() );
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

    public void SearchingGrouping() {
        grouping = new Grouping();
        grouping.setName(FieldName.getText()).setSpecification(FieldSpecification.getText()).setLocation(ChoiceBoxLocation.getValue())
                .setCountry(FieldCityCreation.getText()).setDangerLvl(ChoiceBoxDangerLvl.getValue()).setWantedBy(ChoiceBoxWanted.getValue());
        if (grouping.getName() != null && grouping.getSpecification() != null && grouping.getLocation() != null)
        {
            sqlThread thread = new sqlThread();
            try {
                thread.start();
                thread.join();
                ShowResult(grouping);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("No groupings with such data were found. " +
                        "Make sure that the data is entered correctly and all the necessary fields are filled in!");
                alert.setTitle(null);
                alert.setHeaderText(null);
                alert.initModality(Modality.APPLICATION_MODAL);

                alert.show();
            }
        }
        else
        {
            Shake name = new Shake(FieldName);
            Shake spec = new Shake(FieldSpecification);
            Shake loc = new Shake((Node) LocationList);
            name.playAnim();
            spec.playAnim();
            loc.playAnim();
        }
    }
    class sqlThread extends Thread{
        @Override
        public synchronized void run() {
            grouping = groupingdb.getByPrimaryInfo(grouping).build();
            groupingdb.getCrimes(grouping);
        }
    }

    public void ShowResult(Grouping grouping)
    {
        List<Crime> crimeList = grouping.getCrimes();
        Crime temp = crimeList.get(0);
        ObservableList<String> crimes = FXCollections.observableArrayList("1\t" + temp.getLastname() + "\t" + temp.getFirstname() + "\t" + temp.getAlias());
        for (int i = 1; i<crimeList.size(); i++){
            temp = crimeList.get(i);
            crimes.add(1+i + "\t" + temp.getLastname() + "\t" + temp.getFirstname() + "\t" + temp.getAlias());
        }
        ListViewMemList.setItems(crimes);
        ListViewMemList.getSelectionModel().selectedItemProperty().addListener((prop) -> {
            int index = ListViewMemList.getSelectionModel().getSelectedIndex();
            CrimeDao crimedb = new CrimeDatabaseDao();
            Crime crime;
            crime = grouping.getCrimes().get(index);
            crime = crimedb.getById(crime.getId());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            StringBuilder sometext = new StringBuilder();
            sometext.append(crime.getFirstname()+" ");
            sometext.append("\""+crime.getAlias()+"\"");
            sometext.append(" "+crime.getLastname());
            sometext.append("\n"+crime.getAbout());
            alert.setContentText(sometext.toString());
            alert.setTitle("About");
            alert.setHeaderText(null);
            alert.show();
        });
        ListViewMemList.setVisible(true);
        MainFlowPane.setVisible(false);
        GridPaneResult.setVisible(true);
        ButtonSearching.setVisible(false);
        LockedGroup.setVisible(false);

        TextMain.setText("RESULT: ");
        LabelName.setText(grouping.getName());
        LabelName1.setText(grouping.getSpecification());
        LabelName2.setText(grouping.getLocation());
        TextAreaAbout.setText(grouping.getAboutGrouping());
        TextAreaAbout.setWrapText(true);
        LabelName4.setText(String.valueOf(grouping.getCreationDate()));
        LabelName5.setText(grouping.getCountry());
        LabelName6.setText(String.valueOf(grouping.getMember()));
        LabelName7.setText(grouping.getDangerLvl());
        LabelName8.setText(grouping.getWantedBy());
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
