package app.interfaces;

import app.model.Point;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 20.04.2017.
 */
public interface ReactButton {
    void draw(Point reactPoint, MouseEvent mouseEvent);

    void draw(Point point);

    void setRotation(int rotation);
}

