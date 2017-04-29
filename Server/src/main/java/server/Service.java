package server;

import model.ArrayOfCut;
import model.ArrayOfMaterial;
import model.Cut;
import model.Material;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
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

    @GET
    @Path("/cuts/ibeam")
    @Produces({(MediaType.APPLICATION_JSON)})
    @Consumes({(MediaType.APPLICATION_JSON)})
    public Response getIBeams() {
        ArrayOfCut arrayOfCut = new ArrayOfCut();
        arrayOfCut.setItem(new ArrayList<>());
        try {
            Files.lines(Paths.get("D:/Cut")).forEach(s -> {
                String[] items = s.split(";");
                arrayOfCut.getItem().add(new Cut(items));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Response.ok(arrayOfCut).build();
    }
}
