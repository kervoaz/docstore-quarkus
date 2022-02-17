package com.zou.type;

/**
 * @author HO.CKERVOAZOU
 */
public class MetadataConfiguration {
    String documentType;
    EcmMetadata metadata;

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public EcmMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(EcmMetadata metadata) {
        this.metadata = metadata;
    }
}
