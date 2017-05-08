package app.interfaces;

import app.model.Point;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 20.04.2017.
 */
public abstract class ReactButton {
    protected String name;
    protected int angle;

    public abstract void draw(Point reactPoint, MouseEvent mouseEvent);

    public abstract void draw(Point point);

    public abstract void setAngle(int angle);

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAngle() {
        return angle;
    }
}

