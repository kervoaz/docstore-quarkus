package com.zou.dao;

import com.zou.type.DocumentSchema;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author HO.CKERVOAZOU
 */
public interface DocumentDefinitionRepository {
    DocumentSchema findByType(@NotEmpty String documentType);

    List<DocumentSchema> list();

    void store(@NotNull final DocumentSchema documentSchema);
}
