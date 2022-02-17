package com.zou;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

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
    public InputStream data;

    @FormParam("filename")
    @PartType(MediaType.TEXT_PLAIN)
    public String fileName;

    @FormParam("mimetype")
    @PartType(MediaType.TEXT_PLAIN)
    public String mimeType;

    @FormParam("documentType")
    @PartType(MediaType.TEXT_PLAIN)
    public String documentType;

    @FormParam("meta")
    @PartType(MediaType.TEXT_PLAIN)
    public String meta;

    @FormParam("documentDate")
    @PartType(MediaType.TEXT_PLAIN)
    public OffsetDateTime documentDate;
}
