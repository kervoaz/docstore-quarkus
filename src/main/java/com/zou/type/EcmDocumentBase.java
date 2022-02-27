package com.zou.type;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

/**
 * @author HO.CKERVOAZOU
 */
public class EcmDocumentBase {
    @NotNull
    protected UUID uuid;
    @NotEmpty
    String id;
    @PositiveOrZero
    int revision;
    @NotNull
    DocumentSchema documentSchema;
    Map<String, String> metadata;
    OffsetDateTime createdAt;
    OffsetDateTime updatedAt;
    String origin;

    public EcmDocumentBase() {
        this.uuid = UUID.randomUUID();
    }

    public UUID getUuid() {
        return uuid;
    }

    @Deprecated
    public void setUuid(UUID uuid) {
        //needed for dynamoDB
        this.uuid = uuid;
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

    public DocumentSchema getDocumentSchema() {
        return documentSchema;
    }

    public void setDocumentSchema(DocumentSchema documentSchema) {
        this.documentSchema = documentSchema;
    }

    public Map<String, String> getMetadata() {
        return metadata;
    }

    public void setMetadata(Map<String, String> metadata) {
        this.metadata = metadata;
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


    public OffsetDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(OffsetDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
