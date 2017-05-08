package app.controllers;

import app.Controller;
import app.interfaces.Controllable;
import javafx.scene.input.MouseEvent;

/**
 * Created by podsh on 07.05.2017.
 */
public class SaveController implements Controllable {
    private Controller controller;

    public SaveController(Controller controller){
        this.controller = controller;
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
