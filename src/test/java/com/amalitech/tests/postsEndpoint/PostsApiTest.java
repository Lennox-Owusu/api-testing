package com.amalitech.tests.postsEndpoint;

import com.amalitech.config.TestConfig;
import com.amalitech.data.TestData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostsApiTest extends TestConfig {

    @Test
    @Order(1)
    void testGetAllPosts() {
        given()

                .when()
                .get("/posts")

                .then()
                .statusCode(200)
                .header("Content-Type", containsString(ContentType.JSON.toString()))
                .body("size()", greaterThan(0));
    }

    @Test
    @Order(2)
    void testGetSinglePost() {
        given()

                .when()
                .get("/posts/1")

                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    @Test
    @Order(3)
    void testCreatePost() {
        given()
                .body(TestData.createPostBody())

                .when()
                .post("/posts")

                .then()
                .statusCode(201)
                .body("title", equalTo("AmaliTech Test Post"))
                .body("body", equalTo("This is a test post body"))
                .body("userId", equalTo(1))
                .body("id", notNullValue());
    }

    @Test
    @Order(4)
    void testUpdatePost() {
        given()
                .body(TestData.updatePostBody())

                .when()
                .put("/posts/1")

                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("body", equalTo("Updated body content"));
    }

    @Test
    @Order(5)
    void testDeletePost() {
        given()

                .when()
                .delete("/posts/1")

                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    void testGetNonExistentPost() {
        given()

                .when()
                .get("/posts/99999")

                .then()
                .statusCode(404);
    }
}