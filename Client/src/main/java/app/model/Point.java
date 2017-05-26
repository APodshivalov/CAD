package app.model;

import app.Controller;
import app.utils.CoordinateUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.ws.rs.POST;
import java.util.UUID;

/**
 * Created by podsh on 08.04.2017.
 */
public class Point {
    private String id;
    private double x;
    private double y;
    private boolean isSelected;
    private Force force;
    private Reaction reaction;

    public Point(double x, double y) {
        id = UUID.randomUUID().toString();
        this.x = x;
        this.y = y;
        isSelected = false;
        reaction = new Reaction();
        force = new Force();
    }

    public Point() {
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof Point) {
            Point p = (Point) obj;
            if (p.getId().equals(this.getId())) {
                return true;
            }
        }
        return false;
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
        drawOval(gc, utils.fromRealX(x) - 1, utils.fromRealY(y) - 1);
        reaction.draw(this, controller);
        force.draw(gc, utils, this);
    }

    private void drawOval(GraphicsContext gc, double x, double y) {
        if (isSelected) {
            gc.setFill(Color.RED);
        } else {
            gc.setFill(Color.BLACK);
        }
        gc.fillOval(x, y, 3, 3);
    }

    public void draw(Controller controller, MouseEvent mouseEvent) {
        CoordinateUtils utils = controller.getCoordinateUtils();
        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
        drawOval(gc, utils.fromRealX(x) - 1, utils.fromRealY(y) - 1);
        if (controller.getReac1().getToggleGroup().getSelectedToggle() == null ||
                !controller.getCoordinateUtils().isNear(this, mouseEvent.getX(), mouseEvent.getY())) {
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

    public void select(double x, double y, double x1, double y1) {
        setSelected(this.x >= x &&
                this.x <= x1 &&
                this.y <= y &&
                this.y >= y1
        );
    }
}
