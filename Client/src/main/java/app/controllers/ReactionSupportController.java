package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 29.03.2017.
 */
public class ReactionSupportController implements Controllable {
    private Controller controller;

    public ReactionSupportController(Controller controller) {
        this.controller = controller;
    }

    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void disable() {
        controller.getSideMenu().setVisible(false);
        controller.getCanvas().setXLayout(0);
        controller.getCanvas().redraw();
    }

    @Override
    public void enable() {
        controller.getCanvas().setXLayout(controller.getSideMenu().getWidth());
        controller.getSideMenu().setVisible(true);
        controller.getCanvas().redraw();
    }
}
