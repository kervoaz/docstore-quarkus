package com.zou.service.impl;

import com.zou.FormData;
import com.zou.service.ConfigurationService;
import com.zou.service.MetadataService;
import com.zou.service.ValidationService;
import com.zou.type.DocumentSchema;
import com.zou.type.EcmDocument;
import com.zou.type.EcmDocumentBase;
import com.zou.type.exception.DocumentValidationException;
import com.zou.type.exception.Formatter;
import com.zou.type.exception.UserInputValidationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class ValidationServiceImpl implements ValidationService {

    @Inject
    Validator validator;

    @Inject
    ConfigurationService configurationService;

    @Inject
    MetadataService metadataService;

    @Override
    public void validate(EcmDocument ecmDocument) {
        Set<ConstraintViolation<EcmDocument>> violations = validator.validate(ecmDocument);
        if (!violations.isEmpty()) {
            Formatter<EcmDocument> formatter = new Formatter<>();
            throw new DocumentValidationException(formatter.formatValidation(violations));
        }
    }

    @Override
    public void validate(EcmDocumentBase ecmDocumentBase) {
        Set<ConstraintViolation<EcmDocumentBase>> violations = validator.validate(ecmDocumentBase);
        if (!violations.isEmpty()) {
            Formatter<EcmDocumentBase> formatter = new Formatter<>();
            throw new DocumentValidationException(formatter.formatValidation(violations));
        }
    }

    @Override
    public void validate(FormData formData) {
        Set<ConstraintViolation<FormData>> violations = validator.validate(formData);
        if (!violations.isEmpty()) {
            Formatter<FormData> formatter = new Formatter<>();
            throw new UserInputValidationException(formatter.formatValidation(violations));
        }
    }

    @Override
    public void validate(FormData formData, String documentType) {
        DocumentSchema documentSchema = configurationService.findByType(documentType);
        if (documentSchema == null) {
            throw new UserInputValidationException("DocumentType is not correct and document schema cannot be found");
        }
        validate(formData);
        Map<String, String> postedMeta = metadataService.parse(formData.meta);
        List<String> missingMeta = new ArrayList<>();
        for (String mandatory : documentSchema.getMandatoryMetadata().keySet()) {
            if (postedMeta.get(mandatory) == null || postedMeta.get(mandatory).isEmpty()) {
                missingMeta.add(mandatory);
            }
        }
        if (!missingMeta.isEmpty()) {
            throw new UserInputValidationException("Missing metadata " + missingMeta.toString() + " for current documentType " + documentType);
        }
    }
}
