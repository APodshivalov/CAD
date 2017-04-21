package app;

import app.interfaces.ReactButton;
import app.reactions.Lock;

/**
 * Created by APodshivalov on 21.04.2017.
 */
public class ReactionFactory {
    public static ReactButton getInstance(Class<? extends ReactButton> lockClass, Controller controller) {
        if (lockClass.getName().equals("app.reactions.Lock")){
            return new Lock(controller);
        }

        return null;
    }
}
