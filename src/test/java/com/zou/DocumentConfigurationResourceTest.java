package com.zou;

import com.zou.type.DocumentSchema;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

/**
 * @author HO.CKERVOAZOU
 */
@QuarkusTest
class DocumentConfigurationResourceTest {

    @Test
    void get() {
        given().pathParam("type", "BL")
                .when().get("/document/definitions/definition/{type}")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body(is("{\"functionalType\":\"BL\",\"allowRevision\":true,\"ecmMetadata\":null}"));
    }

    @Test
    void save() {
        DocumentSchema documentDefinitionBL = new DocumentSchema("BL", true);
        Map<String, String> metaMandatory = new HashMap<>();
        metaMandatory.put("xBL", "String");
        documentDefinitionBL.setMandatoryMetadata(metaMandatory);
        given().body(documentDefinitionBL).contentType(ContentType.JSON)
                .when().post("/document/definitions/definition")
                .then()
                .statusCode(201)
                .body(is(""));

        DocumentSchema documentDefinitionINV = new DocumentSchema("INVOICE", false);
        given().body(documentDefinitionINV).contentType(ContentType.JSON)
                .when().post("/document/definitions/definition")
                .then()
                .statusCode(201)
                .body(is(""));
    }
}