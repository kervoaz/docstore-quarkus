package com.zou;

import com.zou.service.ConfigurationService;
import com.zou.type.DocumentSchema;
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
    @Path("/definition/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("type") String type) {
        return Response.status(Response.Status.OK).entity(configurationService.findByType(type)).build();
    }

    @POST
    @Path("/definition")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(@Body DocumentSchema documentDefinition) {
        configurationService.save(documentDefinition);
        return Response.status(Response.Status.CREATED).build();
    }
}