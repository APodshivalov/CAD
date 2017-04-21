package app.reactions;

import app.Controller;
import app.ImageFactory;
import app.interfaces.ReactButton;
import app.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 20.04.2017.
 */
public class Lock implements ReactButton {
    private Controller controller;
    private GraphicsContext gc;
    private String imageName;
    private int rotation;

    public Lock(Controller controller) {
        this.controller = controller;
        imageName = "Reac1";
        gc = controller.getCanvas().getGraphicsContext2D();
    }

    @Override
    public void draw(Point reactPoint, MouseEvent mouseEvent) {
        Image image = ImageFactory.getImage(imageName, getRotation(reactPoint, mouseEvent));
        gc.drawImage(image, controller.getCoordinateUtils().fromRealX(reactPoint.getX()) - 20,
                controller.getCoordinateUtils().fromRealY(reactPoint.getY() + 20));
    }

    public int getRotation(Point reactPoint, MouseEvent mouseEvent) {
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

    @Override
    public void draw(Point point) {
        double x = controller.getCoordinateUtils().fromRealX(point.getX());
        double y = controller.getCoordinateUtils().fromRealY(point.getY());
        gc.drawImage(ImageFactory.getImage(imageName, rotation), x - 20, y - 20);
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
