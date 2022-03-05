package com.zou;

import com.zou.service.ConfigurationService;
import com.zou.service.EcmDocumentService;
import com.zou.service.MetadataService;
import com.zou.service.ValidationService;
import com.zou.type.*;
import io.quarkus.tika.TikaMetadata;
import io.quarkus.tika.TikaParser;
import lombok.extern.slf4j.Slf4j;
import org.jboss.resteasy.annotations.providers.multipart.MultipartForm;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.InputStream;
import java.time.OffsetDateTime;

/**
 * @author HO.CKERVOAZOU
 */
@Path("/ecmDocument")
@ApplicationScoped
@Slf4j
public class EcmDocumentResource {


    @Inject
    EcmDocumentService documentService;

    @Inject
    ValidationService validationService;

    @Inject
    ConfigurationService configurationService;

    @Inject
    MetadataService metadataService;

    @Inject
    TikaParser parser;


    @GET
    @Path("/{documentId}/{revision}")
    public Response get(@PathParam("documentId") String documentId, @PathParam("revision") int revision) {
        EcmDocument res = documentService.get(documentId, revision);
        if (res == null) {
            return Response.ok(null).status(Response.Status.NO_CONTENT).build();
        } else {
            Response resp = Response.ok().status(Response.Status.OK).entity(res).build();
            return resp;
        }
    }

    @DELETE
    @Path("/{documentId}/{revision}")
    public Response delete(@PathParam("documentId") String documentId, @PathParam("revision") int revision) {
        EcmDocument res = documentService.delete(documentId, revision);
        Response resp = Response.ok().status(Response.Status.OK).entity(res).build();
        return resp;

    }

    @GET
    @Path("/{documentId}/latest")
    public Response getLast(@PathParam("documentId") String documentId) {
        return Response.ok(documentService.get(documentId, 999)).build();
    }

    @GET
    @Path("/{documentId}")
    public Response getById(@PathParam("documentId") String documentId) {
        return Response.ok(documentService.find(documentId)).build();
    }

    @GET
    @Path("/download/{documentId}")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@PathParam("documentId") String documentId, @QueryParam("revision") int revision) {
        FileContent fileContent = documentService.getContent(documentId, revision);
        Response.ResponseBuilder response = Response.ok((StreamingOutput) output -> fileContent.getContentDownloaded().writeTo(output));
        response.header("Content-Disposition", "attachment;filename=" + fileContent.getOriginalName());
        response.header("Content-Type", fileContent.getMimeType());
        return response.build();

    }

    @POST
    @Path("/upload/{documentType}/document")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response uploadFile(@MultipartForm FormData formData, @PathParam("documentType") String documentType) {
        try {
            validationService.validate(formData, documentType);
            EcmDocument ecmDocument = asEcmDocument(documentType, formData);
            metadataService.addMandatory(ecmDocument);
            validationService.validate((EcmDocumentBase) ecmDocument);
            EcmDocument savedDoc = documentService.save(ecmDocument);
            if (savedDoc.getId() == null) {
                return Response.serverError().build();
            }
            return Response.ok().status(Response.Status.CREATED).entity(new EcmDocumentView(savedDoc)).build();

        } catch (Exception e) {
            log.error("upload failed", e);
            return Response.ok().status(Response.Status.BAD_REQUEST).entity(new ErrorView(e)).build();
        }
    }


    EcmDocument asEcmDocument(String documentType, FormData formData) {
        EcmDocument ecmDocument = new EcmDocument();
        ecmDocument.setId(formData.documentId != null ? formData.documentId : ecmDocument.getUuid().toString());
        ecmDocument.setRevision(0);
        ecmDocument.setCreatedAt(OffsetDateTime.now());
        ecmDocument.setUpdatedAt(OffsetDateTime.now());
        FileContent fileContent = new FileContent();
        fileContent.setOriginalName(formData.fileName);
        if (formData.fileAnalyzer) {
            //SequenceInputStream can be read only one time and reset is not supported--> duplicate stream
            InputStream[] cloned = Utils.duplicateStream(formData.fileContent);
            fileContent.setContent(cloned[0]);
            if (cloned[1] != null) {
                TikaMetadata detectedMetadata = parser.getMetadata(cloned[1]);
                String fileSize = detectedMetadata.getSingleValue("File Size");//TODO ?
                String mimeTypeDetected = detectedMetadata.getSingleValue("Content-Type");
                fileContent.setMimeType(mimeTypeDetected);
            }
        } else {
            fileContent.setContent(formData.fileContent);
        }
        ecmDocument.setFileContent(fileContent);
        ecmDocument.setCreatedAt(OffsetDateTime.now());
        ecmDocument.setMetadata(metadataService.parse(formData.meta));
        //configservice can be already call. be sure that call is cached
        ecmDocument.setDocumentSchema(configurationService.findByType(documentType));
        return ecmDocument;
    }
}
