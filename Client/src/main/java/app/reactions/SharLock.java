package app.reactions;

import app.Controller;
import app.ImageFactory;
import app.ReactionFactory;
import app.interfaces.ReactButton;
import app.model.Point;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 23.04.2017.
 */
public class SharLock implements ReactButton {
    private Controller controller;
    private GraphicsContext gc;
    private String imageName;
    private int rotation;

    public SharLock(Controller controller) {
        this.controller = controller;
        imageName = "Reac2";
        gc = controller.getCanvas().getGraphicsContext2D();
    }

    @Override
    public void draw(Point reactPoint, MouseEvent mouseEvent) {
        Image image = ImageFactory.getImage(imageName, ReactionFactory.getRotation(reactPoint, mouseEvent, controller));
        gc.drawImage(image, controller.getCoordinateUtils().fromRealX(reactPoint.getX()) - 20,
                controller.getCoordinateUtils().fromRealY(reactPoint.getY() + 20));
    }

    @Override
    public void draw(Point point) {
        double x = controller.getCoordinateUtils().fromRealX(point.getX());
        double y = controller.getCoordinateUtils().fromRealY(point.getY());
        gc.drawImage(ImageFactory.getImage(imageName, rotation), x - 20, y - 20);
    }

    @Override
    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
