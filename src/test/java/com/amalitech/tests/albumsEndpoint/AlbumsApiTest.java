package com.amalitech.tests.albumsEndpoint;

import com.amalitech.config.TestConfig;
import com.amalitech.data.TestData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AlbumsApiTest extends TestConfig {

    @Test
    @Order(1)
    void testGetAllAlbums() {
        given()

                .when()
                .get("/albums")

                .then()
                .statusCode(200)
                .header("Content-Type", containsString(ContentType.JSON.toString()))
                .body("size()", greaterThan(0));
    }

    @Test
    @Order(2)
    void testGetSingleAlbum() {
        given()

                .when()
                .get("/albums/1")

                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", notNullValue());
    }

    @Test
    @Order(3)
    void testCreateAlbum() {
        given()
                .body(TestData.createAlbumBody())

                .when()
                .post("/albums")

                .then()
                .statusCode(201)
                .body("title", equalTo("AmaliTech Test Album"))
                .body("userId", equalTo(1))
                .body("id", notNullValue());
    }

    @Test
    @Order(4)
    void testUpdateAlbum() {
        given()
                .body(TestData.updateAlbumBody())

                .when()
                .put("/albums/1")

                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Album Title"));
    }

    @Test
    @Order(5)
    void testDeleteAlbum() {
        given()

                .when()
                .delete("/albums/1")

                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    void testGetNonExistentAlbum() {
        given()

                .when()
                .get("/albums/99999")

                .then()
                .statusCode(404);
    }
}
