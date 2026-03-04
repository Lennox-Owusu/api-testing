package com.amalitech.tests.usersEndpoint;

import com.amalitech.config.TestConfig;
import com.amalitech.data.TestData;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UsersApiTest extends TestConfig {

    // GET - Fetch all users
    @Test
    @Order(1)
    void testGetAllUsers() {
        given()
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString(ContentType.JSON.toString()))
                .body("size()", greaterThan(0));
    }

    // GET - Fetch single user by ID
    @Test
    @Order(2)
    void testGetSingleUser() {
        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString(ContentType.JSON.toString()))
                .body("id", equalTo(1))
                .body("name", notNullValue())
                .body("username", notNullValue())
                .body("email", notNullValue())
                .body("phone", notNullValue())
                .body("website", notNullValue());
    }

    // GET - Validate nested address fields of a single user
    @Test
    @Order(3)
    void testGetUserAddressFields() {
        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("address.street", notNullValue())
                .body("address.suite", notNullValue())
                .body("address.city", notNullValue())
                .body("address.zipcode", notNullValue())
                .body("address.geo.lat", notNullValue())
                .body("address.geo.lng", notNullValue());
    }

    // GET - Validate nested company fields of a single user
    @Test
    @Order(4)
    void testGetUserCompanyFields() {
        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body("company.name", notNullValue())
                .body("company.catchPhrase", notNullValue())
                .body("company.bs", notNullValue());
    }

    // POST - Create a new user
    @Test
    @Order(5)
    void testCreateUser() {
        given()
                .contentType(ContentType.JSON)
                .body(TestData.createUserBody())
                .when()
                .post("/users")
                .then()
                .statusCode(201)
                .body("name", equalTo("Lennox Owusu"))
                .body("username", equalTo("lennox"))
                .body("email", equalTo("lennox@amalitech.com"))
                .body("phone", equalTo("1-234-567-8900"))
                .body("website", equalTo("amalitech.com"))
                .body("id", notNullValue());
    }

    // PUT - Update an existing user
    @Test
    @Order(6)
    void testUpdateUser() {
        given()
                .contentType(ContentType.JSON)
                .body(TestData.updateUserBody())
                .when()
                .put("/users/1")
                .then()
                .statusCode(200)
                .body("id", equalTo(1))
                .body("name", equalTo("Updated User"))
                .body("username", equalTo("updateduser"))
                .body("email", equalTo("updated@amalitech.com"));
    }

    // DELETE - Delete a user
    @Test
    @Order(7)
    void testDeleteUser() {
        given()
                .when()
                .delete("/users/1")
                .then()
                .statusCode(200);
    }

    // GET - Validate JSON schema of a single user
    @Test
    @Order(8)
    void testGetUserJsonSchema() {
        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .body(matchesJsonSchemaInClasspath("user-schema.json"));
    }

    // GET - Validate response headers
    @Test
    @Order(9)
    void testResponseHeaders() {
        given()
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .header("Content-Type", containsString("application/json"))
                .header("Connection", equalTo("keep-alive"));
    }

    // GET - Filter users by query parameter
    @Test
    @Order(10)
    void testGetUsersByQueryParam() {
        given()
                .queryParam("id", 1)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1))
                .body("[0].id", equalTo(1))
                .body("[0].name", notNullValue());
    }

    // GET - Non-existent user should return 404
    @Test
    @Order(11)
    void testGetNonExistentUser() {
        given()
                .when()
                .get("/users/99999")
                .then()
                .statusCode(404);
    }
}
