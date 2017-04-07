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
    private ToggleButton drawButton;
    @FXML
    private ToggleButton reactionSupportButton;
    @FXML
    private ToggleButton sectionButton;
    @FXML
    private ToggleButton materialButton;
    @FXML
    private ToggleButton forceButton;
    @FXML
    private Label helperLabel;

    private Controllable controller;

    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup modelGroup = new ToggleGroup();
        drawButton.setToggleGroup(modelGroup);
        reactionSupportButton.setToggleGroup(modelGroup);
        materialButton.setToggleGroup(modelGroup);
        forceButton.setToggleGroup(modelGroup);
        sectionButton.setToggleGroup(modelGroup);
    }

    public void drawOn(ActionEvent actionEvent) {
        controller = ControllerFactory.getDrawController(this);
    }

    public void reactionSupportOn(ActionEvent actionEvent) {
        controller = ControllerFactory.getReactionSupportController(this);
    }

    public void onMouseEnteredDraw(MouseEvent mouseEvent) {
        helperLabel.setText("Рисование");
    }

    public void onMouseExitedDraw(MouseEvent mouseEvent) {
        helperLabel.setText("");
    }

    public void onMouseEnteredSupport(MouseEvent mouseEvent) {
        helperLabel.setText("Опоры");
    }

    public void onMouseExitedSupport(MouseEvent mouseEvent) {
        helperLabel.setText("");
    }

    public void onMouseEnteredMaterial(MouseEvent mouseEvent) {
        helperLabel.setText("Материалы");
    }

    public void onMouseExitedMaterial(MouseEvent mouseEvent) {
        helperLabel.setText("");
    }

    public void onMouseEnteredForce(MouseEvent mouseEvent) {
        helperLabel.setText("Нагрузки");
    }

    public void onMouseExitedForce(MouseEvent mouseEvent) {
        helperLabel.setText("");
    }

    public void onMouseEnteredSection(MouseEvent mouseEvent) {
        helperLabel.setText("Поперечное сечение стержня");
    }

    public void onMouseExitedSection(MouseEvent mouseEvent) {
        helperLabel.setText("");
    }
}
