package com.zou;

import com.zou.service.ConfigurationService;
import com.zou.type.DocumentDefinition;
import io.quarkus.vertx.web.Body;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/document/definitions")
public class DocumentConfigurationResource {

    @Inject
    ConfigurationService configurationService;

    @GET
    @Path("/definition/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response findAll(@PathParam("id") String config) {
        return Response.status(Response.Status.OK).entity(configurationService.findByType(config)).build();
    }

    @POST
    @Path("/definition")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Body DocumentDefinition documentDefinition) {
        configurationService.save(documentDefinition);
        return Response.status(Response.Status.CREATED).build();
    }
}