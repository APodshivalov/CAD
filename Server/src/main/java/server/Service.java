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
import java.util.List;

/**
 * Created by APodshivalov on 25.04.2017.
 */
@Path("/")
public class Service {
    @GET
    @Path("/material")
    @Produces({(MediaType.APPLICATION_JSON + ";charset=utf-8")})
    @Consumes({(MediaType.APPLICATION_JSON + ";charset=utf-8")})
    public Response get() {
        Material m = new Material();
        m.setId("1");
        m.setName("Сталь-3");
        m.setE(2_000_000);
        Material q = new Material();
        q.setId("2");
        q.setName("Сталь легированая");
        q.setE(2_000_000);
        ArrayOfMaterial arrayOfMaterial = new ArrayOfMaterial();
        arrayOfMaterial.setItem(new ArrayList<>());
        arrayOfMaterial.getItem().add(m);
        arrayOfMaterial.getItem().add(q);
        return Response.ok(arrayOfMaterial).build();
    }
}
