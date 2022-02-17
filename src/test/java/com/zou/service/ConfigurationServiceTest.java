package com.zou.service;

import com.zou.type.DocumentDefinition;
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
        DocumentDefinition documentDefinition = new DocumentDefinition("BL", true);
        configurationService.save(documentDefinition);
    }
}