package com.zou.service;

import com.zou.type.DocumentSchema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author HO.CKERVOAZOU
 */
public interface ConfigurationService {
    DocumentSchema findByType(@NotEmpty String documentType);

    void save(@NotNull DocumentSchema documentDefinition);
}
