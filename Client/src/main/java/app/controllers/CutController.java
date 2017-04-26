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

    public CutController(Controller controller) {
    }

    private void loadFromCloud() {

    }

    @Override
    public void onMouseClickedOverCanvas(MouseEvent mouseEvent) {

    }

    @Override
    public void onMouseMoved(MouseEvent mouseEvent) {

    }

    @Override
    public void disable() {

    }

    @Override
    public void enable() {

    }
}
