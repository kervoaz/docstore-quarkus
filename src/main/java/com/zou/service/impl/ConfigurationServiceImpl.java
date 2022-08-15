package com.zou.service.impl;

import com.zou.dao.DocumentDefinitionRepository;
import com.zou.service.ConfigurationService;
import com.zou.type.DocumentSchema;
import io.quarkus.cache.CacheResult;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class ConfigurationServiceImpl implements ConfigurationService {
    @Inject
    DocumentDefinitionRepository documentDefinitionRepository;

    @Override
    @CacheResult(cacheName = "documentSchema-cache")
    public DocumentSchema findByType(@NotEmpty String documentType) {
        return documentDefinitionRepository.findByType(documentType);
    }

    @Override
    public List<DocumentSchema> list() {
        return documentDefinitionRepository.list();
    }

    @Override
    public void save(@NotNull DocumentSchema documentDefinition) {
        documentDefinitionRepository.store(documentDefinition);
    }
}
