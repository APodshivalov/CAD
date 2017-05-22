package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.Model;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

/**
 * Created by podsh on 21.05.2017.
 */
public class CheckModelController implements Controllable {
    private Controller controller;
    private Pane checkPane;
    private Button checkModelRefreshButton;
    private Label checkFreePointsLabel;
    private Label checkMaterialLabel;
    private Label checkCutLabel;
    private Label checkForceLabel;
    private Label checkReactionLabel;
    private Button calculateButton;

    public CheckModelController(Controller controller) {
        this.controller = controller;
        checkPane = controller.getCheckPane();
        checkModelRefreshButton = controller.getCheckModelRefreshButton();
        checkModelRefreshButton.setOnAction(event -> checkModel());
        checkFreePointsLabel = controller.getCheckFreePointsLabel();
        checkMaterialLabel = controller.getCheckMaterialLabel();
        checkCutLabel = controller.getCheckCutLabel();
        checkForceLabel = controller.getCheckForceLabel();
        checkReactionLabel = controller.getCheckReactionLabel();
        calculateButton = controller.getCalculateButton();
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
    }

    @Override
    public void enable() {
        controller.activatePane(checkPane);
    }
}
