package app.controllers;

import app.Controller;
import app.ImageFactory;
import app.interfaces.Controllable;
import app.model.ArrayOfCut;
import app.model.Cut;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.ws.rs.core.MediaType;
import java.io.IOException;

/**
 * Created by podsh on 25.04.2017.
 */
public class CutController implements Controllable {
    private Controller controller;
    private ToggleButton iBeamButton;
    private ToggleButton cBeamButton;
    private ToggleButton tBeamButton;
    private ToggleButton oBeamButton;
    private ImageView bigImage;
    private ComboBox<Cut> cutComboBox;

    private Label rProperty;
    private Label sProperty;
    private Label hProperty;
    private Label bProperty;
    private Label tProperty;

    CutController(Controller controller) {
        this.controller = controller;
        bigImage = controller.getBigBeamImage();
        cutComboBox = controller.getCutComboBox();
        cutComboBox.setOnAction(event -> fillLabels());
        Button acceptCut = controller.getAcceptCut();
        acceptCut.setOnAction(event -> acceptCurrentCut());

        rProperty = controller.getrProperty();
        sProperty = controller.getsProperty();
        hProperty = controller.gethProperty();
        bProperty = controller.getbProperty();
        tProperty = controller.gettProperty();

        ToggleGroup beamButtons = new ToggleGroup();

        iBeamButton = controller.getiBeamButton();
        iBeamButton.setToggleGroup(beamButtons);
        iBeamButton.setOnAction(event -> activate(iBeamButton, "iBeam"));

        cBeamButton = controller.getcBeamButton();
        cBeamButton.setToggleGroup(beamButtons);
        cBeamButton.setOnAction(event -> activate(cBeamButton, "cBeam"));

        tBeamButton = controller.gettBeamButton();
        tBeamButton.setToggleGroup(beamButtons);
        tBeamButton.setOnAction(event -> activate(tBeamButton, "tBeam"));

        oBeamButton = controller.getoBeamButton();
        oBeamButton.setToggleGroup(beamButtons);
        oBeamButton.setOnAction(event -> activate(oBeamButton, "oBeam"));
    }

    private void acceptCurrentCut() {
        controller.getModel().setCurrentCut(cutComboBox.getValue());
        controller.getCanvas().redraw();
    }

    private void fillLabels() {
        Cut c = cutComboBox.getValue();
        if (c == null){
            clearLabels();
        } else {
            bigImage.setImage(ImageFactory.getImage("beams/" + c.getType()));
            rProperty.setText(setText("r = ", c.getR()));
            sProperty.setText(setText("s = ", c.getS()));
            hProperty.setText(setText("h = ", c.getH()));
            bProperty.setText(setText("b = ", c.getB()));
            tProperty.setText(setText("t = ", c.getT()));
        }
    }

    private void clearLabels() {
        bigImage.setImage(null);
        rProperty.setText("");
        sProperty.setText("");
        hProperty.setText("");
        bProperty.setText("");
        tProperty.setText("");
    }

    private String setText(String s, double r) {
        if (r == 0){
            return "";
        } else {
            return s + r;
        }
    }

    private void activate(ToggleButton beamButton, String type) {
        if (beamButton.isSelected()) {
            loadFromCloud(type);
        } else {
            cutComboBox.getItems().clear();
            cutComboBox.setValue(null);
            clearLabels();
        }
    }

    private void loadFromCloud(String type) {
        Client client = controller.getClient();
        WebResource webResource = client.resource("http://" + Controller.host + ":8080/Server-1.0/cuts/" + type);
        ObjectMapper objectMapper = new ObjectMapper()
                .enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);

        ClientResponse pojo = webResource
                .header("sessionId", controller.getCurrentUser().getSessionId())
                .accept(MediaType.APPLICATION_JSON)
                .get(ClientResponse.class);

        ArrayOfCut arrayOfCut = new ArrayOfCut();

        try {
            arrayOfCut = objectMapper.readValue(pojo.getEntityInputStream(), ArrayOfCut.class);
        } catch (IOException ignored) {
        }

        cutComboBox.getItems().setAll(arrayOfCut.getItem());
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
        controller.deactivatePane(controller.getCutPane());
    }

    @Override
    public void enable() {
        controller.activatePane(controller.getCutPane());
    }
}
