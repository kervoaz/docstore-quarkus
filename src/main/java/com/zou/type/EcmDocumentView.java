package com.zou.type;

/**
 * @author HO.CKERVOAZOU
 */
public class EcmDocumentView extends EcmDocumentBase {

    public EcmDocumentView(EcmDocumentBase in) {
        super();
        this.id = in.getId();
        this.revision = in.getRevision();
        this.documentSchema = in.getDocumentSchema();
        this.metadata = in.getMetadata();
    }
}
