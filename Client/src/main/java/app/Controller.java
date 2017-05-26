package app;

import app.controllers.ControllerFactory;
import app.interfaces.Controllable;
import app.model.*;
import app.utils.CoordinateUtils;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
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

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    public static String host = "localhost";
    private Client client;

    @FXML
    private ToggleButton nView;
    @FXML
    private ToggleButton qView;
    @FXML
    private ToggleButton mView;
    @FXML
    private Pane resultPane;
    @FXML
    private TableColumn<Result, String> idColumn;
    @FXML
    private TableColumn<Result, Number> n1Column;
    @FXML
    private TableColumn<Result, Number> q1Column;
    @FXML
    private TableColumn<Result, Number> m1Column;
    @FXML
    private TableColumn<Result, Number> n2Column;
    @FXML
    private TableColumn<Result, Number> q2Column;
    @FXML
    private TableColumn<Result, Number> m2Column;
    @FXML
    private TableView<Result> resultTable;
    @FXML
    private ToggleButton tableView;
    @FXML
    private ToggleButton calculationResultButton;
    @FXML
    private Label calculationResultLabel;
    @FXML
    private Button calculateButton;
    @FXML
    private Label checkForceLabel;
    @FXML
    private Label checkReactionLabel;
    @FXML
    private Label checkCutLabel;
    @FXML
    private Label checkMaterialLabel;
    @FXML
    private Label checkFreePointsLabel;
    @FXML
    private Button checkModelRefreshButton;
    @FXML
    private Pane checkPane;
    @FXML
    private ToggleButton checkModelButton;
    @FXML
    private Label loginLabel;
    @FXML
    private ToggleGroup projectGroup;
    @FXML
    private ToggleGroup modelGroup;
    @FXML
    private ToggleGroup calcGroup;
    @FXML
    private Label loadLabel;
    @FXML
    private Pane savePane;
    @FXML
    private Button saveButton;
    @FXML
    private Label saveLabel;
    @FXML
    private Button loadProjectInfoButton;
    @FXML
    private ComboBox<ProjectInfo> projectsComboBox;
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
    private ComboBox<Material> materialComboBox;
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
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        client = Client.create(cfg);

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
        cutView.setOnAction(event -> canvas.redraw());

        drawButton.setOnAction(event -> changeEventListener(drawButton, ControllerFactory.getDrawController(this)));
        reactionSupportButton.setOnAction(event -> changeEventListener(reactionSupportButton, ControllerFactory.getReactionSupportController(this)));
        cutButton.setOnAction(event -> changeEventListener(cutButton, ControllerFactory.getCutController(this)));
        materialButton.setOnAction(event -> changeEventListener(materialButton, ControllerFactory.getMaterialController(this)));
        forceButton.setOnAction(event -> changeEventListener(forceButton, ControllerFactory.getForceController(this)));

        net.setOnAction(event -> canvas.redraw());

        projectButton.setOnAction(event -> changeMenuButton());
        modelButton.setOnAction(event -> changeMenuButton());
        calcButton.setOnAction(event -> changeMenuButton());

        authorizationButton.setOnAction(event -> changeEventListener(authorizationButton, ControllerFactory.getLoginController(this)));
        newProjectButton.setOnAction(event -> changeEventListener(newProjectButton, ControllerFactory.getCreateProjectController(this)));
        saveProjectButton.setOnAction(event -> changeEventListener(saveProjectButton, ControllerFactory.getSaveProjectController(this)));
        loadProjectButton.setOnAction(event -> changeEventListener(loadProjectButton, ControllerFactory.getLoadProjectController(this)));

        checkModelButton.setOnAction(event -> changeEventListener(checkModelButton, ControllerFactory.getCheckModelController(this)));
        calculationResultButton.setOnAction(event -> changeEventListener(calculationResultButton, ControllerFactory.getResultController(this)));

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
        currentEventListener.disable();

        setLabelFill();

        projectGroup.selectToggle(null);
        modelGroup.selectToggle(null);
        calcGroup.selectToggle(null);


        checkFreePointsLabel.setTextFill(Color.BLACK);
        checkMaterialLabel.setTextFill(Color.BLACK);
        checkCutLabel.setTextFill(Color.BLACK);
        checkForceLabel.setTextFill(Color.BLACK);
        checkReactionLabel.setTextFill(Color.BLACK);
        checkFreePointsLabel.setText("Свободные узлы:");
        checkMaterialLabel.setText("Материал:");
        checkCutLabel.setText("Сечение:");
        checkForceLabel.setText("Нагрузки:");
        checkReactionLabel.setText("Опоры:");
        calculateButton.setDisable(true);
        calculationResultButton.setDisable(true);
        tableView.setSelected(false);
        qView.setSelected(false);
        nView.setSelected(false);
        mView.setSelected(false);

        controlProjectPanel.setVisible(projectButton.isSelected());
        controlModelPanel.setVisible(modelButton.isSelected());
        controlCalcPanel.setVisible(calcButton.isSelected());

        canvas.redraw();
    }

    private void setLabelFill() {
        drawButton.setOnMouseEntered(event -> statusLabel.setText("Стержни"));
        drawButton.setOnMouseExited(event -> statusLabel.setText(""));
        reactionSupportButton.setOnMouseEntered(event -> statusLabel.setText("Опоры"));
        reactionSupportButton.setOnMouseExited(event -> statusLabel.setText(""));
        cutButton.setOnMouseEntered(event -> statusLabel.setText("Поперечное сечение"));
        cutButton.setOnMouseExited(event -> statusLabel.setText(""));
        materialButton.setOnMouseEntered(event -> statusLabel.setText("Материалы"));
        materialButton.setOnMouseExited(event -> statusLabel.setText(""));
        forceButton.setOnMouseEntered(event -> statusLabel.setText("Нагрузки"));
        forceButton.setOnMouseExited(event -> statusLabel.setText(""));

        authorizationButton.setOnMouseEntered(event -> statusLabel.setText("Авторизация"));
        authorizationButton.setOnMouseExited(event -> statusLabel.setText(""));
        newProjectButton.setOnMouseEntered(event -> statusLabel.setText("Новый прокт"));
        newProjectButton.setOnMouseExited(event -> statusLabel.setText(""));
        saveProjectButton.setOnMouseEntered(event -> statusLabel.setText("Сохранение"));
        saveProjectButton.setOnMouseExited(event -> statusLabel.setText(""));
        loadProjectButton.setOnMouseEntered(event -> statusLabel.setText("Загрузка"));
        loadProjectButton.setOnMouseExited(event -> statusLabel.setText(""));

        iBeamButton.setOnMouseEntered(event -> statusLabel.setText("Двутавр"));
        iBeamButton.setOnMouseExited(event -> statusLabel.setText(""));
        tBeamButton.setOnMouseEntered(event -> statusLabel.setText("Тавр"));
        tBeamButton.setOnMouseExited(event -> statusLabel.setText(""));
        cBeamButton.setOnMouseEntered(event -> statusLabel.setText("Швеллер"));
        cBeamButton.setOnMouseExited(event -> statusLabel.setText(""));
        oBeamButton.setOnMouseEntered(event -> statusLabel.setText("Брус"));
        oBeamButton.setOnMouseExited(event -> statusLabel.setText(""));

        qView.setOnMouseEntered(event -> statusLabel.setText("Эпюра продольных сил"));
        qView.setOnMouseExited(event -> statusLabel.setText(""));
        nView.setOnMouseEntered(event -> statusLabel.setText("Эпюра поперечных сил"));
        nView.setOnMouseExited(event -> statusLabel.setText(""));
        mView.setOnMouseEntered(event -> statusLabel.setText("Эпюра изгибающего момента"));
        mView.setOnMouseExited(event -> statusLabel.setText(""));
        tableView.setOnMouseEntered(event -> statusLabel.setText("Таблица результатов"));
        tableView.setOnMouseExited(event -> statusLabel.setText(""));

        checkModelButton.setOnMouseEntered(event -> statusLabel.setText("Проверка модели"));
        checkModelButton.setOnMouseExited(event -> statusLabel.setText(""));
        calculationResultButton.setOnMouseEntered(event -> statusLabel.setText("Результат"));
        calculationResultButton.setOnMouseExited(event -> statusLabel.setText(""));

        vForceButton.setOnMouseEntered(event -> statusLabel.setText("Вертикальная нагрузка (H)"));
        vForceButton.setOnMouseExited(event -> statusLabel.setText(""));
        hForceButton.setOnMouseEntered(event -> statusLabel.setText("Горизонтальная нарузка (H)"));
        hForceButton.setOnMouseExited(event -> statusLabel.setText(""));
        mForceButton.setOnMouseEntered(event -> statusLabel.setText("Изгибающий момент (H*м)"));
        mForceButton.setOnMouseExited(event -> statusLabel.setText(""));

        woodButton.setOnMouseEntered(event -> statusLabel.setText("Дерево"));
        woodButton.setOnMouseExited(event -> statusLabel.setText(""));
        steelButton.setOnMouseEntered(event -> statusLabel.setText("Металл"));
        steelButton.setOnMouseExited(event -> statusLabel.setText(""));
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

    AnchorPane getCanvasPane() {
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

    void onMouseDrag(MouseEvent mouseEvent) {
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

    void onMouseReleased(MouseEvent mouseEvent) {
        if (mouseEvent.getButton() == MouseButton.MIDDLE) {
            coordinateUtils.onMouseReleased();
        }
        canvas.redraw();
    }

    void onScroll(ScrollEvent scrollEvent) {
        if (scrollEvent.getDeltaY() < 0) {
            coordinateUtils.prevScale();
        } else {
            coordinateUtils.nextScale();
        }
        scaleLabel.setText(coordinateUtils.getScale());
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

    public Pane getMaterialPane() {
        return materialPane;
    }

    public ToggleButton getWoodButton() {
        return woodButton;
    }

    public ToggleButton getSteelButton() {
        return steelButton;
    }

    public ComboBox<Material> getMaterialComboBox() {
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

    public Label getUserLabel() {
        return userLabel;
    }

    public TextField getLoginField() {
        return loginField;
    }

    public PasswordField getPasswordField() {
        return passwordField;
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

    public ComboBox<ProjectInfo> getProjectsComboBox() {
        return projectsComboBox;
    }

    public Pane getLoadProjectPane() {
        return loadProjectPane;
    }

    public Button getLoadProjectInfoButton() {
        return loadProjectInfoButton;
    }

    public Pane getSavePane() {
        return savePane;
    }

    public Button getSaveButton() {
        return saveButton;
    }

    public Label getSaveLabel() {
        return saveLabel;
    }

    public Label getLoadLabel() {
        return loadLabel;
    }

    public ToggleButton getModelButton() {
        return modelButton;
    }

    public ToggleButton getCalcButton() {
        return calcButton;
    }

    public Label getLoginLabel() {
        return loginLabel;
    }

    public Client getClient() {
        return client;
    }

    public Pane getCheckPane() {
        return checkPane;
    }

    public Button getCheckModelRefreshButton() {
        return checkModelRefreshButton;
    }

    public Label getCheckFreePointsLabel() {
        return checkFreePointsLabel;
    }

    public Label getCheckMaterialLabel() {
        return checkMaterialLabel;
    }

    public Label getCheckCutLabel() {
        return checkCutLabel;
    }

    public Label getCheckForceLabel() {
        return checkForceLabel;
    }

    public Label getCheckReactionLabel() {
        return checkReactionLabel;
    }

    public Button getCalculateButton() {
        return calculateButton;
    }

    public Label getCalculationResultLabel() {
        return calculationResultLabel;
    }

    public ToggleButton getTableView() {
        return tableView;
    }

    public TableView<Result> getResultTable() {
        return resultTable;
    }

    public TableColumn<Result, String> getIdColumn() {
        return idColumn;
    }

    public TableColumn<Result, Number> getN1Column() {
        return n1Column;
    }

    public TableColumn<Result, Number> getQ1Column() {
        return q1Column;
    }

    public TableColumn<Result, Number> getM1Column() {
        return m1Column;
    }

    public TableColumn<Result, Number> getQ2Column() {
        return q2Column;
    }

    public TableColumn<Result, Number> getN2Column() {
        return n2Column;
    }

    public TableColumn<Result, Number> getM2Column() {
        return m2Column;
    }

    public Pane getResultPane() {
        return resultPane;
    }

    public ToggleButton getCalculationResultButton() {
        return calculationResultButton;
    }

    public ToggleButton getnView() {
        return nView;
    }

    public ToggleButton getqView() {
        return qView;
    }

    public ToggleButton getmView() {
        return mView;
    }
}
