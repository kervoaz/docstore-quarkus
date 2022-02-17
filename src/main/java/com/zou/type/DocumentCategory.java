package com.zou.type;

import software.amazon.awssdk.enhanced.dynamodb.mapper.annotations.DynamoDbBean;

/**
 * @author HO.CKERVOAZOU
 */
@DynamoDbBean
public class DocumentCategory {
    FunctionalType functionalType;
    boolean allowRevision;

    public DocumentCategory() {
    }

    public DocumentCategory(FunctionalType functionalType) {
        this.functionalType = functionalType;
        if (functionalType.toString().equalsIgnoreCase("BL")) {
            this.allowRevision = true;
        } else {
            this.allowRevision = false;
        }

    }

    public FunctionalType getFunctionalType() {
        return functionalType;
    }

    public void setFunctionalType(FunctionalType functionalType) {
        this.functionalType = functionalType;
    }

    public boolean isAllowRevision() {
        return allowRevision;
    }

    public void setAllowRevision(boolean allowRevision) {
        this.allowRevision = allowRevision;
    }
}
