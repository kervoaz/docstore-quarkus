package com.zou.dao;

import com.zou.type.EcmDocument;
import com.zou.type.FileContent;
import com.zou.type.StorageInformation;

import javax.validation.constraints.NotNull;

/**
 * @author HO.CKERVOAZOU
 */
public interface DocumentContentRepository {
    StorageInformation upload(@NotNull EcmDocument ecmDocument);

    FileContent download(@NotNull EcmDocument ecmDocument);

    void delete(EcmDocument ecmDocument);
}
