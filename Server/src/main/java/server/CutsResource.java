package server;

import domain.CadUser;
import facade.CutFacade;
import facade.UserFacade;
import model.ArrayOfCut;
import model.ArrayOfMaterial;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by podsh on 06.05.2017.
 */
@Path("/cuts")
public class CutsResource {
    private CutFacade cutFacade;
    private UserFacade userFacade;

    public CutsResource() {
        userFacade = new UserFacade();
        cutFacade = new CutFacade();
    }

    @GET
    @Path("/{type}")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response getIBeams(@HeaderParam("sessionId") String sessionId, @PathParam("type") String type) {
        CadUser user = userFacade.getCadUserBySessionId(sessionId);
        if (user != null) {
            ArrayOfCut arrayOfCut = cutFacade.getByType(type);
            return Response.ok(arrayOfCut).build();
        } else {
            return Response.serverError().build();
        }
    }
}
