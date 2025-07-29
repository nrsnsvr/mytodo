package com.mytodo.spec;

import com.mytodo.constants.TestData;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * REST Assured Request Specification class
 * Used for common request configurations
 */
public class RequestSpec {

    private static RequestSpecification baseRequestSpec;
    private static RequestSpecification authenticatedRequestSpec;

    /**
     * Create base request specification
     */
    public static RequestSpecification getBaseRequestSpec() {
        if (baseRequestSpec == null) {
            baseRequestSpec = new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setAccept(ContentType.JSON)
                    .build();
        }
        return baseRequestSpec;
    }

    /**
     * Create request specification with authentication token
     */
    public static RequestSpecification getAuthenticatedRequestSpec(String authToken) {
        if (authenticatedRequestSpec == null || authToken != null) {
            authenticatedRequestSpec = given()
                    .spec(getBaseRequestSpec())
                    .header(TestData.HEADER_AUTHORIZATION, TestData.BEARER_PREFIX + authToken)
                    .request();
        }
        return authenticatedRequestSpec;
    }

    /**
     * Update request specification with authentication token
     */
    public static RequestSpecification updateAuthenticatedRequestSpec(String authToken) {
        authenticatedRequestSpec = given()
                .spec(getBaseRequestSpec())
                .header(TestData.HEADER_AUTHORIZATION, TestData.BEARER_PREFIX + authToken)
                .request();
        return authenticatedRequestSpec;
    }

    /**
     * Reset base request specification
     */
    public static void resetBaseRequestSpec() {
        baseRequestSpec = null;
    }

    /**
     * Reset authenticated request specification
     */
    public static void resetAuthenticatedRequestSpec() {
        authenticatedRequestSpec = null;
    }

    /**
     * Reset all specifications
     */
    public static void resetAllSpecs() {
        resetBaseRequestSpec();
        resetAuthenticatedRequestSpec();
    }
} 