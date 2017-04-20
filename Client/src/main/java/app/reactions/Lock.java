package app.reactions;

import app.Controller;
import app.interfaces.ReactButton;
import app.model.Point;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 20.04.2017.
 */
public class Lock implements ReactButton {
    private Controller controller;

    public Lock(Controller controller){
        this.controller = controller;
    }

    @Override
    public void draw(Point reactPoint, MouseEvent mouseEvent) {
        double x = controller.getCoordinateUtils().fromRealX(reactPoint.getX());
        double y = controller.getCoordinateUtils().fromRealY(reactPoint.getY());
        controller.getCanvas().getGraphicsContext2D().fillOval(x-2,y-2,5,5);
    }
}
