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
            drawNewElements();
        }
        if(mouseEvent.getButton() == MouseButton.SECONDARY){
            firstPoint = null;
        }
    }

    private void drawNewElements() {
        Point pointAtThisCoordinate = model.findPoint(utils.toRealX(controller.getX()), utils.toRealY(controller.getY()));
        if (firstPoint == null) {
            firstPoint = pointAtThisCoordinate == null ? new Point(utils.toRealX(controller.getX()), utils.toRealY(controller.getY())) : pointAtThisCoordinate;
        } else {
            Point newPoint = pointAtThisCoordinate == null ? new Point(utils.toRealX(controller.getX()), utils.toRealY(controller.getY())) : pointAtThisCoordinate;
            model.addBar(new Bar(firstPoint, newPoint));
            firstPoint = newPoint;
            controller.getCanvas().redraw();
        }
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        // Привязка курсора к точкам
        if (controller.getPivot().isSelected()){
            Point p = model.findNearbyPoint(utils.toRealX(mouseEvent.getX()), utils.toRealY(mouseEvent.getY()));
            if (p != null){
                controller.setY(utils.fromRealY(p.getY()));
                controller.setX(utils.fromRealX(p.getX()));
            }
        }
        // Перерисовка модели
        controller.getCanvas().redraw();
        if (firstPoint != null) {
            gc.strokeLine(utils.fromRealX(firstPoint.getX()), utils.fromRealY(firstPoint.getY()),
                    controller.getX(), controller.getY());
        }
        drawCursor();
    }

    private void drawCursor() {
        double x = controller.getX();
        double y = controller.getY();
        gc.strokeLine(x, y + 3, x, y + 8);
        gc.strokeLine(x + 3, y, x + 8, y);
        gc.strokeLine(x, y - 3, x, y - 8);
        gc.strokeLine(x - 3, y, x - 8, y);
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
