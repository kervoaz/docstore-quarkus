package com.zou.service.impl;

import com.zou.dao.DocumentContentRepository;
import com.zou.dao.DocumentMetadataRepository;
import com.zou.service.EcmDocumentService;
import com.zou.type.EcmDocument;
import com.zou.type.FileContent;
import com.zou.type.StorageInformation;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class EcmDocumentServiceImpl implements EcmDocumentService {
    @Inject
    DocumentMetadataRepository documentMetadataRepository;

    @Inject
    DocumentContentRepository documentContentRepository;

    @Override
    public FileContent getContent(String id, int revision) {
        EcmDocument ecmDocument = documentMetadataRepository.findByIdAndRevision(id, revision);
//        Response.ok()
        return documentContentRepository.download(ecmDocument);
    }

    @Override
    public List<EcmDocument> find(@NotNull String id, int revision) {
        return revision == 0 ? documentMetadataRepository.findById(id) :
                Arrays.asList(documentMetadataRepository.findByIdAndRevision(id, revision));
    }

    @Override
    public EcmDocument save(EcmDocument ecmDocument) {
        EcmDocument lastDocumentStored;

        lastDocumentStored = documentMetadataRepository.findLastRevision(ecmDocument.getId());
        if (lastDocumentStored != null) {
            if (!ecmDocument.getDocumentCategory().getFunctionalType().toString().equalsIgnoreCase(lastDocumentStored.getDocumentCategory().getFunctionalType().toString())) {
                throw new RuntimeException("Document type mismatch: Expected:" + lastDocumentStored.getDocumentCategory().getFunctionalType().toString() + " received:" + ecmDocument.getDocumentCategory().getFunctionalType().toString());
            }
            if (!ecmDocument.getDocumentCategory().isAllowRevision()) {
                throw new RuntimeException("This type of document cannot have more than one revision");
            }
            lastDocumentStored.setFileContent(ecmDocument.getFileContent());
        } else {
            lastDocumentStored = ecmDocument;
        }
        lastDocumentStored.setRevision(lastDocumentStored.getRevision() + 1);
        StorageInformation storageInformation = documentContentRepository.upload(lastDocumentStored.getFileContent(),
                lastDocumentStored.getEcmMetadata());
        lastDocumentStored.setStorageInformation(storageInformation);
        documentMetadataRepository.save(lastDocumentStored);

        return lastDocumentStored;
    }
}
