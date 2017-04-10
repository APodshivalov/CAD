package app.utils;

import app.Controller;

/**
 * Created by podsh on 11.04.2017.
 */
public class CoordinateUtils {
    private Controller controller;

    public CoordinateUtils(Controller controller) {
        this.controller = controller;
    }

    public double toRealX(double x) {
        return (x - 20) * controller.getScale();
    }

    public double toRealY(double y) {
        return (controller.getCanvas().getHeight() - y - 20) * controller.getScale();
    }

    public double fromRealX(double x) {
        return x / controller.getScale() + 20;
    }

    public double fromRealY(double y) {
        return controller.getCanvas().getHeight() - (y / controller.getScale() + 20);
    }
}
