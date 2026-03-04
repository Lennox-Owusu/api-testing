package com.amalitech.config;

import org.junit.jupiter.api.AfterAll;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeAll;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import static org.hamcrest.Matchers.lessThan;
import java.time.Duration;

public class TestConfig {

    protected static RequestSpecification requestSpec;
    protected static ResponseSpecification responseSpec;

    @BeforeAll
    static void setup() {

        Properties props = new Properties();

        try (InputStream is = TestConfig.class
                .getClassLoader()
                .getResourceAsStream("test.properties")) {

            if (is == null) {
                throw new RuntimeException("test.properties NOT FOUND in src/test/resources");
            }

            props.load(is);

        } catch (IOException e) {
            throw new RuntimeException("Failed to load test.properties", e);
        }


        String baseUri = props.getProperty("api.baseUri");

        if (baseUri == null || baseUri.isBlank()) {
            throw new RuntimeException("api.baseUri NOT SET in test.properties");
        }

        RestAssured.baseURI = baseUri;
        // -----------------------------------------------------------

        // Default request spec
        requestSpec = new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .log(LogDetail.METHOD)
                .log(LogDetail.URI)
                .build();

        // Default response spec
        responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectResponseTime(lessThan(Duration.ofSeconds(2).toMillis()))
                .build();

        RestAssured.requestSpecification = requestSpec;
        RestAssured.responseSpecification = responseSpec;

        RestAssured.filters(new AllureRestAssured());
    }


    @AfterAll
    static void teardown() {
        System.out.println("Teardown: Resetting REST Assured configuration...");
        RestAssured.reset();
    }

}