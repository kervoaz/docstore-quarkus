package com.zou.type;

import io.quarkus.runtime.annotations.RegisterForReflection;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbSortKey;

/**
 * @author HO.CKERVOAZOU
 */
@DynamoDbBean
@RegisterForReflection
public class EcmDocument extends EcmDocumentBase {

    private FileContent fileContent;
    StorageInformation storageInformation;

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

    public FileContent getFileContent() {
        return fileContent;
    }

    public void setFileContent(FileContent fileContent) {
        this.fileContent = fileContent;
    }

    public StorageInformation getStorageInformation() {
        return storageInformation;
    }

    public void setStorageInformation(StorageInformation storageInformation) {
        this.storageInformation = storageInformation;
    }
}
