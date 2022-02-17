package com.zou.dao;

import com.zou.type.EcmDocument;
import com.zou.type.EcmMetadata;
import com.zou.type.FileContent;
import com.zou.type.StorageInformation;

/**
 * @author HO.CKERVOAZOU
 */
public interface DocumentContentRepository {
    StorageInformation upload(FileContent fileContent, EcmMetadata metadata);

    FileContent download(EcmDocument ecmDocument);
}
