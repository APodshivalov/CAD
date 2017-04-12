package app.utils;

import app.Controller;
import app.controllers.ControllerFactory;
import app.controllers.DrawController;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 11.04.2017.
 */
public class CoordinateUtils {
    private Controller controller;
    private double x;
    private double y;
    private double tempDelX;
    private double tempDelY;
    private double delX;
    private double delY;

    public CoordinateUtils(Controller controller) {
        this.controller = controller;
    }

    public double toRealX(double x) {
        return (x - 20 - tempDelX - delX) * controller.getScale();
    }

    public double toRealY(double y) {
        return (controller.getCanvas().getHeight() - y - 20 + tempDelY+ delY) * controller.getScale();
    }

    public double fromRealX(double x) {
        return x / controller.getScale() + 20 + tempDelX + delX;
    }

    public double fromRealY(double y) {
        return controller.getCanvas().getHeight() - (y / controller.getScale() + 20 - tempDelY - delY);
    }

    public void onMouseDrag(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.MIDDLE){
            tempDelX = mouseEvent.getX() - x;
            tempDelY = mouseEvent.getY() - y;
            controller.getCanvas().redraw();
            if (controller.getCurrentEventListener() instanceof DrawController){
                DrawController drawController = (DrawController) controller.getCurrentEventListener();
                drawController.drawCursor();
            }
        }
    }

    public void onMouseMoved(MouseEvent mouseEvent) {
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        controller.getCurrentEventListener().onMouseMoved(mouseEvent);
        controller.getCoordinatesLabel().setText("x: " + toRealX(x) +
                ", y: " + toRealY(y));
    }

    public void onMouseReleased() {
        delX += tempDelX;
        delY += tempDelY;
    }

    public double toRealY() {
        return toRealY(y);
    }

    public double toRealX() {
        return toRealX(x);
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
