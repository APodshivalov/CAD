package server;

import model.Material;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by APodshivalov on 25.04.2017.
 */
@Path("/")
public class Service {
    @GET
    @Path("/material")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response get() {
        Material m = new Material();
        m.setId("1");
        m.setName("Steel");
        m.setE(2_000_000);
        return Response.ok(m).build();
    }
}
