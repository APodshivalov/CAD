package app.controllers;

import app.Controller;
import app.interfaces.Controllable;

/**
 * Created by Valentine on 29.03.2017.
 */
public class ControllerFactory {
    private static DrawController drawController;
    private static ReactionSupportController reactionSupportController;

    public static Controllable getDrawController(Controller controller) {
        if (drawController == null) {
            drawController = new DrawController(controller);
        }
        return drawController;
    }

    public static Controllable getReactionSupportController(Controller controller) {
        if (reactionSupportController == null) {
            reactionSupportController = new ReactionSupportController(controller);
        }
        return reactionSupportController;
    }
}
