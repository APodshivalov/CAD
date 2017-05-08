package app.reactions;

import app.interfaces.ReactButton;
import app.model.Point;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 21.04.2017.
 */
public class EmptyReaction extends ReactButton {
    public EmptyReaction() {
        name = "Reac0";
        angle = 0;
    }

    @Override
    public void draw(Point reactPoint, MouseEvent mouseEvent) {

    }

    @Override
    public void draw(Point point) {

    }

    @Override
    public void setAngle(int angle) {

    }
}
