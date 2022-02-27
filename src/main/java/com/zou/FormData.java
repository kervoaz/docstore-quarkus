package com.zou;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.ws.rs.FormParam;
import javax.ws.rs.core.MediaType;
import java.io.InputStream;
import java.time.OffsetDateTime;

/**
 * @author HO.CKERVOAZOU
 */


public class FormData {

    @FormParam("file")
    @PartType(MediaType.APPLICATION_OCTET_STREAM)
    @NotNull
    public InputStream fileContent;

    @FormParam("filename")
    @PartType(MediaType.TEXT_PLAIN)
    @NotEmpty
    public String fileName;

    @FormParam("documentId")
    @PartType(MediaType.TEXT_PLAIN)
    @NotEmpty
    public String documentId;

    @FormParam("mimetype")
    @PartType(MediaType.TEXT_PLAIN)
    public String mimeType;

    @FormParam("meta")
    @PartType(MediaType.TEXT_PLAIN)
    public String meta;

    @FormParam("documentDate")
    @PartType(MediaType.TEXT_PLAIN)
    public OffsetDateTime documentDate;

    @FormParam("fileAnalyzer")
    @PartType(MediaType.TEXT_PLAIN)
    public Boolean fileAnalyzer = false;
}
