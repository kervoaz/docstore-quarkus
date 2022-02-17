package com.zou;

import com.zou.type.DocumentDefinition;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author HO.CKERVOAZOU
 */
@QuarkusTest
class DocumentConfigurationResourceTest {

    @Test
    void findAll() {
        given().pathParam("id", "BL")
                .when().get("/document/definitions/definition/{id}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is("{\"functionalType\":\"BL\",\"allowRevision\":true,\"ecmMetadata\":null}"));
    }

    @Test
    void save() {
        DocumentDefinition documentDefinition = new DocumentDefinition("BL", true);
        given().body(documentDefinition).contentType(ContentType.JSON)
                .when().post("/document/definitions/definition")
                .then()
                .statusCode(201)
                .body(is(""));
    }
}