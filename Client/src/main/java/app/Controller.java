package app;

import app.controllers.ControllerFactory;
import app.interfaces.Controllable;
import app.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private AnchorPane canvasPane;
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
    private Label statusLabel;

    private ResizableCanvas canvas;
    private GraphicsContext gc;
    private Model model;
    private Controllable controller;

    public ResizableCanvas getCanvas() {
        return canvas;
    }

    public void initialize(URL location, ResourceBundle resources) {
        ToggleGroup modelGroup = new ToggleGroup();
        drawButton.setToggleGroup(modelGroup);
        reactionSupportButton.setToggleGroup(modelGroup);
        materialButton.setToggleGroup(modelGroup);
        forceButton.setToggleGroup(modelGroup);
        sectionButton.setToggleGroup(modelGroup);

        canvas = new ResizableCanvas(this);
        gc = canvas.getGraphicsContext2D();

        model = new Model(this);
    }

    public void drawOn(ActionEvent actionEvent) {
        if(drawButton.isSelected()){
            controller = ControllerFactory.getDrawController(this);
        } else {
            controller = null;
        }
    }

    public void reactionSupportOn(ActionEvent actionEvent) {
        if(reactionSupportButton.isSelected()){
            controller = ControllerFactory.getReactionSupportController(this);
        } else {
            controller = null;
        }

    }

    public void onMouseEnteredDraw(MouseEvent mouseEvent) {
        statusLabel.setText("Рисование");
    }

    public void onMouseExitedDraw(MouseEvent mouseEvent) {
        statusLabel.setText("");
    }

    public void onMouseEnteredSupport(MouseEvent mouseEvent) {
        statusLabel.setText("Опоры");
    }

    public void onMouseExitedSupport(MouseEvent mouseEvent) {
        statusLabel.setText("");
    }

    public void onMouseEnteredMaterial(MouseEvent mouseEvent) {
        statusLabel.setText("Материалы");
    }

    public void onMouseExitedMaterial(MouseEvent mouseEvent) {
        statusLabel.setText("");
    }

    public void onMouseEnteredForce(MouseEvent mouseEvent) {
        statusLabel.setText("Нагрузки");
    }

    public void onMouseExitedForce(MouseEvent mouseEvent) {
        statusLabel.setText("");
    }

    public void onMouseEnteredSection(MouseEvent mouseEvent) {
        statusLabel.setText("Поперечное сечение стержня");
    }

    public void onMouseExitedSection(MouseEvent mouseEvent) {
        statusLabel.setText("");
    }

    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {
        controller.onMouseClickedOverCanvas(mouseEvent);
    }

    public GraphicsContext getGraphicsContext() {
        return gc;
    }

    public AnchorPane getCanvasPane() {
        return canvasPane;
    }

    public Model getModel() {
        return model;
    }
}
