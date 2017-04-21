package app.controllers;

import app.Controller;
import app.ReactionFactory;
import app.interfaces.Controllable;
import app.interfaces.ReactButton;
import app.model.Model;
import app.model.Point;
import app.reactions.Lock;
import app.utils.CoordinateUtils;
import javafx.event.ActionEvent;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 29.03.2017.
 */
public class ReactionSupportController implements Controllable {
    private Controller controller;
    private Model model;
    private CoordinateUtils utils;
    private ReactButton activeButton;
    private ToggleButton reac1;
    private ToggleButton reac2;
    private ToggleButton reac3;
    private ToggleButton reac4;

    public ReactionSupportController(Controller controller) {
        this.controller = controller;
        model = controller.getModel();
        utils = controller.getCoordinateUtils();
        reac1 = controller.getReac1();
        reac2 = controller.getReac2();
        reac3 = controller.getReac3();
        reac4 = controller.getReac4();
        reac1.setOnAction(event -> setAction(reac1, Lock.class));
    }

    private void setAction(ToggleButton button, Class<? extends ReactButton> lockClass) {
        if (button.isSelected()){
            activeButton = ReactionFactory.getInstance(lockClass, controller);
        }
    }

    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
        Point reactPoint = model.findNearbyPoint(utils.toRealX(mouseEvent.getX()), utils.toRealY(mouseEvent.getY()));
        if (reactPoint != null) {
            activeButton.setRotation(activeButton.getRotation(reactPoint, mouseEvent));
            reactPoint.setReaction(activeButton);
        }
        controller.getCanvas().redraw();
        activeButton = ReactionFactory.getInstance(activeButton.getClass(), controller);
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        controller.getCanvas().redraw(mouseEvent);
        Point reactPoint = model.findNearbyPoint(utils.toRealX(mouseEvent.getX()), utils.toRealY(mouseEvent.getY()));
        if (reactPoint != null) {
            activeButton.draw(reactPoint, mouseEvent);
        }
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
