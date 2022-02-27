package com.zou.service;

import com.zou.type.DocumentSchema;
import com.zou.type.EcmDocument;
import com.zou.type.FileContent;
import com.zou.type.FunctionalType;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.SequenceInputStream;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.Map;

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
        DocumentSchema type = new DocumentSchema(FunctionalType.BL.toString(), true);
        ecmDocument.setDocumentSchema(type);
        ecmDocument.setCreatedAt(OffsetDateTime.now());
        ecmDocument.setUpdatedAt(OffsetDateTime.now());
        FileContent fileContent = new FileContent();
        ByteArrayInputStream in1 = new ByteArrayInputStream("blabla content of file 1 ".getBytes());
        ByteArrayInputStream in2 = new ByteArrayInputStream("blabla content of file 2".getBytes());
        fileContent.setContent(new SequenceInputStream(in1, in2));
        fileContent.setCompressed(false);
        fileContent.setOriginalName("readme.txt");
        fileContent.setMimeType("txt");
        ecmDocument.setFileContent(fileContent);
        Map<String, String> ecmMetadata = new HashMap<>();
        ecmMetadata.put("zou", "test");
        ecmDocument.setMetadata(ecmMetadata);

        EcmDocument res = ecmDocumentService.save(ecmDocument);
        res.getId();
    }
}