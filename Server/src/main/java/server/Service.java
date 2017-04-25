package server;

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
    @Path("/get")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response get() {
        return Response.ok("response").build();
    }
}
