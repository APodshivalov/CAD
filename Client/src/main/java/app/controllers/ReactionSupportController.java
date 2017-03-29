package app.controllers;

import app.Controller;
import app.interfaces.Controllable;

/**
 * Created by APodshivalov on 29.03.2017.
 */
public class ReactionSupportController implements Controllable {
    private Controller controller;

    public ReactionSupportController(Controller controller) {
        this.controller = controller;
    }

}
