package com.zou;

import com.zou.service.ConfigurationService;
import com.zou.type.DocumentSchema;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.openapi.quarkus.DocstoreSpringSwagger_yaml.api.ConfigurationApi;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/document/definitions")
@ApplicationScoped
public class DocumentConfigurationResource /*implements ConfigurationApi*/ {

    @Inject
    ConfigurationService configurationService;

    @Inject
    @RestClient
    ConfigurationApi configurationApi;

    @GET
    @Path("/definition/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response get(@PathParam("type") String type) {
        return Response.status(Response.Status.OK).entity(configurationService.findByType(type)).build();
    }

    @GET
    @Path("/definition")
    @Produces(MediaType.APPLICATION_JSON)
    public Response list() {
        return Response.status(Response.Status.OK).entity(configurationService.list()).build();
    }


    @POST
    @Path("/definition")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response save(DocumentSchema documentDefinition) {
        configurationService.save(documentDefinition);
        return Response.status(Response.Status.CREATED).build();
    }

}