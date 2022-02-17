package com.zou.service;

import com.zou.type.EcmMetadata;

/**
 * @author HO.CKERVOAZOU
 */
public interface MetadataService {
    EcmMetadata parse(String metadataAsString);

    void validate(EcmMetadata ecmMetadata);
}
