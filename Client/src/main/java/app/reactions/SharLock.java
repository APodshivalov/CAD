package app.reactions;

import app.Controller;
import app.ImageFactory;
import app.ReactionFactory;
import app.interfaces.ReactButton;
import app.model.Point;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 23.04.2017.
 */
public class SharLock extends ReactButton  {
    private Controller controller;

    public SharLock(Controller controller) {
        this.controller = controller;
        name = "Reac2";
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

    @Override
    public void setAngle(int angle) {
        this.angle = angle;
    }
}
