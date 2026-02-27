package com.amalitech;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PostsApiTest {

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "https://jsonplaceholder.typicode.com";
    }

    // GET - Fetch all posts
    @Test
    @Order(1)
    void testGetAllPosts() {
        given()
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("size()", equalTo(100));
    }

    // GET - Fetch single post
    @Test
    @Order(2)
    void testGetSinglePost() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", equalTo(1))
                .body("title", notNullValue())
                .body("body", notNullValue());
    }

    // POST - Create a new post
    @Test
    @Order(3)
    void testCreatePost() {
        String requestBody = """
                {
                    "title": "AmaliTech Test Post",
                    "body": "This is a test post body",
                    "userId": 1
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("/posts")
                .then()
                .statusCode(201)
                .body("title", equalTo("AmaliTech Test Post"))
                .body("body", equalTo("This is a test post body"))
                .body("userId", equalTo(1))
                .body("id", notNullValue());
    }

    // PUT - Update an existing post
    @Test
    @Order(4)
    void testUpdatePost() {
        String requestBody = """
                {
                    "id": 1,
                    "title": "Updated Title",
                    "body": "Updated body content",
                    "userId": 1
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .put("/posts/1")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Title"))
                .body("body", equalTo("Updated body content"));
    }

    // DELETE - Delete a post
    @Test
    @Order(5)
    void testDeletePost() {
        given()
                .when()
                .delete("/posts/1")
                .then()
                .statusCode(200);
    }
    // GET - Validate JSON schema of a single post
    @Test
    @Order(6)
    void testGetPostJsonSchema() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("post-schema.json"));
    }

    // Validate response headers
    @Test
    @Order(7)
    void testResponseHeaders() {
        given()
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .header("Connection", equalTo("keep-alive"));
    }

    // GET - Non-existent post should return 404
    @Test
    @Order(8)
    void testGetNonExistentPost() {
        given()
                .when()
                .get("/posts/99999")
                .then()
                .statusCode(404);
    }


}
