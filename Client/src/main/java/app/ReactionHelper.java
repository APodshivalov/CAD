package app;

import app.model.Point;
import app.model.Reaction;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 21.04.2017.
 */
public class ReactionHelper {

    public static int getRotation(Point reactPoint, MouseEvent mouseEvent, Controller controller) {
        double x = controller.getCoordinateUtils().fromRealX(reactPoint.getX());
        double y = controller.getCoordinateUtils().fromRealY(reactPoint.getY());
        double delX = mouseEvent.getX() - x;
        double delY = mouseEvent.getY() - y;
        if (Math.abs(delX) > Math.abs(delY)) {
            if (delX >= 0) {
                return 90;
            } else {
                return 270;
            }
        } else {
            if (delY >= 0) {
                return 0;
            } else {
                return 180;
            }
        }
    }

    public static void draw(Reaction reaction, Point point, MouseEvent mouseEvent) {
    }
}
