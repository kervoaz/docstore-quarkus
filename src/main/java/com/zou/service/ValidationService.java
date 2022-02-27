package com.zou.service;

import com.zou.FormData;
import com.zou.type.EcmDocument;
import com.zou.type.EcmDocumentBase;

import javax.validation.constraints.NotNull;

/**
 * @author HO.CKERVOAZOU
 */
public interface ValidationService {

    void validate(@NotNull EcmDocument ecmDocument);

    void validate(@NotNull EcmDocumentBase ecmDocument);

    void validate(@NotNull FormData formData);
}
