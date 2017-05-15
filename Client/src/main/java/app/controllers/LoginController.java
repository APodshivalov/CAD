package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.LoginObject;
import app.model.User;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import javax.ws.rs.core.MediaType;

/**
 * Created by podsh on 07.05.2017.
 */
public class LoginController implements Controllable {
    private Controller controller;
    private Button sendLogInButton;
    private Button cancelLogInButton;
    private TextField loginField;
    private PasswordField passwordField;

    public LoginController(Controller controller) {
        this.controller = controller;
        sendLogInButton = controller.getSendLogInButton();
        sendLogInButton.setOnAction(event -> logIn());
        cancelLogInButton = controller.getCancelLogInButton();
        cancelLogInButton.setOnAction(event -> controller.clearEventListener());
        loginField = controller.getLoginField();
        passwordField = controller.getPasswordField();
    }

    private void logIn() {
        LoginObject loginObject = new LoginObject(loginField.getText(), passwordField.getText());
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        Client client = Client.create(cfg);
        WebResource webResource = client.resource("http://localhost:8080/Server-1.0/user/login" );

        User pojo = webResource
                .accept(MediaType.APPLICATION_JSON)
                .entity(loginObject)
                .post(User.class);

        controller.setTabsDisable(false);
        controller.getUserLabel().setText(pojo.getFirstName() + " " + pojo.getLastName());
        controller.setCurrentUser(pojo);
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
        controller.deactivatePane(controller.getAuthPane());
    }

    @Override
    public void enable() {
        controller.activatePane(controller.getAuthPane());
    }
}
