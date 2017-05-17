package server;

import domain.*;
import facade.ProjectFacade;
import facade.UserFacade;
import model.*;
import model.Bar;
import model.ProjectInfo;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by APodshivalov on 25.04.2017.
 */
@Path("/")
public class ProjectResource {
    private UserFacade userFacade;
    private ProjectFacade projectFacade;

    public ProjectResource() {
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
            projectFacade.save(project);
            return Response.ok().build();
        } else {
            return Response.ok().build();
        }
    }

    @GET
    @Path("/projectNames")
    @Produces({(MediaType.APPLICATION_JSON)})
    public Response loadProjectInfo(@HeaderParam("sessionId") String sessionId) {
        CadUser user = userFacade.getCadUserBySessionId(sessionId);
        if (user != null) {
            ArrayOfProjectInfo arrayOfProjectInfo = projectFacade.getProjectInfoByUser(user);
            return Response.ok(arrayOfProjectInfo).build();
        } else {
            return Response.ok().build();
        }
    }

    @POST
    @Path("/load")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response loadProject(ProjectInfo projectInfo) {
        Project project = projectFacade.loadProject(projectInfo);
        for (Bar bar : project.getBars()){
            System.out.println(bar);
        }
        return Response.ok(project).build();
    }
}
