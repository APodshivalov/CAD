package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.*;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by podsh on 09.05.2017.
 */
public class LoadProjectController implements Controllable {
    private Controller controller;
    private ComboBox<ProjectInfo> projectInfoComboBox;
    private Button loadButton;
    private Pane loadPane;
    private Label loadLabel;

    public LoadProjectController(Controller controller) {
        this.controller = controller;
        projectInfoComboBox = controller.getProjectsComboBox();
        loadButton = controller.getLoadProjectInfoButton();
        loadPane = controller.getLoadProjectPane();
        loadLabel = controller.getLoadLabel();
        loadFromCloud(controller.getCurrentUser().getSessionId());
        loadButton.setOnAction(event -> loadProjectFromCloud(projectInfoComboBox.getValue()));
    }

    private void loadProjectFromCloud(ProjectInfo value) {
        if (value != null) {
            Client client = controller.getClient();
            WebResource webResource = client.resource("http://" + Controller.host + ":8080/Server-1.0/load");
            ObjectMapper objectMapper = new ObjectMapper()
                    .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

            Project project = new Project();

            try {
                ClientResponse response = webResource
                        .entity(value)
                        .accept(MediaType.APPLICATION_JSON)
                        .post(ClientResponse.class);
                project = objectMapper.readValue(response.getEntityInputStream(), Project.class);
            } catch (ClientHandlerException e) {
                loadLabel.setTextFill(Color.web("#76323F"));
                loadLabel.setText("Сервис недоступен");
                return;
            } catch (IOException e) {
                loadLabel.setTextFill(Color.web("#76323F"));
                loadLabel.setText("Ошибка");
            }

            loadLabel.setTextFill(Color.web("#116611"));
            loadLabel.setText("Проект загружен");

            Model model = controller.getModel();
            model.clear();
            model.getProject().setProjectInfo(project.getProjectInfo());
            for (Bar bar : project.getBars()) {
                bar.setCut(model.getArrayOfCut().getCutFromArrayOfCuts(bar.getCut()));
                bar.setMaterial(model.getArrayOfMaterial().getMaterialFromArrayOfMaterials(bar.getMaterial()));
                model.addPoint(bar.getFirstPoint());
                model.addPoint(bar.getSecondPoint());
                model.getProject().add(bar);
                model.setCurrentCut(bar.getCut());
                model.setCurrentMaterial(bar.getMaterial());
            }
            controller.getCanvas().redraw();
            ((Stage) loadButton.getScene().getWindow()).setTitle(project.getProjectInfo().getName());
        }
    }

    private void loadFromCloud(String sessionId) {
        Client client = controller.getClient();
        WebResource webResource = client.resource("http://" + Controller.host + ":8080/Server-1.0/projectNames");
        ObjectMapper objectMapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        ArrayOfProjectInfo pojo = new ArrayOfProjectInfo();

        try {
            ClientResponse response = webResource
                    .header("sessionId", sessionId)
                    .accept(MediaType.APPLICATION_JSON)
                    .get(ClientResponse.class);

            pojo = objectMapper.readValue(response.getEntityInputStream(), ArrayOfProjectInfo.class);

        } catch (ClientHandlerException e) {
            loadLabel.setTextFill(Color.web("#76323F"));
            loadLabel.setText("Сервис недоступен");
            return;
        } catch (IOException e) {
            loadLabel.setTextFill(Color.web("#76323F"));
            loadLabel.setText("Ошибка");
        }

        projectInfoComboBox.getItems().setAll(pojo.getItem());
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
        controller.deactivatePane(loadPane);
        loadLabel.setText("");
    }

    @Override
    public void enable() {
        controller.activatePane(loadPane);
    }
}
