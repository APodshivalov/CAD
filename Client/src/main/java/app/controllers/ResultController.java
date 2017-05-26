package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.Result;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Created by podsh on 25.05.2017.
 */
public class ResultController implements Controllable {
    private Controller controller;
    private Pane resultPane;
    private ToggleButton tableView;
    private TableView<Result> resultTable;

    ResultController(Controller controller) {
        this.controller = controller;
        resultPane = controller.getResultPane();
        tableView = controller.getTableView();
        ToggleButton nView = controller.getnView();
        ToggleButton qView = controller.getqView();
        ToggleButton mView = controller.getmView();
        resultTable = controller.getResultTable();

        TableColumn<Result, String> idColumn = controller.getIdColumn();
        idColumn.setCellValueFactory(param -> new SimpleStringProperty(param.getValue().getBarId()));

        TableColumn<Result, Number> n1Column = controller.getN1Column();
        n1Column.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getN1()));

        TableColumn<Result, Number> q1Column = controller.getQ1Column();
        q1Column.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getQ1()));

        TableColumn<Result, Number> m1Column = controller.getM1Column();
        m1Column.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getM1()));

        TableColumn<Result, Number> n2Column = controller.getN2Column();
        n2Column.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getN2()));

        TableColumn<Result, Number> q2Column = controller.getQ2Column();
        q2Column.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getQ2()));

        TableColumn<Result, Number> m2Column = controller.getM2Column();
        m2Column.setCellValueFactory(param -> new SimpleDoubleProperty(param.getValue().getM2()));
        tableView.setOnAction(event -> showTable());

        nView.setOnAction(event -> draw());
        qView.setOnAction(event -> draw());
        mView.setOnAction(event -> draw());
    }

    private void draw() {
        resultTable.setVisible(false);
        controller.getCanvas().setVisible(true);
        controller.getCanvas().redraw();
    }

    private void showTable() {
        if (tableView.isSelected()) {
            resultTable.setVisible(true);
            controller.getCanvas().setVisible(false);
            resultTable.setItems(controller.getModel().getResult());
        } else {
            resultTable.setVisible(false);
            controller.getCanvas().setVisible(true);
        }
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
        controller.deactivatePane(resultPane);
        resultTable.setVisible(false);
        controller.getCanvas().setVisible(true);
    }

    @Override
    public void enable() {
        controller.activatePane(resultPane);
    }
}
