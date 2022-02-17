package com.zou;

import com.zou.type.Fruit;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author HO.CKERVOAZOU
 */


@Path("/fruits")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class FruitResource {

    @Inject
    FruitSyncService service;

    @GET
    public List<Fruit> getAll() {
        return service.findAll();
    }

    @GET
    @Path("{name}")
    public Fruit getSingle(@PathParam("name") String name) {
        return service.get(name);
    }

    @POST
    public List<Fruit> add(Fruit fruit) {
        service.add(fruit);
        return getAll();
    }
}
