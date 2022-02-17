package com.zou.service;

import com.zou.type.EcmDocument;
import com.zou.type.FileContent;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author HO.CKERVOAZOU
 */
public interface EcmDocumentService {
    FileContent getContent(@NotNull String id, @NotNull int revision);

    List<EcmDocument> find(@NotNull String id, @NotNull int revision);

    EcmDocument save(@NotNull EcmDocument ecmDocument);
}
