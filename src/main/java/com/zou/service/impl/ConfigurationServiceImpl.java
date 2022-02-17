package com.zou.service.impl;

import com.zou.dao.DocumentDefinitionRepository;
import com.zou.service.ConfigurationService;
import com.zou.type.DocumentDefinition;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class ConfigurationServiceImpl implements ConfigurationService {
    @Inject
    private DocumentDefinitionRepository documentDefinitionRepository;

    @Override
    public DocumentDefinition findByType(String documentType) {
        return documentDefinitionRepository.findByType(documentType);
    }

    @Override
    public void save(DocumentDefinition documentDefinition) {
        documentDefinitionRepository.save(documentDefinition);
    }
}
