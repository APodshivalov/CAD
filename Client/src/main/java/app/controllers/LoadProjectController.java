package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.*;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import javax.ws.rs.core.MediaType;

/**
 * Created by podsh on 09.05.2017.
 */
public class LoadProjectController implements Controllable {
    private Controller controller;
    private ComboBox<ProjectInfo> projectInfoComboBox;
    private Button loadButton;
    private Pane loadPane;

    public LoadProjectController(Controller controller){
        this.controller = controller;
        projectInfoComboBox = controller.getProjectsComboBox();
        loadButton = controller.getLoadProjectInfoButton();
        loadPane = controller.getLoadProjectPane();
        loadFromCloud(controller.getCurrentUser().getSessionId());
        loadButton.setOnAction(event -> loadProjectFromCloud(projectInfoComboBox.getValue()));
    }

    private void loadProjectFromCloud(ProjectInfo value) {
        if (value != null){
            ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
            Client client = Client.create(cfg);
            WebResource webResource = client.resource("http://localhost:8080/Server-1.0/load");

            Project project = webResource
                    .entity(value)
                    .accept(MediaType.APPLICATION_JSON)
                    .post(Project.class);

            Model model = controller.getModel();
            model.clear();
            model.getProject().setProjectInfo(project.getProjectInfo());
            for (Bar bar: project.getBars()) {
                model.addPoint(bar.getFirstPoint());
                model.addPoint(bar.getSecondPoint());
                model.getProject().add(bar);
                model.getArrayOfMaterial().add(bar.getMaterial());
                model.getArrayOfCut().add(bar.getCut());
                model.setCurrentCut(bar.getCut());
                model.setCurrentMaterial(bar.getMaterial());
            }

        }
    }

    private void loadFromCloud(String sessionId) {
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        Client client = Client.create(cfg);
        WebResource webResource = client.resource("http://localhost:8080/Server-1.0/projectNames");

        ArrayOfProjectInfo pojo = webResource
                .header("sessionId", sessionId)
                .accept(MediaType.APPLICATION_JSON)
                .get(ArrayOfProjectInfo.class);

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
    }

    @Override
    public void enable() {
        controller.activatePane(loadPane);
    }
}
