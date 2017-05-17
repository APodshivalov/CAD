package server;

import facade.UserFacade;
import model.LoginInfo;
import model.User;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by podsh on 06.05.2017.
 */
@Path("/user")
public class UserResource {
    private UserFacade facade;
    public UserResource() {
        facade = new UserFacade();
    }

    @POST
    @Path("/login")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response login(LoginInfo logInUser){
        User user = facade.validateUser(logInUser);
        return Response.ok(user).build();
    }
}
