package bin.controls.funcs.Anim;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class Shake {
    private TranslateTransition tt;

    public Shake(Node node)
    {
        tt = new TranslateTransition(Duration.millis(50), node);
        tt.setFromX(-10f);
        tt.setByX(10f);
        tt.setCycleCount(5);
        tt.setAutoReverse(true);
    }

    public void playAnim()
    {
        tt.playFromStart();
    }
}
