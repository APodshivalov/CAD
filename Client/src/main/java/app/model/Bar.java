package app.model;

import app.Controller;
import app.utils.CoordinateUtils;

/**
 * Created by podsh on 08.04.2017.
 */
public class Bar {
    private Point firstPoint;
    private Point secondPoint;

    public Bar(Point first, Point second){
        this.firstPoint = first;
        this.secondPoint = second;
    }

    public void draw(Controller controller) {
        CoordinateUtils utils = controller.getCoordinateUtils();
        controller.getCanvas().getGraphicsContext2D().strokeLine(utils.fromRealX(firstPoint.getX()), utils.fromRealY(firstPoint.getY()),
                utils.fromRealX(secondPoint.getX()), utils.fromRealY(secondPoint.getY()));
    }
}
