package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.ProjectInfo;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.ws.rs.core.MediaType;

/**
 * Created by podsh on 08.05.2017.
 */
public class CreateProjectController implements Controllable {

    private Controller controller;
    private Pane createProjectPane;
    private TextField projectNameField;
    private Button createProjectButton;


    public CreateProjectController(Controller controller) {
        this.controller = controller;
        createProjectPane = controller.getNewProjectPane();
        createProjectButton = controller.getCreateProjectButton();
        projectNameField = controller.getProjectNameField();

        createProjectButton.setOnAction(event -> createProject(projectNameField.getText()));
    }

    private void createProject(String text) {
        Client client = controller.getClient();
        WebResource webResource = client.resource("http://" + Controller.host + ":8080/Server-1.0/create");

        ProjectInfo projectInfo = new ProjectInfo(text);
        ProjectInfo response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .entity(projectInfo)
                .header("sessionId", controller.getCurrentUser().getSessionId())
                .post(ProjectInfo.class);

        controller.getModel().clear();
        controller.getCanvas().redraw();
        controller.getModel().getProject().setProjectInfo(response);
        ((Stage) createProjectButton.getScene().getWindow()).setTitle(projectInfo.getName());
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
        controller.deactivatePane(createProjectPane);
    }

    @Override
    public void enable() {
        controller.activatePane(createProjectPane);
    }
}
