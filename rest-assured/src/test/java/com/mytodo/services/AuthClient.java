package com.mytodo.services;

import com.mytodo.constants.ApiEndpoints;
import com.mytodo.constants.HttpStatusCodes;
import com.mytodo.constants.TestData;
import com.mytodo.constants.TokenManager;
import com.mytodo.models.AuthRequest;
import com.mytodo.models.AuthResponse;
import com.mytodo.spec.RequestSpec;
import com.mytodo.spec.ResponseSpec;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Client class for Authentication API operations
 */
public class AuthClient {

    /**
     * Successful login
     */
    public Response login(String email, String password) {
        AuthRequest request = AuthRequest.builder()
                .email(email)
                .password(password)
                .build();

        return given()
                .spec(RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    /**
     * Login with invalid email
     */
    public Response loginWithInvalidEmail(String email, String password) {
        AuthRequest request = AuthRequest.builder()
                .email(email)
                .password(password)
                .build();

        return given()
                .spec(RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    /**
     * Login with invalid password
     */
    public Response loginWithInvalidPassword(String email, String password) {
        AuthRequest request = AuthRequest.builder()
                .email(email)
                .password(password)
                .build();

        return given()
                .spec(RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    /**
     * Login with empty email
     */
    public Response loginWithEmptyEmail(String password) {
        AuthRequest request = AuthRequest.builder()
                .email(TestData.EMPTY_STRING)
                .password(password)
                .build();

        return given()
                .spec(RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    /**
     * Login with empty password
     */
    public Response loginWithEmptyPassword(String email) {
        AuthRequest request = AuthRequest.builder()
                .email(email)
                .password(TestData.EMPTY_STRING)
                .build();

        return given()
                .spec(RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    /**
     * Login with malformed JSON
     */
    public Response loginWithMalformedJson(String malformedPayload) {
        return given()
                .spec(RequestSpec.getBaseRequestSpec())
                .body(malformedPayload)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    /**
     * Login with missing email field
     */
    public Response loginWithMissingEmail(String password) {
        AuthRequest request = AuthRequest.builder()
                .password(password)
                .build();

        return given()
                .spec(RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    /**
     * Login with missing password field
     */
    public Response loginWithMissingPassword(String email) {
        AuthRequest request = AuthRequest.builder()
                .email(email)
                .build();

        return given()
                .spec(RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);
    }

    /**
     * Get login token (using TokenManager)
     */
    public String getLoginToken(String email, String password) {
        return TokenManager.getUserToken(email, password);
    }

    /**
     * Get login token with test user (using TokenManager)
     */
    public String getTestUserToken() {
        return TokenManager.getTestUserToken();
    }

    /**
     * Verify login success
     */
    public boolean verifyLoginSuccess(String email, String password) {
        Response response = login(email, password);
        
        return response.getStatusCode() == HttpStatusCodes.OK &&
               response.jsonPath().getBoolean("success") &&
               response.jsonPath().getString("data.object.accessToken") != null;
    }

    /**
     * Verify login failure
     */
    public boolean verifyLoginFailure(String email, String password, int expectedStatusCode) {
        Response response = login(email, password);
        
        return response.getStatusCode() == expectedStatusCode &&
               !response.jsonPath().getBoolean("success");
    }

    /**
     * Verify if token is valid (using TokenManager)
     */
    public boolean verifyTokenValid(String token) {
        return TokenManager.isTokenValid(token);
    }

    /**
     * Check token format
     */
    public boolean verifyTokenFormat(String token) {
        return TokenManager.isValidTokenFormat(token);
    }

    /**
     * Verify token with API
     */
    public boolean verifyTokenWithApi(String token) {
        return TokenManager.verifyTokenWithApi(token);
    }

    /**
     * Clear cached token
     */
    public void clearCachedToken() {
        TokenManager.clearCachedToken();
    }
} 