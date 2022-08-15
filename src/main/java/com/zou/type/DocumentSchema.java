package com.zou.type;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author HO.CKERVOAZOU
 */
@DynamoDbBean
public class DocumentSchema {
    @NotEmpty
    String functionalType;
    @NotNull
    boolean allowRevision;
    Map<String, String> mandatoryMetadata;

    String storagePathPattern;

    public DocumentSchema() {
    }

    public DocumentSchema(String functionalType, boolean allowRevision) {
        this.functionalType = functionalType;
        this.allowRevision = allowRevision;
    }

    @DynamoDbPartitionKey
    @DynamoDbAttribute("functionalType")
    public String getFunctionalType() {
        return functionalType;
    }

    public void setFunctionalType(String functionalType) {
        this.functionalType = functionalType;
    }

    public boolean isAllowRevision() {
        return allowRevision;
    }

    public void setAllowRevision(boolean allowRevision) {
        this.allowRevision = allowRevision;
    }

    public Map<String, String> getMandatoryMetadata() {
        return mandatoryMetadata;
    }

    public void setMandatoryMetadata(Map<String, String> mandatoryMetadata) {
        this.mandatoryMetadata = mandatoryMetadata;
    }

    public String getStoragePathPattern() {
        return storagePathPattern;
    }

    public void setStoragePathPattern(String storagePathPattern) {
        this.storagePathPattern = storagePathPattern;
    }
}
