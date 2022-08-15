package com.zou.service;

import com.zou.type.DocumentSchema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author HO.CKERVOAZOU
 */
public interface ConfigurationService {
    DocumentSchema findByType(@NotEmpty String documentType);

    List<DocumentSchema> list();

    void save(@NotNull DocumentSchema documentDefinition);
}
