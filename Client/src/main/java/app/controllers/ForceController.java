package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.Force;
import app.model.Point;
import app.utils.CoordinateUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 30.04.2017.
 */
public class ForceController implements Controllable {
    private Controller controller;
    private TextField textField;
    private ToggleButton vForceButton;
    private ToggleButton hForceButton;
    private ToggleButton mForceButton;
    private Button acceptForceButton;
    private Force currentForce;

    public ForceController(Controller controller) {
        this.controller = controller;

        textField = controller.getForceTextInput();
        textField.setDisable(true);
        TextFormatter<String> textFormatter = getTextFormatter();
        textField.setTextFormatter(textFormatter);

        vForceButton = controller.getvForceButton();
        hForceButton = controller.gethForceButton();
        mForceButton = controller.getmForceButton();

        vForceButton.setOnAction(event -> activateForceEditor(vForceButton));
        hForceButton.setOnAction(event -> activateForceEditor(hForceButton));
        mForceButton.setOnAction(event -> activateForceEditor(mForceButton));

        ToggleGroup forcesButtons = new ToggleGroup();
        vForceButton.setToggleGroup(forcesButtons);
        hForceButton.setToggleGroup(forcesButtons);
        mForceButton.setToggleGroup(forcesButtons);

        currentForce = new Force();

        acceptForceButton = controller.getAcceptForce();
        acceptForceButton.setOnAction(event -> acceptForce());
    }

    private void acceptForce() {
        if (vForceButton.isSelected() && !textField.getText().isEmpty()) {
            currentForce = new Force(Integer.parseInt(textField.getText()), 0, 0);
        }
        if (hForceButton.isSelected() && !textField.getText().isEmpty()) {
            currentForce = new Force(0, Integer.parseInt(textField.getText()), 0);
        }
        if (mForceButton.isSelected() && !textField.getText().isEmpty()) {
            currentForce = new Force(0, 0, Integer.parseInt(textField.getText()));
        }
    }

    private void activateForceEditor(ToggleButton forceButton) {
        textField.setDisable(!forceButton.isSelected());
        textField.setText("");
        currentForce = new Force();
    }

    private TextFormatter<String> getTextFormatter() {
        return new TextFormatter<>(change -> {
            String text = change.getText();
            if (!change.isContentChange()) {
                return change;
            }
            if (text.matches("[0-9]") || text.isEmpty()) {
                return change;
            }
            if (text.matches("-") && textField.getText().isEmpty() ) {
                return change;
            }
            return null;
        });
    }

    @Override
    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
        if (vForceButton.isSelected() || hForceButton.isSelected() || mForceButton.isSelected()) {
            Point point = controller.getModel().findNearbyPoint(mouseEvent.getX(), mouseEvent.getY());
            if (point != null) {
                point.getForce().add(currentForce);
            }
        }
        controller.getCanvas().redraw();
    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {
        if (vForceButton.isSelected() || hForceButton.isSelected() || mForceButton.isSelected()) {
            controller.getCanvas().redraw();
            Point point = controller.getModel().findNearbyPoint(mouseEvent.getX(), mouseEvent.getY());
            GraphicsContext gc = controller.getCanvas().getGraphicsContext2D();
            CoordinateUtils utils = controller.getCoordinateUtils();
            if (point != null) {
                currentForce.draw(gc, utils, point);
            }
        }
    }

    @Override
    public void redraw() {

    }

    @Override
    public void disable() {
        controller.getForcePane().setVisible(false);
        controller.getCanvas().setXLayout(0);
        controller.getCanvas().redraw();
    }

    @Override
    public void enable() {
        controller.getCanvas().setXLayout(controller.getForcePane().getWidth());
        controller.getForcePane().setVisible(true);
        controller.getCanvas().redraw();
    }
}
