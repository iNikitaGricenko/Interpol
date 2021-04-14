package bin.controls.funcs.Anim;

import javafx.animation.FadeTransition;
import javafx.scene.control.Button;
import javafx.util.Duration;

public class Select {
    static private Button button;

    public Select(){
    }

    public Button getAnim(Button button)
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

        return Select.button;
    }
}
