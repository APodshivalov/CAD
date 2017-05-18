package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import app.model.LoginObject;
import app.model.User;
import com.owlike.genson.ext.jaxrs.GensonJsonConverter;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.ClientConfig;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javax.ws.rs.core.MediaType;

/**
 * Created by podsh on 07.05.2017.
 */
public class LoginController implements Controllable {
    private Controller controller;
    private Button sendLogInButton;
    private Label loginLabel;
    private TextField loginField;
    private PasswordField passwordField;

    public LoginController(Controller controller) {
        this.controller = controller;
        loginLabel = controller.getLoginLabel();
        sendLogInButton = controller.getSendLogInButton();
        sendLogInButton.setOnAction(event -> logIn());
        loginField = controller.getLoginField();
        passwordField = controller.getPasswordField();
    }

    private void logIn() {
        if (loginField.getText().isEmpty()){
            loginLabel.setTextFill(Color.web("#76323F"));
            loginLabel.setText("Укажите логин");
            return;
        }

        if (passwordField.getText().isEmpty()){
            loginLabel.setTextFill(Color.web("#76323F"));
            loginLabel.setText("Укажите пароль");
            return;
        }
        LoginObject loginObject = new LoginObject(loginField.getText(), passwordField.getText());
        ClientConfig cfg = new DefaultClientConfig(GensonJsonConverter.class);
        Client client = Client.create(cfg);
        WebResource webResource = client.resource("http://" + Controller.host + ":8080/Server-1.0/user/login" );

        ClientResponse response = webResource
                .accept(MediaType.APPLICATION_JSON)
                .entity(loginObject)
                .post(ClientResponse.class);

        if (response.getStatus() == 500) {
            loginLabel.setTextFill(Color.web("#76323F"));
            loginLabel.setText("Внутренняя ошибка сервера");
            return;
        }

        User pojo = response.getEntity(User.class);

        if (pojo == null) {
            loginLabel.setTextFill(Color.web("#76323F"));
            loginLabel.setText("Вход не выполнен. Проверьте правильность введенного логина и пароля");
            return;
        } else {
            loginLabel.setTextFill(Color.web("#116611"));
            loginLabel.setText("Вход выполнен");
        }

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
        loginLabel.setText("");
    }

    @Override
    public void enable() {
        controller.activatePane(controller.getAuthPane());
    }
}
