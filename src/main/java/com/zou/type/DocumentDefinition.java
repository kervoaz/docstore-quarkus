package com.zou.type;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbAttribute;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;
import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbPartitionKey;

/**
 * @author HO.CKERVOAZOU
 */
@DynamoDbBean
public class DocumentDefinition {
    String functionalType;
    boolean allowRevision;
    EcmMetadata ecmMetadata;

    public DocumentDefinition() {
    }

    public DocumentDefinition(String functionalType, boolean allowRevision) {
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

    public EcmMetadata getEcmMetadata() {
        return ecmMetadata;
    }

    public void setEcmMetadata(EcmMetadata ecmMetadata) {
        this.ecmMetadata = ecmMetadata;
    }
}
