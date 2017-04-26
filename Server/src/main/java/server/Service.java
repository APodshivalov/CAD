package server;

import model.ArrayOfMaterial;
import model.Material;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by APodshivalov on 25.04.2017.
 */
@Path("/")
public class Service {
    @GET
    @Path("/material/wood")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response getWood() {
        Material m = new Material("1", "Wood1", 2_000_000);
        Material q = new Material("2", "Wood2", 2_000_000);
        ArrayOfMaterial arrayOfMaterial = new ArrayOfMaterial();
        arrayOfMaterial.setItem(new ArrayList<>());
        arrayOfMaterial.getItem().add(m);
        arrayOfMaterial.getItem().add(q);
        return Response.ok(arrayOfMaterial).build();
    }

    @GET
    @Path("/material/steel")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response getSteel() {
        Material m = new Material("1", "Steel1", 2_000_000);
        Material q = new Material("2", "Steel2", 2_000_000);
        ArrayOfMaterial arrayOfMaterial = new ArrayOfMaterial();
        arrayOfMaterial.setItem(new ArrayList<>());
        arrayOfMaterial.getItem().add(m);
        arrayOfMaterial.getItem().add(q);
        return Response.ok(arrayOfMaterial).build();
    }
}
