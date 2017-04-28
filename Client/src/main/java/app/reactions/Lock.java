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
 * Created by podsh on 20.04.2017.
 */
public class Lock implements ReactButton {
    private Controller controller;
    private String imageName;
    private int rotation;

    public Lock(Controller controller) {
        this.controller = controller;
        imageName = "Reac1";
    }

    @Override
    public void draw(Point point, MouseEvent mouseEvent) {
        int rotation = ReactionFactory.getRotation(point, mouseEvent, controller);
        Image image = ImageFactory.getImage(imageName, rotation);
        ImageFactory.drawImage(image, controller, point);
    }


    @Override
    public void draw(Point point) {
        Image image = ImageFactory.getImage(imageName, rotation);
        ImageFactory.drawImage(image, controller, point);
    }

    public void setRotation(int rotation) {
        this.rotation = rotation;
    }
}
