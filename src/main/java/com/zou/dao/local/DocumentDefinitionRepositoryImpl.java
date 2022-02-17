package com.zou.dao.local;

import com.zou.dao.DocumentDefinitionRepository;
import com.zou.type.*;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * @author HO.CKERVOAZOU
 */
@ApplicationScoped
public class DocumentDefinitionRepositoryImpl /*implements DocumentDefinitionRepository*/ {

    @Inject
    DocumentDefinitionRepository documentDefinitionRepository;

    public EcmMetadata getMetadataConfiguration(DocumentCategory documentType) {
        EcmMetadata ecmMetadata = new EcmMetadata();
        if (documentType.getFunctionalType().compareTo(FunctionalType.BL) > 0) {
            ecmMetadata.add("xBL", "required");
        }
        return ecmMetadata;
    }

    //    @Override
    public DocumentDefinition findByType(String documentType) {
        return null;
    }

    //    @Override
    public void save(DocumentDefinition documentDefinition) {
        documentDefinitionRepository.save(documentDefinition);
    }

    public DocumentDefinitionRepositoryImpl() {
        MetadataConfiguration metadataConfiguration = new MetadataConfiguration();
//        metadataConfiguration
    }

}
