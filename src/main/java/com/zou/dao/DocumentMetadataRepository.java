package com.zou.dao;

import com.zou.type.EcmDocument;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author HO.CKERVOAZOU
 */
public interface DocumentMetadataRepository {
    // Store the order item in the database
    void save(EcmDocument ecmDocument);

    EcmDocument findByIdAndRevision(@NotNull String id, @Positive int revision);

    List<EcmDocument> findById(@NotNull String id);

    EcmDocument findLastRevision(@NotNull String id);

}
