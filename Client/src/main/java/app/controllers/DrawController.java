package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.Bar;
import app.model.Model;
import app.model.Point;
import app.utils.CoordinateUtils;
import javafx.scene.Cursor;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;


/**
 * Обработчик событий при режиме "Рисование"
 * Created by APodshivalov on 29.03.2017.
 */
public class DrawController implements Controllable {
    private double x;
    private double y;

    private Controller controller;
    private GraphicsContext gc;
    private CoordinateUtils utils;
    private Model model;
    private Point firstPoint;

    public DrawController(Controller controller) {
        this.controller = controller;
        gc = controller.getCanvas().getGraphicsContext2D();
        utils = controller.getCoordinateUtils();
        firstPoint = null;
        model = controller.getModel();
    }

    /**
     * Обработка клика мышки по канвасу
     *
     * @param mouseEvent Эвент клика
     */
    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
        if(mouseEvent.getButton() == MouseButton.PRIMARY){
            drawNewElements(mouseEvent);
        }
        if(mouseEvent.getButton() == MouseButton.SECONDARY){
            firstPoint = null;
        }
    }

    private void drawNewElements(MouseEvent mouseEvent) {
        if (firstPoint == null) {
            firstPoint = new Point(utils.toRealX(x), utils.toRealY(y));
        } else {
            Point newPoint = new Point(utils.toRealX(mouseEvent.getX()), utils.toRealY(mouseEvent.getY()));
            model.addBar(new Bar(firstPoint, newPoint));
            firstPoint = newPoint;
            controller.getCanvas().redraw();
        }
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        controller.getCanvas().redraw();
        if (firstPoint != null) {
            gc.strokeLine(utils.fromRealX(firstPoint.getX()), utils.fromRealY(firstPoint.getY()),
                    mouseEvent.getX(), mouseEvent.getY());
        }
        drawCursor(mouseEvent);
    }

    private void drawCursor(MouseEvent e) {
        gc.strokeLine(e.getX(), e.getY() + 3, e.getX(), e.getY() + 8);
        gc.strokeLine(e.getX() + 3, e.getY(), e.getX() + 8, e.getY());
        gc.strokeLine(e.getX(), e.getY() - 3, e.getX(), e.getY() - 8);
        gc.strokeLine(e.getX() - 3, e.getY(), e.getX() - 8, e.getY());
    }

    @Override
    public void disable() {
        controller.getCanvas().redraw();
        controller.getPivot().setVisible(false);
        controller.getOrto().setVisible(false);
        controller.getNet().setVisible(false);
        controller.getCanvas().setCursor(Cursor.DEFAULT);
        firstPoint = null;
    }

    @Override
    public void enable() {
        controller.getPivot().setVisible(true);
        controller.getOrto().setVisible(true);
        controller.getNet().setVisible(true);
        controller.getCanvas().setCursor(Cursor.NONE);
    }
}
