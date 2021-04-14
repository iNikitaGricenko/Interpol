package bin.controls.funcs;

import bin.controls.*;
import javafx.stage.Stage;

public class Stager {
    public static final Stager stager = new Stager();

    public void forwarder(String key, Stage stage)
    {
        switch(key){
            case "Add crime":
                InterpolAddCrimeController.stage = stage;
                break;
            case "Add grouping":
                InterpolAddGroupingController.stage = stage;
                break;
            case "Grouping search":
                InterpolSearchGroupingController.stage = stage;
                break;
            case "Crime search":
                InterpolSearchCrimeController.stage = stage;
                break;
            case "About":
                About.stage = stage;
                break;
            default:
                break;
        }
    }

}
