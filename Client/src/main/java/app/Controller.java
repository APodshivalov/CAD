package app;

import app.controllers.ControllerFactory;
import app.interfaces.Controllable;
import app.model.Model;
import app.utils.CoordinateUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable{

    @FXML
    private AnchorPane canvasPane;
    @FXML
    private Pane statusBar;
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
    @FXML
    private ToggleButton pivot;
    @FXML
    private ToggleButton orto;
    @FXML
    private ToggleButton net;
    @FXML
    private Label coordinatesLabel;

    private ResizableCanvas canvas;
    private Model model;
    private Controllable currentEventListener;
    private CoordinateUtils coordinateUtils;
    private double scale;
    private double x;
    private double y;

    public ResizableCanvas getCanvas() {
        return canvas;
    }

    public void initialize(URL location, ResourceBundle resources) {
        scale = 1;

        ToggleGroup modelGroup = new ToggleGroup();
        drawButton.setToggleGroup(modelGroup);
        reactionSupportButton.setToggleGroup(modelGroup);
        materialButton.setToggleGroup(modelGroup);
        forceButton.setToggleGroup(modelGroup);
        sectionButton.setToggleGroup(modelGroup);

        canvas = new ResizableCanvas(this);
        canvasPane.getChildren().add(canvas);
        canvasPane.widthProperty().addListener(evt -> canvas.redraw());
        canvasPane.heightProperty().addListener(evt -> canvas.redraw());
        statusBar.widthProperty().addListener(evt -> organizeStatusBar());

        model = new Model(this);
        coordinateUtils = new CoordinateUtils(this);
    }

    public void drawOn(ActionEvent actionEvent) {
        changeEventListener(drawButton, ControllerFactory.getDrawController(this));
    }

    private void changeEventListener(ToggleButton toggleButton, Controllable eventListener) {
        if(currentEventListener != null){
            currentEventListener.disable();
        }
        if(toggleButton.isSelected()){
            currentEventListener = eventListener;
            currentEventListener.enable();
        } else {
            currentEventListener = null;
        }
    }

    public void reactionSupportOn(ActionEvent actionEvent) {
        changeEventListener(reactionSupportButton, ControllerFactory.getReactionSupportController(this));
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
        currentEventListener.onMouseClickedOverCanvas(mouseEvent);
    }

    public void onMouseMoved(MouseEvent mouseEvent) {
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        currentEventListener.onMouseMoved(mouseEvent);
        coordinatesLabel.setText("x: " + coordinateUtils.toRealX(x) +
                ", y: " + coordinateUtils.toRealY(y));
    }

    public void onMouseExited(MouseEvent mouseEvent) {
        canvas.redraw();
    }

    private void organizeStatusBar() {
        coordinatesLabel.setLayoutX(statusBar.getWidth()-110);
        net.setLayoutX(statusBar.getWidth()-170);
        orto.setLayoutX(statusBar.getWidth()-210);
        pivot.setLayoutX(statusBar.getWidth()-265);
    }

    public AnchorPane getCanvasPane() {
        return canvasPane;
    }

    public Model getModel() {
        return model;
    }

    public ToggleButton getPivot() {
        return pivot;
    }

    public ToggleButton getOrto() {
        return orto;
    }

    public ToggleButton getNet() {
        return net;
    }

    public double getScale() {
        return scale;
    }

    public CoordinateUtils getCoordinateUtils() {
        return coordinateUtils;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setScale(int scale) {
        this.scale = scale;
    }

}
