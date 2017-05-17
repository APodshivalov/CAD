package server;

import domain.CadUser;
import facade.MaterialFacade;
import facade.UserFacade;
import model.ArrayOfMaterial;
import model.Material;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by podsh on 06.05.2017.
 */
@Path("/material")
public class MaterialResource {
    private MaterialFacade materialFacade;
    private UserFacade userFacade;

    public MaterialResource() {
        userFacade = new UserFacade();
        materialFacade = new MaterialFacade();
    }

    @GET
    @Path("/wood")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response getWood(@HeaderParam("sessionId") String sessionId) {
        CadUser user = userFacade.getCadUserBySessionId(sessionId);
        if (user != null){
            ArrayOfMaterial arrayOfMaterial = materialFacade.getMaterials("wood");
            return Response.ok(arrayOfMaterial).build();
        } else {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/steel")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response getSteel(@HeaderParam("sessionId") String sessionId) {
        CadUser user = userFacade.getCadUserBySessionId(sessionId);
        if (user != null){
            ArrayOfMaterial arrayOfMaterial = materialFacade.getMaterials("steel");
            return Response.ok(arrayOfMaterial).build();
        } else {
            return Response.serverError().build();
        }

    }
}
