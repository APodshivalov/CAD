package server;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by APodshivalov on 25.04.2017.
 */
@ApplicationPath("/")
public class Server extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> set = new HashSet<>();
        set.add(Service.class);
        return set;
    }
}