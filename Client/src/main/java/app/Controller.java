package app;

import app.controllers.ControllerFactory;
import app.interfaces.Controllable;
import app.model.Model;
import app.model.Project;
import app.model.ProjectInfo;
import app.model.User;
import app.utils.CoordinateUtils;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.ws.rs.core.MediaType;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button loadProjectInfoButton;
    @FXML
    private ComboBox projectsComboBox;
    @FXML
    private Pane loadProjectPane;
    @FXML
    private TextField projectNameField;
    @FXML
    private Button createProjectButton;
    @FXML
    private Pane newProjectPane;
    @FXML
    private ToggleButton newProjectButton;
    @FXML
    private ToggleButton saveProjectButton;
    @FXML
    private ToggleButton loadProjectButton;
    @FXML
    private Label userLabel;
    @FXML
    private TextField loginField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button sendLogInButton;
    @FXML
    private Button cancelLogInButton;
    @FXML
    private Pane authPane;
    @FXML
    private ToggleButton authorizationButton;
    @FXML
    private Pane controlCalcPanel;
    @FXML
    private Pane controlProjectPanel;
    @FXML
    private ToggleButton projectButton;
    @FXML
    private ToggleButton modelButton;
    @FXML
    private Pane controlModelPanel;
    @FXML
    private ToggleButton calcButton;
    @FXML
    private AnchorPane menuPane;
    @FXML
    private ToggleButton mForceButton;
    @FXML
    private Button acceptForce;
    @FXML
    private ToggleButton vForceButton;
    @FXML
    private ToggleButton hForceButton;
    @FXML
    private TextField forceTextInput;
    @FXML
    private Pane forcePane;
    @FXML
    private ToggleButton cutView;
    @FXML
    private Button acceptCut;
    @FXML
    private Label rProperty;
    @FXML
    private Label sProperty;
    @FXML
    private Label hProperty;
    @FXML
    private Label bProperty;
    @FXML
    private Label tProperty;
    @FXML
    private ComboBox cutComboBox;
    @FXML
    private ImageView bigBeamImage;
    @FXML
    private ToggleButton iBeamButton;
    @FXML
    private ToggleButton tBeamButton;
    @FXML
    private ToggleButton cBeamButton;
    @FXML
    private ToggleButton oBeamButton;
    @FXML
    private Pane cutPane;
    @FXML
    private ToggleButton materialView;
    @FXML
    private Button acceptMaterial;
    @FXML
    private ComboBox materialComboBox;
    @FXML
    private ToggleButton steelButton;
    @FXML
    private ToggleButton woodButton;
    @FXML
    private Pane materialPane;
    @FXML
    private ToggleButton reac1;
    @FXML
    private ToggleButton reac2;
    @FXML
    private ToggleButton reac3;
    @FXML
    private ToggleButton reac4;
    @FXML
    private Pane reacPane;
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
    private ToggleButton cutButton;
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

    public Label getCoordinatesLabel() {
        return coordinatesLabel;
    }

    public Controllable getCurrentEventListener() {
        return currentEventListener;
    }

    private ResizableCanvas canvas;
    private Model model;
    private User currentUser;
    private Controllable currentEventListener;
    private CoordinateUtils coordinateUtils;

    public ResizableCanvas getCanvas() {
        return canvas;
    }

    public void initialize(URL location, ResourceBundle resources) {
        currentEventListener = ControllerFactory.getEmpty();

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

        materialView.setOnAction(event -> canvas.redraw());

        projectButton.setOnAction(event -> changeMenuButton());
        modelButton.setOnAction(event -> changeMenuButton());
        calcButton.setOnAction(event -> changeMenuButton());

        saveProjectButton.setOnAction(event -> save());


        authorizationButton.setOnAction(event -> changeEventListener(authorizationButton, ControllerFactory.getLoginController(this)));
        newProjectButton.setOnAction(event -> changeEventListener(newProjectButton, ControllerFactory.getCreateProjectController(this)));
        loadProjectButton.setOnAction(event -> changeEventListener(loadProjectButton, ControllerFactory.getLoadProjectController(this)));
    }

    private void save() {
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        Client client = Client.create(cfg);
        WebResource webResource = client.resource("http://localhost:8080/Server-1.0/save");

        System.out.println(model.getProject());

        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .entity(model.getProject())
                .header("sessionId", currentUser.getSessionId())
                .post(ClientResponse.class);

        if (response.getStatus() == 200) {
            System.out.println("done");
        }
    }

    public void activatePane(Pane pane) {
        canvas.setXLayout(pane.getWidth());
        pane.setVisible(true);
        canvas.redraw();
    }

    public void deactivatePane(Pane pane) {
        pane.setVisible(false);
        canvas.setXLayout(0);
        canvas.redraw();
    }

    private void changeMenuButton() {
        controlProjectPanel.setVisible(projectButton.isSelected());
        controlModelPanel.setVisible(modelButton.isSelected());
        controlCalcPanel.setVisible(calcButton.isSelected());
    }

    public void drawOn(ActionEvent actionEvent) {
        changeEventListener(drawButton, ControllerFactory.getDrawController(this));
    }

    public void reactionSupportOn(ActionEvent actionEvent) {
        changeEventListener(reactionSupportButton, ControllerFactory.getReactionSupportController(this));
    }

    public void cutButtonOn(ActionEvent actionEvent) {
        changeEventListener(cutButton, ControllerFactory.getCutController(this));
    }

    public void materialButtonOn(ActionEvent actionEvent) {
        changeEventListener(materialButton, ControllerFactory.getMaterialController(this));
    }

    public void onForceButtonOn(ActionEvent actionEvent) {
        changeEventListener(forceButton, ControllerFactory.getForceController(this));
    }

    public void clearEventListener() {
        changeEventListener(null, ControllerFactory.getEmpty());
    }

    private void changeEventListener(ToggleButton toggleButton, Controllable eventListener) {
        currentEventListener.disable();
        if (toggleButton != null && toggleButton.isSelected()) {
            currentEventListener = eventListener;
            currentEventListener.enable();
        } else {
            currentEventListener = ControllerFactory.getEmpty();
        }
        canvas.redraw();
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

    private void organizeStatusBar() {
        scaleLabel.setLayoutX(statusBar.getWidth() - 50);
        coordinatesLabel.setLayoutX(statusBar.getWidth() - 180);
        net.setLayoutX(statusBar.getWidth() - 230);
        orto.setLayoutX(statusBar.getWidth() - 270);
        pivot.setLayoutX(statusBar.getWidth() - 325);
        materialView.setLayoutX(statusBar.getWidth() - 344);
        cutView.setLayoutX(statusBar.getWidth() - 361);
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

    public Pane getReacPane() {
        return reacPane;
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

    public void setStatus(String s) {
        statusLabel.setText(s);
    }

    public Label getStatusLabel() {
        return statusLabel;
    }

    public Pane getMaterialPane() {
        return materialPane;
    }

    public ToggleButton getWoodButton() {
        return woodButton;
    }

    public ToggleButton getSteelButton() {
        return steelButton;
    }

    public ComboBox getMaterialComboBox() {
        return materialComboBox;
    }

    public Button getAcceptMaterial() {
        return acceptMaterial;
    }

    public ToggleButton getMaterialView() {
        return materialView;
    }

    public Pane getCutPane() {
        return cutPane;
    }

    public ToggleButton getiBeamButton() {
        return iBeamButton;
    }

    public ToggleButton gettBeamButton() {
        return tBeamButton;
    }

    public ToggleButton getcBeamButton() {
        return cBeamButton;
    }

    public ToggleButton getoBeamButton() {
        return oBeamButton;
    }

    public ImageView getBigBeamImage() {
        return bigBeamImage;
    }

    public ComboBox getCutComboBox() {
        return cutComboBox;
    }

    public Label getrProperty() {
        return rProperty;
    }

    public Label getsProperty() {
        return sProperty;
    }

    public Label gethProperty() {
        return hProperty;
    }

    public Label getbProperty() {
        return bProperty;
    }

    public Label gettProperty() {
        return tProperty;
    }

    public Button getAcceptCut() {
        return acceptCut;
    }

    public ToggleButton getCutView() {
        return cutView;
    }

    public Pane getForcePane() {
        return forcePane;
    }

    public TextField getForceTextInput() {
        return forceTextInput;
    }

    public ToggleButton getvForceButton() {
        return vForceButton;
    }

    public ToggleButton gethForceButton() {
        return hForceButton;
    }

    public Button getAcceptForce() {
        return acceptForce;
    }

    public ToggleButton getmForceButton() {
        return mForceButton;
    }

    public Pane getAuthPane() {
        return authPane;
    }

    public Button getSendLogInButton() {
        return sendLogInButton;
    }

    public Button getCancelLogInButton() {
        return cancelLogInButton;
    }

    public Label getUserLabel() {
        return userLabel;
    }

    public TextField getLoginField() {
        return loginField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public ToggleButton getNewProjectButton() {
        return newProjectButton;
    }

    public ToggleButton getSaveProjectButton() {
        return saveProjectButton;
    }

    public ToggleButton getLoadProjectButton() {
        return loadProjectButton;
    }

    public void setCurrentUser(User pojo) {
        currentUser = pojo;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Pane getNewProjectPane() {
        return newProjectPane;
    }

    public TextField getProjectNameField() {
        return projectNameField;
    }

    public Button getCreateProjectButton() {
        return createProjectButton;
    }

    public ComboBox getProjectsComboBox() {
        return projectsComboBox;
    }

    public Pane getLoadProjectPane() {
        return loadProjectPane;
    }

    public Button getLoadProjectInfoButton() {
        return loadProjectInfoButton;
    }
}
