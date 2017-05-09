package app.controllers;

import app.Controller;
import app.ImageFactory;
import app.ReactionHelper;
import app.interfaces.Controllable;
import app.model.Model;
import app.model.Point;
import app.model.Reaction;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 29.03.2017.
 */
public class ReactionSupportController implements Controllable {
    private Controller controller;
    private Model model;
    private ToggleButton reac1;
    private ToggleButton reac2;
    private ToggleButton reac3;
    private ToggleButton reac4;
    private Reaction currentReaction;

    public ReactionSupportController(Controller controller) {
        this.controller = controller;
        model = controller.getModel();
        reac1 = controller.getReac1();
        reac2 = controller.getReac2();
        reac3 = controller.getReac3();
        reac4 = controller.getReac4();

        reac1.setOnAction(event -> setAction(reac1, "Reac1"));
        reac1.setOnMouseEntered(event -> controller.setStatus("Жесткое закрепление"));
        reac1.setOnMouseExited(event -> controller.setStatus(""));

        reac2.setOnAction(event -> setAction(reac2, "Reac2"));
        reac2.setOnMouseEntered(event -> controller.setStatus("Шарнирно-неподвижная опора"));
        reac2.setOnMouseExited(event -> controller.setStatus(""));

        reac3.setOnAction(event -> setAction(reac3, "Reac3"));
        reac3.setOnMouseEntered(event -> controller.setStatus("Шарнирно-подвижная опора"));
        reac3.setOnMouseExited(event -> controller.setStatus(""));

        reac4.setOnAction(event -> setAction(reac4, "Reac0"));
        reac4.setOnMouseEntered(event -> controller.setStatus("Узел"));
        reac4.setOnMouseExited(event -> controller.setStatus(""));
    }

    private void setAction(ToggleButton button, String type) {
        if (button.isSelected()){
            currentReaction = new Reaction(type);
        } else {
            currentReaction = null;
        }
    }

    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
        if (currentReaction != null){
            Point reactPoint = model.findNearbyPoint(mouseEvent.getX(), mouseEvent.getY());
            if (reactPoint != null) {
                currentReaction.setAngle(ReactionHelper.getRotation(reactPoint, mouseEvent, controller));
                reactPoint.setReaction(currentReaction);
            }
            controller.getCanvas().redraw();
            currentReaction = new Reaction(currentReaction.getName());
        }
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        controller.getCanvas().redraw(mouseEvent);
        Point reactPoint = model.findNearbyPoint(mouseEvent.getX(), mouseEvent.getY());
        if (reactPoint != null && currentReaction != null) {
            currentReaction.draw(reactPoint, mouseEvent, controller);
        }
    }

    @Override
    public void redraw() {

    }

    @Override
    public void disable() {
        controller.deactivatePane(controller.getReacPane());
    }

    @Override
    public void enable() {
        controller.activatePane(controller.getReacPane());
    }
}
