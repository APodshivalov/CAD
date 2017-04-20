package app.utils;

import app.Controller;
import app.controllers.DrawController;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

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
    private List<Double> scalesList;
    private double scale;

    public CoordinateUtils(Controller controller) {
        this.controller = controller;
        scalesList = new ArrayList<>();
        scalesList.add(0.01);
        scalesList.add(0.05);
        scalesList.add(0.1);
        scalesList.add(0.2);
        scalesList.add(0.5);
        scalesList.add(1.0);
        scalesList.add(1.1);
        scalesList.add(1.2);
        scalesList.add(1.5);
        scalesList.add(2.0);
        scalesList.add(4.0);
        scalesList.add(6.0);
        scalesList.add(8.0);
        scalesList.add(10.0);
        scale = 1;
    }

    public double toRealX(double x) {
        return (x - 20 - tempDelX - delX) * scale;
    }

    public double toRealY(double y) {
        return (controller.getCanvas().getHeight() - y - 20 + tempDelY + delY) * scale;
    }

    public double fromRealX(double x) {
        return x / scale + 20 + tempDelX + delX;
    }

    public double fromRealY(double y) {
        return controller.getCanvas().getHeight() - (y / scale + 20 - tempDelY - delY);
    }

    public void onMouseDrag(MouseEvent mouseEvent) {
        tempDelX = mouseEvent.getX() - x;
        tempDelY = mouseEvent.getY() - y;
        controller.getCanvas().redraw();
    }

    public void onMouseMoved(MouseEvent mouseEvent) {
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        controller.getCurrentEventListener().onMouseMoved(mouseEvent);
        controller.getCoordinatesLabel().setText(String.format("x: %-6.2f, y: %-6.2f", toRealX(), toRealY()));
    }

    public void onMouseReleased() {
        delX += tempDelX;
        delY += tempDelY;
        tempDelX = 0;
        tempDelY = 0;
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

    public void prevScale() {
        int index = scalesList.indexOf(scale);
        if (index < scalesList.size() - 1) {
            scale = scalesList.get(++index);
        }
    }

    public void nextScale() {
        int index = scalesList.indexOf(scale);
        if (index > 0) {
            scale = scalesList.get(--index);
        }
    }

    public String getScale() {
        if (scale < 1) {
            return String.format("M %.0f:1", 1 / scale);
        } else {
            if (scale == (long) scale)
                return String.format("M 1:%d", (long) scale);
            else
                return String.format("M 1:%s", scale);
        }
    }

    public void addDelX(double x) {
        delX += x;
    }
}
