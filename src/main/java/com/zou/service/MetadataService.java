package com.zou.service;

import com.zou.type.EcmDocument;

import java.util.Map;

/**
 * @author HO.CKERVOAZOU
 */
public interface MetadataService {
    /**
     * add metadata
     *
     * @param ecmDocument
     */
    void addMandatory(EcmDocument ecmDocument);

    /**
     * Parse metadata received as parameter
     *
     * @param metadataAsString
     * @return
     */
    Map<String, String> parse(String metadataAsString);
}
