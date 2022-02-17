package com.zou.dao;

import com.zou.type.DocumentDefinition;

/**
 * @author HO.CKERVOAZOU
 */
public interface DocumentDefinitionRepository {
    DocumentDefinition findByType(String documentType);

    void save(DocumentDefinition documentDefinition);
}
