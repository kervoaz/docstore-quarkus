package com.zou;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import software.amazon.awssdk.http.HttpStatusCode;

import java.io.File;
import java.net.SocketException;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author HO.CKERVOAZOU
 */
@QuarkusTest
class EcmDocumentResourceTest {
    @Test
    void getFileNotExisting() {
        given().pathParam("documentId", "doesntexists").pathParam("revision", 1)
                .when().get("/ecmDocument/{documentId}/{revision}")
                .then()
                .statusCode(HttpStatusCode.NO_CONTENT);
    }

    @Test
    void uploadFilePdfTooBig() {
        assertThrows(SocketException.class, () ->
                given().multiPart("file", new File("src\\test\\resources\\quarkus-fascicle-practising-v1.pdf"))
                        .multiPart("filename", "forceName.jpg")
                        .pathParam("documentType", "INVOICE")
                        .when().post("/ecmDocument/upload/{documentType}/document")
                        .then()
                        .statusCode(200)
                        .body("uuid", notNullValue()));
    }

//    @Test
//    void uploadFileNotExisting() {
//        assertThrows(SocketException.class, () ->
//                given().multiPart("file", new File("src\\test\\resources\\doesntexists"))
//                        .multiPart("filename", "forceName.jpg")
//                        .pathParam("documentType", "INVOICE")
//                        .when().post("/ecmDocument/upload/{documentType}/document")
//                        .then()
//                        .statusCode(400)
//                        .body("message", containsString("documentId")));
//    }

    @Test
    void uploadGetDeleteFileInvoicePdf() {
        String documentId = "test_INVOICE_zou1";

        given().multiPart("file", new File("src\\test\\resources\\cl-4-reasons-try-quarkus-checklist-f19180cs-201909-a4-fr.pdf"))
                .multiPart("documentId", documentId)
                .multiPart("filename", "invoice_and_4copies.pdf")
                .multiPart("meta", "xINV=1,xTEST=2")
                .pathParam("documentType", "INVOICE")
                .when().post("/ecmDocument/upload/{documentType}/document")
                .then()
                .statusCode(201)
                .body("uuid", notNullValue());

        given().pathParam("documentId", documentId).pathParam("revision", 1)
                .when().get("/ecmDocument/{documentId}/{revision}")
                .then()
                .statusCode(200)
                .body("id", equalTo(documentId))
                .body("revision", equalTo(1));

        given().multiPart("file", new File("src\\test\\resources\\cl-4-reasons-try-quarkus-checklist-f19180cs-201909-a4-fr.pdf"))
                .multiPart("documentId", documentId)
                .multiPart("filename", "invoice_and_4copies.pdf")
                .multiPart("meta", "xINV=1,xTEST=2")
                .pathParam("documentType", "INVOICE")
                .when().post("/ecmDocument/upload/{documentType}/document")
                .then()
                .statusCode(400)
                .body("message", equalTo("This type of document cannot have more than one revision"));
        ;

        given().pathParam("documentId", documentId).pathParam("revision", 1)
                .when().delete("/ecmDocument/{documentId}/{revision}")
                .then()
                .statusCode(200)
                .body("id", equalTo(documentId))
                .body("revision", equalTo(1));
    }

    @Test
    void uploadGetDeleteFileBLPdf() {
        String documentId = "test_BL_zou1";

        given().multiPart("file", new File("src\\test\\resources\\cl-4-reasons-try-quarkus-checklist-f19180cs-201909-a4-fr.pdf"))
                .multiPart("documentId", documentId)
                .multiPart("filename", "invoice_and_4copies.pdf")
                .multiPart("meta", "xINV=1,xTEST=2")
                .pathParam("documentType", "BL")
                .when().post("/ecmDocument/upload/{documentType}/document")
                .then()
                .statusCode(201)
                .body("uuid", notNullValue())
                .body("revision", equalTo(1));

        given().pathParam("documentId", documentId).pathParam("revision", 1)
                .when().get("/ecmDocument/{documentId}/{revision}")
                .then()
                .statusCode(200)
                .body("id", equalTo(documentId))
                .body("revision", equalTo(1));

        given().multiPart("file", new File("src\\test\\resources\\cl-4-reasons-try-quarkus-checklist-f19180cs-201909-a4-fr.pdf"))
                .multiPart("documentId", documentId)
                .multiPart("filename", "invoice_and_4copies.pdf")
                .multiPart("meta", "xINV=1,xTEST=2")
                .pathParam("documentType", "BL")
                .when().post("/ecmDocument/upload/{documentType}/document")
                .then()
                .statusCode(201)
                .body("revision", equalTo(2));

        given().pathParam("documentId", documentId).pathParam("revision", 2)
                .when().get("/ecmDocument/{documentId}/{revision}")
                .then()
                .statusCode(200)
                .body("id", equalTo(documentId))
                .body("revision", equalTo(2));

        given().pathParam("documentId", documentId).pathParam("revision", 1)
                .when().delete("/ecmDocument/{documentId}/{revision}")
                .then()
                .statusCode(200)
                .body("id", equalTo(documentId))
                .body("revision", equalTo(1));

        given().pathParam("documentId", documentId)
                .queryParam("revision", 2)
                .when().get("/ecmDocument/download/{documentId}")
                .then()
                .statusCode(200);

        given().pathParam("documentId", documentId).pathParam("revision", 2)
                .when().delete("/ecmDocument/{documentId}/{revision}")
                .then()
                .statusCode(200)
                .body("id", equalTo(documentId))
                .body("revision", equalTo(2));
    }

}