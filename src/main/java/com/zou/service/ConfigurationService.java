package com.zou.service;

import com.zou.type.DocumentDefinition;

/**
 * @author HO.CKERVOAZOU
 */
public interface ConfigurationService {
    DocumentDefinition findByType(String documentType);

    void save(DocumentDefinition documentDefinition);
}
