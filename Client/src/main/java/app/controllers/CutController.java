package app.controllers;

import app.Controller;
import app.ImageFactory;
import app.interfaces.Controllable;
import app.model.ArrayOfCut;
import app.model.Cut;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import javax.ws.rs.core.MediaType;

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

    public CutController(Controller controller) {
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
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        Client client = Client.create(cfg);
        WebResource webResource = client.resource("http://" + Controller.host + ":8080/Server-1.0/cuts/" + type);

        ArrayOfCut pojo = webResource
                .header("sessionId", controller.getCurrentUser().getSessionId())
                .accept(MediaType.APPLICATION_JSON)
                .get(ArrayOfCut.class);

        cutComboBox.getItems().setAll(pojo.getItem());
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
