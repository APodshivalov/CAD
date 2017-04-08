package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;


/**
 * Created by APodshivalov on 29.03.2017.
 */
public class DrawController implements Controllable{
    private Controller controller;
    private GraphicsContext gc;

    public DrawController(Controller controller) {
        this.controller = controller;
        gc = controller.getGraphicsContext();
    }

    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
        gc.strokeLine(0,0, mouseEvent.getX(), mouseEvent.getY());
    }
}
