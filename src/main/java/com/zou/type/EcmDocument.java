package com.zou.type;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

import java.time.OffsetDateTime;

/**
 * @author HO.CKERVOAZOU
 */
@DynamoDbBean
@RegisterForReflection
public class EcmDocument {
    private String id;
    private int revision;
    private DocumentCategory documentCategory;
    private EcmMetadata ecmMetadata;
    private OffsetDateTime createdAt;
    private String origin;
    private FileContent fileContent;
    private String uid;
    private OffsetDateTime updatedAt;
    StorageInformation storageInformation;
//    validation?: { isValid: boolean; errors?: any[] };

    public EcmDocument(String id, int revision) {
        this.id = id;
        this.revision = revision;
    }

    public EcmDocument(String id) {
        this.id = id;
    }

    public EcmDocument() {

    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("id")
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @DynamoDbSortKey
    @DynamoDbAttribute("revision")
    public int getRevision() {
        return revision;
    }

    public void setRevision(int revision) {
        this.revision = revision;
    }

    @DynamoDbAttribute("type")
    public DocumentCategory getDocumentCategory() {
        return documentCategory;
    }

    public void setDocumentCategory(DocumentCategory documentCategory) {
        this.documentCategory = documentCategory;
    }

    @DynamoDbAttribute("metadata")
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

    public FileContent getFileContent() {
        return fileContent;
    }

    public void setFileContent(FileContent fileContent) {
        this.fileContent = fileContent;
    }


    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }


    public StorageInformation getStorageInformation() {
        return storageInformation;
    }

    public void setStorageInformation(StorageInformation storageInformation) {
        this.storageInformation = storageInformation;
    }
}
