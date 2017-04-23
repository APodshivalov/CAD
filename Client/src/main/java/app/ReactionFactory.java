package app;

import app.interfaces.ReactButton;
import app.model.Point;
import app.reactions.EmptyReaction;
import app.reactions.Lock;
import app.reactions.SharLock;
import app.reactions.SharNoLock;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 21.04.2017.
 */
public class ReactionFactory {
    public static ReactButton getInstance(Class<? extends ReactButton> lockClass, Controller controller) {
        if (lockClass.getName().equals("app.reactions.Lock")){
            return new Lock(controller);
        }
        if (lockClass.getName().equals("app.reactions.SharLock")){
            return new SharLock(controller);
        }
        if (lockClass.getName().equals("app.reactions.SharNoLock")){
            return new SharNoLock(controller);
        }
        if (lockClass.getName().equals("app.reactions.EmptyReaction")){
            return new EmptyReaction();
        }

        return null;
    }

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
}
