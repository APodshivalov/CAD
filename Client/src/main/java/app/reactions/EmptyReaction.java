package app.reactions;

import app.interfaces.ReactButton;
import app.model.Point;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 21.04.2017.
 */
public class EmptyReaction implements ReactButton {
    @Override
    public void draw(Point reactPoint, MouseEvent mouseEvent) {

    }

    @Override
    public void draw(Point point) {

    }

    @Override
    public void setRotation(int rotation) {

    }

    @Override
    public int getRotation(Point reactPoint, MouseEvent mouseEvent) {
        return 0;
    }
}
