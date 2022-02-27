package com.zou.service.impl;

import com.zou.dao.DocumentContentRepository;
import com.zou.dao.DocumentMetadataRepository;
import com.zou.service.EcmDocumentService;
import com.zou.service.MetadataService;
import com.zou.type.EcmDocument;
import com.zou.type.FileContent;
import com.zou.type.StorageInformation;
import com.zou.type.exception.DocumentValidationException;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class EcmDocumentServiceImpl implements EcmDocumentService {
    @Inject
    DocumentMetadataRepository documentMetadataRepository;

    @Inject
    DocumentContentRepository documentContentRepository;

    @Inject
    MetadataService metadataService;

    @Override
    public FileContent getContent(@NotEmpty String id, @Positive int revision) {
        EcmDocument ecmDocument = documentMetadataRepository.findByIdAndRevision(id, revision);
//        Response.ok()
        return documentContentRepository.download(ecmDocument);
    }

    @Override
    public List<EcmDocument> find(@NotEmpty String id) {
        return documentMetadataRepository.findById(id);
    }

    @Override
    public EcmDocument get(@NotEmpty String id,
                           @Positive int revision) {
        if (revision > 999) {
            //lastest rev
            return documentMetadataRepository.findById(id).stream().sorted(Comparator.comparing(EcmDocument::getRevision).reversed()).collect(Collectors.toList()).get(0);
        } else {
            return documentMetadataRepository.findByIdAndRevision(id, revision);
        }
    }

    @Override
    public EcmDocument save(@NotNull EcmDocument ecmDocument) {
        EcmDocument lastDocumentStored;

        lastDocumentStored = documentMetadataRepository.findLastRevision(ecmDocument.getId());
        if (lastDocumentStored != null) {
            if (!ecmDocument.getDocumentSchema().getFunctionalType().equalsIgnoreCase(lastDocumentStored.getDocumentSchema().getFunctionalType())) {
                throw new DocumentValidationException("Document type mismatch: Expected:" + lastDocumentStored.getDocumentSchema().getFunctionalType() + " received:" + ecmDocument.getDocumentSchema().getFunctionalType());
            }
            if (!ecmDocument.getDocumentSchema().isAllowRevision()) {
                throw new DocumentValidationException("This type of document cannot have more than one revision");
            }
            ecmDocument.setRevision(lastDocumentStored.getRevision());
        }
        ecmDocument.setRevision(ecmDocument.getRevision() + 1);
        metadataService.addMandatory(ecmDocument);
        StorageInformation storageInformation = documentContentRepository.upload(ecmDocument);
        ecmDocument.setStorageInformation(storageInformation);
        documentMetadataRepository.save(ecmDocument);
        return ecmDocument;
    }

    @Override
    public EcmDocument delete(@NotEmpty String id, @Positive int revision) {
        EcmDocument ecmDocument = documentMetadataRepository.findByIdAndRevision(id, revision);
        documentContentRepository.delete(ecmDocument);
        documentMetadataRepository.deleteByIdAndRevision(id, revision);
        return ecmDocument;
    }
}
