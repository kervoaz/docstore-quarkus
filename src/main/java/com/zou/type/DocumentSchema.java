package com.zou.type;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

import java.util.Map;

/**
 * @author HO.CKERVOAZOU
 */
@DynamoDbBean
public class DocumentSchema {
    String functionalType;
    boolean allowRevision;
    Map<String, String> mandatoryMetadata;

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

    public Map<String, String> getEcmMetadata() {
        return mandatoryMetadata;
    }

    public void setEcmMetadata(Map<String, String> mandatoryMetadata) {
        this.mandatoryMetadata = mandatoryMetadata;
    }
}
