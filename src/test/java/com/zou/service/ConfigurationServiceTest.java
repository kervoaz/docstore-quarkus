package com.zou.service;

import com.zou.type.DocumentSchema;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

/**
 * @author HO.CKERVOAZOU
 */
@QuarkusTest
class ConfigurationServiceTest {
    @Inject
    ConfigurationService configurationService;

    @Test
    void findByType() {
    }

    @Test
    void save() {
        DocumentSchema documentDefinition = new DocumentSchema("BL", true);
        configurationService.save(documentDefinition);
    }
}