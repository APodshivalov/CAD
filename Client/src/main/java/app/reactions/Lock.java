package app.reactions;

import app.Controller;
import app.ImageFactory;
import app.ReactionFactory;
import app.interfaces.ReactButton;
import app.model.Point;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 20.04.2017.
 */
public class Lock extends ReactButton  {
    private Controller controller;

    public Lock(Controller controller) {
        this.controller = controller;
        name = "Reac1";
    }

    @Override
    public void draw(Point point, MouseEvent mouseEvent) {
        int rotation = ReactionFactory.getRotation(point, mouseEvent, controller);
        Image image = ImageFactory.getImage(name, rotation);
        ImageFactory.drawImage(image, controller, point);
    }


    @Override
    public void draw(Point point) {
        Image image = ImageFactory.getImage(name, angle);
        ImageFactory.drawImage(image, controller, point);
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }
}
