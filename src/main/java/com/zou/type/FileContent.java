package com.zou.type;


import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbIgnore;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 * @author HO.CKERVOAZOU
 */
@DynamoDbBean
public class FileContent {
    InputStream content;
    ByteArrayOutputStream contentDownloaded;
    String originalName;
    String mimeType;
    boolean compressed;

    @DynamoDbIgnore
    public InputStream getContent() {
        return content;
    }

    public void setContent(InputStream content) {
        this.content = content;
    }

    @DynamoDbIgnore
    public ByteArrayOutputStream getContentDownloaded() {
        return contentDownloaded;
    }

    public void setContentDownloaded(ByteArrayOutputStream contentDownloaded) {
        this.contentDownloaded = contentDownloaded;
    }

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public boolean isCompressed() {
        return compressed;
    }

    public void setCompressed(boolean compressed) {
        this.compressed = compressed;
    }
}
