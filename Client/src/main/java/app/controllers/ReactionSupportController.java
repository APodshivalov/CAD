package app.controllers;

import app.Controller;
import app.ReactionFactory;
import app.interfaces.Controllable;
import app.interfaces.ReactButton;
import app.model.Model;
import app.model.Point;
import app.reactions.EmptyReaction;
import app.reactions.Lock;
import app.reactions.SharLock;
import app.reactions.SharNoLock;
import app.utils.CoordinateUtils;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 29.03.2017.
 */
public class ReactionSupportController implements Controllable {
    private Controller controller;
    private Model model;
    private ReactButton activeButton;
    private ToggleButton reac1;
    private ToggleButton reac2;
    private ToggleButton reac3;
    private ToggleButton reac4;

    public ReactionSupportController(Controller controller) {
        this.controller = controller;
        model = controller.getModel();
        reac1 = controller.getReac1();
        reac2 = controller.getReac2();
        reac3 = controller.getReac3();
        reac4 = controller.getReac4();

        reac1.setOnAction(event -> setAction(reac1, Lock.class));
        reac1.setOnMouseEntered(event -> controller.setStatus("Жесткое закрепление"));
        reac1.setOnMouseExited(event -> controller.setStatus(""));

        reac2.setOnAction(event -> setAction(reac2, SharLock.class));
        reac2.setOnMouseEntered(event -> controller.setStatus("Шарнирно-неподвижная опора"));
        reac2.setOnMouseExited(event -> controller.setStatus(""));

        reac3.setOnAction(event -> setAction(reac3, SharNoLock.class));
        reac3.setOnMouseEntered(event -> controller.setStatus("Шарнирно-подвижная опора"));
        reac3.setOnMouseExited(event -> controller.setStatus(""));

        reac4.setOnAction(event -> setAction(reac4, EmptyReaction.class));
        reac4.setOnMouseEntered(event -> controller.setStatus("Узел"));
        reac4.setOnMouseExited(event -> controller.setStatus(""));
    }

    private void setAction(ToggleButton button, Class<? extends ReactButton> lockClass) {
        if (button.isSelected()){
            activeButton = ReactionFactory.getInstance(lockClass, controller);
        } else {
            activeButton = null;
        }
    }

    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
        if (activeButton != null){
            Point reactPoint = model.findNearbyPoint(mouseEvent.getX(), mouseEvent.getY());
            if (reactPoint != null) {
                activeButton.setRotation(ReactionFactory.getRotation(reactPoint, mouseEvent, controller));
                reactPoint.setReaction(activeButton);
            }
            controller.getCanvas().redraw();
            activeButton = ReactionFactory.getInstance(activeButton.getClass(), controller);
        }
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        controller.getCanvas().redraw(mouseEvent);
        Point reactPoint = model.findNearbyPoint(mouseEvent.getX(), mouseEvent.getY());
        if (reactPoint != null && activeButton != null) {
            activeButton.draw(reactPoint, mouseEvent);
        }
    }

    @Override
    public void disable() {
        controller.getReacPane().setVisible(false);
        controller.getCanvas().setXLayout(0);
        controller.getCanvas().redraw();
    }

    @Override
    public void enable() {
        controller.getCanvas().setXLayout(controller.getReacPane().getWidth());
        controller.getReacPane().setVisible(true);
        controller.getCanvas().redraw();
    }
}
