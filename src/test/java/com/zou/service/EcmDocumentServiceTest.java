package com.zou.service;

import com.zou.type.DocumentCategory;
import com.zou.type.EcmDocument;
import com.zou.type.FileContent;
import com.zou.type.FunctionalType;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.time.OffsetDateTime;

/**
 * @author HO.CKERVOAZOU
 */
@QuarkusTest
class EcmDocumentServiceTest {
    @Inject
    EcmDocumentService ecmDocumentService;

    @Test
    void testSave() {
        EcmDocument ecmDocument = new EcmDocument("zou1");

        DocumentCategory type = new DocumentCategory(FunctionalType.BL);
        type.setAllowRevision(true);
        ecmDocument.setDocumentCategory(type);

        ecmDocument.setCreatedAt(OffsetDateTime.now());
        ecmDocument.setUpdatedAt(OffsetDateTime.now());

        FileContent fileContent = new FileContent();
        fileContent.setContent(new ByteArrayInputStream("blabla content of file".getBytes()));
        fileContent.setCompressed(false);
        fileContent.setOriginalName("readme.txt");
        fileContent.setMimeType("txt");
        ecmDocument.setFileContent(fileContent);

        EcmDocument res = ecmDocumentService.save(ecmDocument);
        res.getId();
    }
}