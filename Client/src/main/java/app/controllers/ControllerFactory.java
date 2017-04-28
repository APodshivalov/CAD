package app.controllers;

import app.Controller;
import app.interfaces.Controllable;

/**
 * Created by Valentine on 29.03.2017.
 */
public class ControllerFactory {
    private static DrawController drawController;
    private static ReactionSupportController reactionSupportController;
    private static CutController cutController;
    private static MaterialController materialController;
    private static EmptyController emptyController;

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

    public static Controllable getCutController(Controller controller) {
        if (cutController == null) {
            cutController = new CutController(controller);
        }
        return cutController;
    }

    public static Controllable getMaterialController(Controller controller) {
        if (materialController == null) {
            materialController = new MaterialController(controller);
        }
        return materialController;
    }

    public static Controllable getEmpty() {
        if (emptyController == null) {
            emptyController = new EmptyController();
        }
        return emptyController;
    }
}
