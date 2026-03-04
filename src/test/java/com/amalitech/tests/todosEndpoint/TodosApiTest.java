package com.amalitech.tests.todosEndpoint;

import com.amalitech.config.TestConfig;
import com.amalitech.data.TestData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TodosApiTest extends TestConfig {

    @Test
    @Order(1)
    void testGetAllTodos() {
        given()

                .when()
                .get("/todos")

                .then()
                .statusCode(200)
                .header("Content-Type", containsString(ContentType.JSON.toString()))
                // If dataset is fixed you can use equalTo(200)
                .body("size()", greaterThan(0));
    }

    @Test
    @Order(2)
    void testGetSingleTodo() {
        given()

                .when()
                .get("/todos/1")

                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("userId", notNullValue())
                .body("title", notNullValue())
                .body("completed", anyOf(is(true), is(false)));
    }

    @Test
    @Order(3)
    void testCreateTodo() {
        given()
                .body(TestData.createTodoBody())

                .when()
                .post("/todos")

                .then()
                .statusCode(201)
                .body("userId", equalTo(1))
                .body("title", equalTo("AmaliTech Test Todo"))
                .body("completed", is(false))
                .body("id", notNullValue());
    }

    @Test
    @Order(4)
    void testUpdateTodo() {
        given()
                .body(TestData.updateTodoBody())

                .when()
                .put("/todos/1")

                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Todo Title"))
                .body("completed", is(true));
    }

    @Test
    @Order(5)
    void testDeleteTodo() {
        given()

                .when()
                .delete("/todos/1")

                .then()
                .statusCode(200);
    }

    @Test
    @Order(6)
    void testGetNonExistentTodo() {
        given()

                .when()
                .get("/todos/99999")

                .then()
                .statusCode(404);
    }
}