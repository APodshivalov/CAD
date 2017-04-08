package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.Bar;
import app.model.Model;
import app.model.Point;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


/**
 * Обработчик событий при режиме "Рисование"
 * Created by APodshivalov on 29.03.2017.
 */
public class DrawController implements Controllable {
    private Controller controller;
    private Model model;
    private Point firstPoint;

    public DrawController(Controller controller) {
        this.controller = controller;
        firstPoint = null;
        model = controller.getModel();
    }

    /**
     * Обработка клика мышки по канвасу
     * @param mouseEvent Эвент клика
     */
    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
        if(firstPoint == null){
            firstPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
        } else {
            Point newPoint = new Point(mouseEvent.getX(), mouseEvent.getY());
            model.addBar(new Bar(firstPoint, newPoint));
            firstPoint = newPoint;
            controller.getCanvas().redraw();
        }
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        if (firstPoint != null){
            controller.getCanvas().redraw();
            controller.getCanvas().getGraphicsContext2D().strokeLine(firstPoint.getX(), firstPoint.getY(),
                    mouseEvent.getX(), mouseEvent.getY());
        }
    }
}
