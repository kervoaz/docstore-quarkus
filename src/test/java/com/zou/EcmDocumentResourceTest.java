package com.zou;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author HO.CKERVOAZOU
 */
@QuarkusTest
class EcmDocumentResourceTest {

    @Test
    void findOneDocument() {
        given().pathParam("id", "zou4")
                .when().get("/ecmDocument/{id}")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .body(is("null"));
    }

    @Test
    void uploadFile() {
        given().pathParam("id", "zou4")
                .when().get("/ecmDocument/upload/{id}")
                .then()
                .statusCode(200)
                .body(is("Hello RESTEasy"));
    }
}