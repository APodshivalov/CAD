package app.controllers;

import app.Controller;
import app.interfaces.Controllable;


/**
 * Created by APodshivalov on 29.03.2017.
 */
public class DrawController implements Controllable{
    private Controller controller;

    public DrawController(Controller controller) {
        this.controller = controller;
    }

}
