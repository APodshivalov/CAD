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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private ToggleButton reac1;
    @FXML
    private ToggleButton reac2;
    @FXML
    private ToggleButton reac3;
    @FXML
    private ToggleButton reac4;
    @FXML
    private Pane sideMenu;
    @FXML
    private Label scaleLabel;
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

    public Label getCoordinatesLabel() {
        return coordinatesLabel;
    }

    public Controllable getCurrentEventListener() {
        return currentEventListener;
    }

    @FXML
    private Label coordinatesLabel;

    private ResizableCanvas canvas;
    private Model model;
    private Controllable currentEventListener;
    private CoordinateUtils coordinateUtils;
    private ToggleGroup reactButtons;

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

        reactButtons = new ToggleGroup();
        reac1.setToggleGroup(reactButtons);
        reac2.setToggleGroup(reactButtons);
        reac3.setToggleGroup(reactButtons);
        reac4.setToggleGroup(reactButtons);

        canvas = new ResizableCanvas(this);
        canvasPane.getChildren().add(canvas);
        canvasPane.widthProperty().addListener(evt -> canvas.redraw());
        canvasPane.heightProperty().addListener(evt -> canvas.redraw());
        statusBar.widthProperty().addListener(evt -> organizeStatusBar());

        model = new Model(this);
        coordinateUtils = new CoordinateUtils(this);
        scaleLabel.setText("M 1:1");
        coordinatesLabel.setText(String.format("x: %-6.2f, y: %-6.2f", coordinateUtils.toRealX(), coordinateUtils
                .toRealY()));
    }

    public void drawOn(ActionEvent actionEvent) {
        changeEventListener(drawButton, ControllerFactory.getDrawController(this));
    }

    public void reactionSupportOn(ActionEvent actionEvent) {
        changeEventListener(reactionSupportButton, ControllerFactory.getReactionSupportController(this));
    }

    private void changeEventListener(ToggleButton toggleButton, Controllable eventListener) {
        if (currentEventListener != null) {
            currentEventListener.disable();
        }
        if (toggleButton.isSelected()) {
            currentEventListener = eventListener;
            currentEventListener.enable();
        } else {
            currentEventListener = null;
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
        currentEventListener.onMouseClickedOverCanvas(mouseEvent);
    }

    public void onMouseMoved(MouseEvent mouseEvent) {
        coordinateUtils.onMouseMoved(mouseEvent);
    }

    public void onMouseExited(MouseEvent mouseEvent) {
        canvas.redraw();
    }

    private void organizeStatusBar() {
        scaleLabel.setLayoutX(statusBar.getWidth() - 50);
        coordinatesLabel.setLayoutX(statusBar.getWidth() - 180);
        net.setLayoutX(statusBar.getWidth() - 230);
        orto.setLayoutX(statusBar.getWidth() - 270);
        pivot.setLayoutX(statusBar.getWidth() - 325);
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

    public CoordinateUtils getCoordinateUtils() {
        return coordinateUtils;
    }

    public void onMouseDrag(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.PRIMARY
                && currentEventListener != ControllerFactory.getDrawController(this)) {
            double x = Math.min(coordinateUtils.getX(), mouseEvent.getX());
            double y = Math.min(coordinateUtils.getY(), mouseEvent.getY());
            double width = Math.abs(mouseEvent.getX() - coordinateUtils.getX());
            double height = Math.abs(mouseEvent.getY() - coordinateUtils.getY());
            model.select(coordinateUtils.toRealX(x),
                    coordinateUtils.toRealY(y),
                    coordinateUtils.toRealX(x + width),
                    coordinateUtils.toRealY(y + height));
            canvas.redraw();
            canvas.getGraphicsContext2D().setStroke(Color.RED);
            canvas.getGraphicsContext2D().strokeRect(x, y, width, height);
            canvas.getGraphicsContext2D().setStroke(Color.BLACK);
        }
        if (mouseEvent.getButton() == MouseButton.MIDDLE) {
            coordinateUtils.onMouseDrag(mouseEvent);
        }
    }

    public void onMouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.MIDDLE) {
            coordinateUtils.onMouseReleased();
        }
        canvas.redraw();
    }

    public void onScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            coordinateUtils.prevScale();
        } else {
            coordinateUtils.nextScale();
        }
        scaleLabel.setText(coordinateUtils.getScale());
        canvas.redraw();
    }

    public void onNetAction(ActionEvent actionEvent) {
        canvas.redraw();
    }

    public Pane getSideMenu() {
        return sideMenu;
    }

    public ToggleButton getReac1() {
        return reac1;
    }

    public ToggleButton getReac2() {
        return reac2;
    }

    public ToggleButton getReac3() {
        return reac3;
    }

    public ToggleButton getReac4() {
        return reac4;
    }

    public ToggleGroup getReactButtons() {
        return reactButtons;
    }

    public void setStatus(String s) {
        statusLabel.setText(s);
    }
}
