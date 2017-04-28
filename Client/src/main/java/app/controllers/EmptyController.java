package app.controllers;

import app.interfaces.Controllable;
import javafx.scene.input.MouseEvent;

/**
 * Created by APodshivalov on 28.04.2017.
 */
public class EmptyController implements Controllable {
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
