package com.zou.dao;

import com.zou.type.DocumentSchema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author HO.CKERVOAZOU
 */
public interface DocumentDefinitionRepository {
    DocumentSchema findByType(@NotEmpty String documentType);

    void store(@NotNull final DocumentSchema documentSchema);
}
