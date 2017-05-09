package app.model;

import app.Controller;
import app.utils.CoordinateUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;

import java.util.UUID;

/**
 * Created by podsh on 08.04.2017.
 */
public class Point {
    private String id;
    private String nativeId;
    private double x;
    private double y;
    private boolean isSelected;
    private Force force;
    private Reaction reaction;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        nativeId = UUID.randomUUID().toString();
        isSelected = false;
        reaction = new Reaction();
        force = new Force();
    }

    public Point() {

    }

    public double getX() {
        return x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public boolean equals(double x, double y) {
        return this.x == x && this.y == y;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public boolean between(double x1, double y1, double x2, double y2) {
        isSelected = x1 <= x && x <= x2 && y2 <= y && y <= y1;
        return isSelected;
    }

    public Reaction getReaction() {
        return reaction;
    }

    public void setReaction(Reaction reaction) {
        this.reaction = reaction;
    }

    public void draw(Controller controller) {
        CoordinateUtils utils = controller.getCoordinateUtils();
        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
        gc.fillOval(utils.fromRealX(x) - 1, utils.fromRealY(y) - 1, 3 ,3);
        reaction.draw(this, controller);
        force.draw(gc, utils, this);
    }

    public void draw(Controller controller, MouseEvent mouseEvent) {
        CoordinateUtils utils = controller.getCoordinateUtils();
        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
        gc.fillOval(utils.fromRealX(x) - 1, utils.fromRealY(y) - 1, 3 ,3);
        if (controller.getReac1().getToggleGroup().getSelectedToggle() == null ||
                !controller.getCoordinateUtils().isNear(this, mouseEvent.getX(), mouseEvent.getY())){
            reaction.draw(this, controller);
        }
        force.draw(gc, utils, this);
    }

    public Force getForce() {
        return force;
    }

    public void setForce(Force force) {
        this.force = force;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNativeId() {
        return nativeId;
    }

    public void setNativeId(String nativeId) {
        this.nativeId = nativeId;
    }
}
