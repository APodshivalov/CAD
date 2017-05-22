package app.model;

import app.Controller;
import app.ImageFactory;
import app.ReactionHelper;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 09.05.2017.
 */
public class Reaction {
    private String id;
    private String name;
    private int angle;

    public Reaction() {
        name = "Reac0";
    }

    public Reaction(String type) {
        name = type;
    }

    public void draw(Point point, MouseEvent mouseEvent, Controller controller) {
        if (!name.equals("Reac0")) {
            int rotation = ReactionHelper.getRotation(point, mouseEvent, controller);
            Image image = ImageFactory.getImage(name, rotation);
            ImageFactory.drawImage(image, controller, point);
        }
    }


    public void draw(Point point, Controller controller) {
        if (!name.equals("Reac0")) {
            Image image = ImageFactory.getImage(name, angle);
            ImageFactory.drawImage(image, controller, point);
        }
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAngle() {
        return angle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean empty() {
        return name.equals("Reac0");
    }
}
