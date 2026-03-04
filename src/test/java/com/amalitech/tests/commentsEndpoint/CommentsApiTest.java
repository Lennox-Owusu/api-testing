package com.amalitech.tests.commentsEndpoint;

import com.amalitech.config.TestConfig;
import com.amalitech.data.TestData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CommentsApiTest extends TestConfig {

    @Test
    @Order(1)
    void testGetAllComments() {
        given()

                .when()
                .get("/comments")

                .then()
                .statusCode(200)
                .header("Content-Type", containsString(ContentType.JSON.toString()))
                .body("size()", greaterThan(0));
    }

    @Test
    @Order(2)
    void testGetSingleComment() {
        given()

                .when()
                .get("/comments/1")

                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("postId", notNullValue())
                .body("name", notNullValue())
                .body("email", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    @Order(3)
    void testCreateComment() {
        given()
                .body(TestData.createCommentBody())

                .when()
                .post("/comments")

                .then()
                .statusCode(201)
                .body("name", equalTo("AmaliTech Comment"))
                .body("email", equalTo("test@amalitech.com"))
                .body("body", equalTo("This is a test comment"))
                .body("id", notNullValue());
    }

    @Test
    @Order(4)
    void testUpdateComment() {
        given()
                .body(TestData.updateCommentBody())

                .when()
                .put("/comments/1")

                .then()
                .statusCode(200)
                .body("name", equalTo("Updated Comment"))
                .body("email", equalTo("updated@amalitech.com"));
    }

    @Test
    @Order(5)
    void testDeleteComment() {
        given()

                .when()
                .delete("/comments/1")

                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    void testGetNonExistentComment() {
        given()

                .when()
                .get("/comments/99999")

                .then()
                .statusCode(404);
    }
}