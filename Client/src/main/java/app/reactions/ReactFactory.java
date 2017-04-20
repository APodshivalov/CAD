package app.reactions;

import app.Controller;
import app.interfaces.ReactButton;

/**
 * Created by podsh on 21.04.2017.
 */
public class ReactFactory {
    private static Lock lock;

    public static ReactButton getInstance(Class<?> lockClass, Controller controller) {
        if (lockClass.getName().equals("app.reactions.Lock")){
            if (lock == null) {
                lock = new Lock(controller);
            }
            return lock;
        }
        return null;
    }
}
