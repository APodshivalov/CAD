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
    private static ForceController forceController;
    private static LoginController loginController;
    private static CreateProjectController createProjectController;
    private static SaveController saveController;
    private static LoadProjectController loadProjectController;
    private static EmptyController emptyController;
    private static CheckModelController checkModelController;
    private static ResultController resultController;

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

    public static Controllable getForceController(Controller controller) {
        if (forceController == null) {
            forceController = new ForceController(controller);
        }
        return forceController;
    }

    public static Controllable getLoginController(Controller controller) {
        if (loginController == null) {
            loginController = new LoginController(controller);
        }
        return loginController;
    }

    public static Controllable getCreateProjectController(Controller controller) {
        if (createProjectController == null) {
            createProjectController = new CreateProjectController(controller);
        }
        return createProjectController;
    }

    public static Controllable getLoadProjectController(Controller controller) {
        if (loadProjectController == null) {
            loadProjectController = new LoadProjectController(controller);
        }
        return loadProjectController;
    }

    public static Controllable getSaveProjectController(Controller controller) {
        if (saveController == null) {
            saveController = new SaveController(controller);
        }
        return saveController;
    }

    public static Controllable getCheckModelController(Controller controller) {
        if (checkModelController == null) {
            checkModelController = new CheckModelController(controller);
        }
        return checkModelController;
    }

    public static Controllable getResultController(Controller controller) {
        if (resultController == null) {
            resultController = new ResultController(controller);
        }
        return resultController;
    }
}
