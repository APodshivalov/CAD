package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.ArrayOfResult;
import app.model.Model;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by podsh on 21.05.2017.
 */
public class CheckModelController implements Controllable {
    private Controller controller;
    private Pane checkPane;
    private ToggleButton calculationResultButton;
    private Label calculationResultLabel;
    private Label checkFreePointsLabel;
    private Label checkMaterialLabel;
    private Label checkCutLabel;
    private Label checkForceLabel;
    private Label checkReactionLabel;
    private Button calculateButton;

    CheckModelController(Controller controller) {
        this.controller = controller;
        checkPane = controller.getCheckPane();
        Button checkModelRefreshButton = controller.getCheckModelRefreshButton();
        checkModelRefreshButton.setOnAction(event -> checkModel());
        checkFreePointsLabel = controller.getCheckFreePointsLabel();
        checkMaterialLabel = controller.getCheckMaterialLabel();
        checkCutLabel = controller.getCheckCutLabel();
        checkForceLabel = controller.getCheckForceLabel();
        checkReactionLabel = controller.getCheckReactionLabel();
        calculateButton = controller.getCalculateButton();
        calculateButton.setOnAction(event -> calculateInCloud());
        calculationResultLabel = controller.getCalculationResultLabel();
        calculationResultButton = controller.getCalculationResultButton();
    }

    private void calculateInCloud() {
        Client client = controller.getClient();
        WebResource webResource = client.resource("http://" + Controller.host + ":8080/Server-1.0/calculate");
        ObjectMapper objectMapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
        ArrayOfResult arrayOfResult = new ArrayOfResult();
        try {
            ClientResponse response = webResource
                    .accept(MediaType.APPLICATION_JSON)
                    .entity(controller.getModel().getProject())
                    .header("sessionId", controller.getCurrentUser().getSessionId())
                    .post(ClientResponse.class);
            arrayOfResult = objectMapper.readValue(response.getEntityInputStream(), ArrayOfResult.class);
        } catch (ClientHandlerException e) {
            calculationResultLabel.setTextFill(Color.web("#76323F"));
            calculationResultLabel.setText("Сервис недоступен");
            return;
        } catch (IOException e) {
            calculationResultLabel.setTextFill(Color.web("#76323F"));
            calculationResultLabel.setText("Ошибка");
        }

        calculationResultLabel.setTextFill(Color.web("#116611"));
        calculationResultLabel.setText("Успешно");
        calculationResultButton.setDisable(false);
        controller.getModel().setResult(arrayOfResult);
    }

    private void checkModel() {
        Model model = controller.getModel();

        // Свободные узлы
        boolean hasFreePoint = !model.getPoints().isEmpty() && model.getPoints().stream().anyMatch(point -> !model.getProject().contains(point));
        if (hasFreePoint) {
            checkFreePointsLabel.setTextFill(Color.web("#76323F"));
            checkFreePointsLabel.setText("Свободные узлы: Ошибка");
        } else {
            checkFreePointsLabel.setTextFill(Color.web("#116611"));
            checkFreePointsLabel.setText("Свободные узлы: OK");
        }

        // Материал
        boolean hasNoMaterial = model.getProject().getBars().isEmpty() || model.getProject().getBars().stream().anyMatch(bar -> bar.getMaterial() == null);
        if (hasNoMaterial) {
            checkMaterialLabel.setTextFill(Color.web("#76323F"));
            checkMaterialLabel.setText("Материал: Ошибка");
        } else {
            checkMaterialLabel.setTextFill(Color.web("#116611"));
            checkMaterialLabel.setText("Материал: OK");
        }

        // Сечение
        boolean hasNoCut = model.getProject().getBars().isEmpty() || model.getProject().getBars().stream().anyMatch(bar -> bar.getCut() == null);
        if (hasNoCut) {
            checkCutLabel.setTextFill(Color.web("#76323F"));
            checkCutLabel.setText("Сечение: Ошибка");
        } else {
            checkCutLabel.setTextFill(Color.web("#116611"));
            checkCutLabel.setText("Сечение: OK");
        }

        // Нагрузки
        boolean hasNoForces = model.getPoints().isEmpty() || model.getPoints().stream().allMatch(point -> point.getForce().empty());
        if (hasNoForces) {
            checkForceLabel.setTextFill(Color.web("#76323F"));
            checkForceLabel.setText("Нагрузки: Ошибка");
        } else {
            checkForceLabel.setTextFill(Color.web("#116611"));
            checkForceLabel.setText("Нагрузки: OK");
        }

        // Опоры
        boolean hasNoReactions = model.getPoints().isEmpty() || model.getPoints().stream().allMatch(point -> point.getReaction().empty());
        if (hasNoReactions) {
            checkReactionLabel.setTextFill(Color.web("#76323F"));
            checkReactionLabel.setText("Опоры: Ошибка");
        } else {
            checkReactionLabel.setTextFill(Color.web("#116611"));
            checkReactionLabel.setText("Опоры: OK");
        }

        calculateButton.setDisable(
                hasFreePoint ||
                hasNoMaterial ||
                hasNoCut ||
                hasNoForces ||
                hasNoReactions
        );
    }

    @Override
    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {

    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void redraw() {

    }

    @Override
    public void disable() {
        controller.deactivatePane(checkPane);
        calculationResultLabel.setText("");
    }

    @Override
    public void enable() {
        controller.activatePane(checkPane);
    }
}
