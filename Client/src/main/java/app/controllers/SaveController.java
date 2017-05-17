package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientHandlerException;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.ws.rs.core.MediaType;

/**
 * Created by podsh on 07.05.2017.
 */
public class SaveController implements Controllable {
    private Controller controller;
    private Pane savePane;
    private Button saveButton;
    private Label saveLabel;

    public SaveController(Controller controller){
        this.controller = controller;
        savePane = controller.getSavePane();
        saveButton = controller.getSaveButton();
        saveLabel = controller.getSaveLabel();
        saveButton.setOnAction(event -> save());
    }

    private void save() {
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        Client client = Client.create(cfg);
        WebResource webResource = client.resource("http://" + Controller.host + ":8080/Server-1.0/save");

        if (controller.getCurrentUser() == null) {
            saveLabel.setTextFill(Color.web("#76323F"));
            saveLabel.setText("Вход не выполнен");
            return;
        }
        ClientResponse response;
        try {
            response = webResource
                    .accept(MediaType.APPLICATION_JSON)
                    .entity(controller.getModel().getProject())
                    .header("sessionId", controller.getCurrentUser().getSessionId())
                    .post(ClientResponse.class);
        } catch (ClientHandlerException e) {
            saveLabel.setTextFill(Color.web("#76323F"));
            saveLabel.setText("Сервис недоступен");
            return;
        }


        if (response.getStatus() == 200) {
            saveLabel.setTextFill(Color.web("#3B3738"));
            saveLabel.setText("Сохранено");
        }

        if (response.getStatus() == 500) {
            saveLabel.setTextFill(Color.web("#76323F"));
            saveLabel.setText("Внутренняя ошибка сервера");
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
        controller.deactivatePane(savePane);
        saveLabel.setText("");
    }

    @Override
    public void enable() {
        controller.activatePane(savePane);
    }
}
