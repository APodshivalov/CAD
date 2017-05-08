package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.ArrayOfMaterial;
import app.model.ArrayOfProjectInfo;
import app.model.ProjectInfo;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;

import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * Created by podsh on 09.05.2017.
 */
public class LoadProjectController implements Controllable {
    private Controller controller;
    private ComboBox<ProjectInfo> projectInfoComboBox;
    private Button loadButton;

    public LoadProjectController(Controller controller){
        this.controller = controller;
        projectInfoComboBox = controller.getProjectsComboBox();
        loadButton = controller.getLoadProjectInfoButton();
        loadFromCloud(controller.getCurrentUser().getSessionId());
    }

    private void loadFromCloud(String sessionId) {
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        Client client = Client.create(cfg);
        WebResource webResource = client.resource("http://localhost:8080/Server-1.0/project/meta");

        ArrayOfProjectInfo pojo = webResource
                .header("sessionId", controller.getCurrentUser().getSessionId())
                .accept(MediaType.APPLICATION_JSON)
                .get(ArrayOfProjectInfo.class);

        projectInfoComboBox.getItems().setAll(pojo.getList());
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

    }

    @Override
    public void enable() {

    }
}
