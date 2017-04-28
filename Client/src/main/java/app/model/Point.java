package app.model;

import app.Controller;
import app.interfaces.ReactButton;
import app.reactions.EmptyReaction;
import app.utils.CoordinateUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Created by podsh on 08.04.2017.
 */
public class Point {
    private double x;
    private double y;
    private boolean isSelected;
    private ReactButton reaction;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        isSelected = false;
        reaction = new EmptyReaction();
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
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

    public ReactButton getReaction() {
        return reaction;
    }

    public void setReaction(ReactButton reaction) {
        this.reaction = reaction;
    }

    public void draw(Controller controller) {
        CoordinateUtils utils = controller.getCoordinateUtils();
        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
        gc.fillOval(utils.fromRealX(x) - 1, utils.fromRealY(y) - 1, 3 ,3);
        reaction.draw(this);
    }

    public void draw(Controller controller, MouseEvent mouseEvent) {
        CoordinateUtils utils = controller.getCoordinateUtils();
        GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
        gc.fillOval(utils.fromRealX(x) - 1, utils.fromRealY(y) - 1, 3 ,3);
        if (controller.getReactButtons().getSelectedToggle() == null ||
                !controller.getCoordinateUtils().isNear(this, mouseEvent.getX(), mouseEvent.getY())){
            reaction.draw(this);
        }
    }
}
