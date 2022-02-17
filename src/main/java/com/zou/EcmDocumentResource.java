package com.zou;

import com.zou.service.EcmDocumentService;
import com.zou.service.MetadataService;
import com.zou.type.*;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;
import software.amazon.awssdk.services.s3.S3Client;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.time.OffsetDateTime;
import java.util.Arrays;

/**
 * @author HO.CKERVOAZOU
 */
@Path("/ecmDocument")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EcmDocumentResource {

    @Inject
    S3Client s3;

    @Inject
    EcmDocumentService documentService;

    @Inject
    MetadataService metadataService;

    @GET
    @Path("/{id}")
    public Response findDocument(@PathParam("id") String id, @QueryParam("revision") int revision) {
        return Response.ok(documentService.find(id, revision)).build();
    }

    @GET
    @Path("/download/{id}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getContent(@PathParam("id") String id, @QueryParam("revision") int revision) {
        FileContent fileContent = documentService.getContent(id, revision);
        Response.ResponseBuilder response = Response.ok((StreamingOutput) output -> fileContent.getContentDownloaded().writeTo(output));
        response.header("Content-Disposition", "attachment;filename=" + fileContent.getOriginalName());
        response.header("Content-Type", fileContent.getMimeType());
        return response.build();

    }

    @POST
    @Path("upload/{id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadFile(@MultipartForm FormData formData, @PathParam("id") String id) throws Exception {
        try {
            if (!validateInput(formData)) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            EcmDocument ecmDocument = asEcmDocument(id, formData);
            EcmDocument savedDoc = documentService.save(ecmDocument);

            if (savedDoc.getId() == null) {
                return Response.serverError().build();
            }
            return Response.ok().status(Response.Status.CREATED).entity(new EcmDocumentView(savedDoc)).build();
        } catch (Exception e) {
            return Response.ok().status(Response.Status.BAD_REQUEST).entity(new ErrorView(e)).build();
        }
    }

    boolean validateInput(FormData input) {
        boolean isValid = true;
        if (input.fileName == null || input.fileName.isEmpty()) {
            isValid = false;
        }
        if (input.mimeType == null || input.mimeType.isEmpty()) {
            isValid = false;
        }
        if (input.documentType == null || input.documentType.isEmpty() || Arrays.stream(FunctionalType.values()).noneMatch(x -> x.toString().equalsIgnoreCase(input.documentType))) {
            isValid = false;
        }
        return isValid;
    }

    EcmDocument asEcmDocument(String id, FormData formData) {
        EcmDocument ecmDocument = new EcmDocument(id);
        FileContent fileContent = new FileContent();
        fileContent.setContent(formData.data);
        fileContent.setOriginalName(formData.fileName);
        fileContent.setMimeType(formData.mimeType);
        ecmDocument.setFileContent(fileContent);
        ecmDocument.setCreatedAt(OffsetDateTime.now());
        ecmDocument.setEcmMetadata(metadataService.parse(formData.meta));
        DocumentCategory documentCategory = new DocumentCategory(FunctionalType.valueOf(formData.documentType));
        ecmDocument.setDocumentCategory(documentCategory);

        metadataService.validate(ecmDocument.getEcmMetadata());
        return ecmDocument;
    }
}
