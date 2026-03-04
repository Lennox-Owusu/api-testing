package com.amalitech.tests.photosEndpoint;

import com.amalitech.config.TestConfig;
import com.amalitech.data.TestData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PhotosApiTest extends TestConfig {

    @Test
    @Order(1)
    void testGetAllPhotos() {
        given()

                .when()
                .get("/photos")

                .then()
                .statusCode(200)
                .header("Content-Type", containsString(ContentType.JSON.toString()))
                // If dataset is fixed you can use equalTo(5000)
                .body("size()", greaterThan(0));
    }

    @Test
    @Order(2)
    void testGetSinglePhoto() {
        given()

                .when()
                .get("/photos/1")

                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("albumId", notNullValue())
                .body("title", notNullValue())
                .body("url", startsWith("http"))
                .body("thumbnailUrl", startsWith("http"));
    }

    @Test
    @Order(3)
    void testCreatePhoto() {
        given()
                .body(TestData.createPhotoBody())

                .when()
                .post("/photos")

                .then()
                .statusCode(201)
                .body("albumId", equalTo(1))
                .body("title", equalTo("AmaliTech Test Photo"))
                .body("url", startsWith("http"))
                .body("thumbnailUrl", startsWith("http"))
                .body("id", notNullValue());
    }

    @Test
    @Order(4)
    void testUpdatePhoto() {
        given()
                .body(TestData.updatePhotoBody())

                .when()
                .put("/photos/1")

                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Photo Title"))
                .body("url", startsWith("http"))
                .body("thumbnailUrl", startsWith("http"));
    }

    @Test
    @Order(5)
    void testDeletePhoto() {
        given()

                .when()
                .delete("/photos/1")

                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    void testGetNonExistentPhoto() {
        given()

                .when()
                .get("/photos/99999")

                .then()
                .statusCode(404);
    }
}