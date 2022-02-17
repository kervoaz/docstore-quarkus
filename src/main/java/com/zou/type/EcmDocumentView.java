package com.zou.type;

import java.time.OffsetDateTime;

/**
 * @author HO.CKERVOAZOU
 */
public class EcmDocumentView {
    String id;
    int revision;
    DocumentCategory documentCategory;
    EcmMetadata ecmMetadata;
    OffsetDateTime createdAt;
    String origin;
    String originalFileName;
//    StorageInformation storageInformation;

    public EcmDocumentView(EcmDocument in) {
        this.id = in.getId();
        this.revision = in.getRevision();
        this.documentCategory = in.getDocumentCategory();
        this.ecmMetadata = in.getEcmMetadata();
        this.originalFileName = in.getFileContent().getOriginalName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    public DocumentCategory getEcmDocumentType() {
        return documentCategory;
    }

    public void setEcmDocumentType(DocumentCategory documentCategory) {
        this.documentCategory = documentCategory;
    }

    public EcmMetadata getEcmMetadata() {
        return ecmMetadata;
    }

    public void setEcmMetadata(EcmMetadata ecmMetadata) {
        this.ecmMetadata = ecmMetadata;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
}
