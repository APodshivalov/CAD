package app;

import app.model.Point;
import javafx.scene.image.Image;

/**
 * Created by APodshivalov on 21.04.2017.
 */
public class ImageFactory {
    public static Image getImage(String imageName, int i) {
        return new Image("images/reactions/mini" + imageName + "grad" + i + ".png", 40,40,true,true);
    }

    public static void drawImage(Image image, Controller controller, Point reactPoint) {
        double x = controller.getCoordinateUtils().fromRealX(reactPoint.getX());
        double y = controller.getCoordinateUtils().fromRealY(reactPoint.getY());
        controller.getCanvas().getGraphicsContext2D().drawImage(image, x - 20, y - 20);
    }

    public static Image getImage(String type) {
        return new Image("images/" + type + ".png");
    }
}
