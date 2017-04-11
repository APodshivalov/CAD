package app.model;

import app.Controller;
import app.utils.CoordinateUtils;

/**
 * Created by podsh on 08.04.2017.
 */
public class Bar {
    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }

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

    public boolean equals(Bar obj) {
        return ((obj.getSecondPoint() == secondPoint) && (obj.getFirstPoint() == firstPoint)) ||
                ((obj.getSecondPoint() == firstPoint) && (obj.getFirstPoint() == secondPoint));
    }
}
