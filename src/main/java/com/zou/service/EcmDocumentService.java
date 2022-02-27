package com.zou.service;

import com.zou.type.EcmDocument;
import com.zou.type.FileContent;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * @author HO.CKERVOAZOU
 */
public interface EcmDocumentService {
    FileContent getContent(@NotEmpty String id, @Positive int revision);

    EcmDocument get(@NotEmpty String id,
                    @Positive int revision);

    List<EcmDocument> find(@NotEmpty String id);

    EcmDocument save(@NotNull EcmDocument ecmDocument);

    EcmDocument delete(@NotEmpty String id, @Positive int revision);
}
