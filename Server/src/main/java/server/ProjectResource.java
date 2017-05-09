package server;

import domain.CadUser;
import facade.ProjectFacade;
import facade.UserFacade;
import model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by APodshivalov on 25.04.2017.
 */
@Path("/")
public class Service {
    private UserFacade userFacade;
    private ProjectFacade projectFacade;

    public Service() {
        userFacade = new UserFacade();
        projectFacade = new ProjectFacade();
    }

    @POST
    @Path("/create")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response create(@HeaderParam("sessionId") String sessionId, ProjectInfo project) {
        CadUser user = userFacade.getCadUserBySessionId(sessionId);
        if (user != null) {
            return Response.ok(projectFacade.createProject(project, user)).build();
        } else {
            return Response.ok().build();
        }
    }

    @POST
    @Path("/save")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response save(@HeaderParam("sessionId") String sessionId, Project project) {
        CadUser user = userFacade.getCadUserBySessionId(sessionId);
        if (user != null) {
            projectFacade.save(project, user);
            return Response.ok().build();
        } else {
            return Response.ok().build();
        }
    }
}
