package app;

import app.controllers.ControllerFactory;
import app.interfaces.Controllable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private ToggleButton drawMode;
    @FXML
    private ToggleButton reactionSupport;
    @FXML
    private Label labelTest;

    private Controllable controller;


    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup modelGroup = new ToggleGroup();
        drawMode.setToggleGroup(modelGroup);
        reactionSupport.setToggleGroup(modelGroup);
    }

    public void drawOn(ActionEvent actionEvent) {
        controller = ControllerFactory.getDrawController(this);
    }

    public void reactionSupportOn(ActionEvent actionEvent) {
        controller = ControllerFactory.getReactionSupportController(this);
    }

    public Label getLabel() {
        return labelTest;
    }

    public void onMouseEntredDraw(MouseEvent mouseEvent) {
        labelTest.setText("Рисование");
    }

    public void onMouseExitedDraw(MouseEvent mouseEvent) {
        labelTest.setText("");
    }

    public void onMouseEntredSupport(MouseEvent mouseEvent) {
        labelTest.setText("Опоры");
    }

    public void onMouseExitedSupport(MouseEvent mouseEvent) {
        labelTest.setText("");
    }
}
