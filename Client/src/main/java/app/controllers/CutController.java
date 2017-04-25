package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.ArrayOfMaterial;
import app.model.Material;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;

import javax.ws.rs.core.MediaType;
import java.io.UnsupportedEncodingException;
import java.util.stream.Collectors;

/**
 * Created by podsh on 25.04.2017.
 */
public class CutController implements Controllable{
    private Controller controller;
    private ToggleButton wood;
    private ChoiceBox<String> materialChoiceBox;

    public CutController(Controller controller) {
        this.controller = controller;
        wood = controller.getWoodButton();
        wood.setOnAction(event -> loadFromCloud());
        materialChoiceBox = controller.getMaterialChoiceBox();
    }

    private void loadFromCloud() {
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        Client client = Client.create(cfg);
        WebResource webResource = client.resource("http://localhost:8080/Server-1.0/material");

        ArrayOfMaterial pojo = webResource
                .accept(MediaType.APPLICATION_JSON)
                .get(ArrayOfMaterial.class);

        materialChoiceBox.getItems().addAll(pojo.getItem().stream().map(material -> {
            try {
                return new String(material.getName().getBytes("cp1251"));
            } catch (UnsupportedEncodingException e) {
                return "";
            }
        }).collect(Collectors.toList()));
    }

    @Override
    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {

    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void disable() {
        controller.getCutSideMenu().setVisible(false);
        controller.getCanvas().setXLayout(0);
        controller.getCanvas().redraw();
    }

    @Override
    public void enable() {
        controller.getCanvas().setXLayout(controller.getCutSideMenu().getWidth());
        controller.getCutSideMenu().setVisible(true);
        controller.getCanvas().redraw();
    }
}
