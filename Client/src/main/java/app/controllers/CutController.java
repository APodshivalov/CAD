package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 25.04.2017.
 */
public class CutController implements Controllable{
    private Controller controller;

    public CutController(Controller controller) {
        this.controller = controller;
    }

    private void loadFromCloud() {

    }

    @Override
    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {

    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void disable() {
        controller.getCutPane().setVisible(false);
        controller.getCanvas().setXLayout(0);
        controller.getCanvas().redraw();
    }

    @Override
    public void enable() {
        controller.getCanvas().setXLayout(controller.getCutPane().getWidth());
        controller.getCutPane().setVisible(true);
        controller.getCanvas().redraw();
    }
}
