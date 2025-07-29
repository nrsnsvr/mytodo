package com.mytodo.constants;

import com.mytodo.models.AuthRequest;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Utility class for token management
 * Manages authentication tokens centrally
 */
public class TokenManager {

    private static String cachedAuthToken;
    private static long tokenExpiryTime;
    private static final long TOKEN_VALIDITY_DURATION = 24 * 60 * 60 * 1000; // 24 hours (milliseconds)

    /**
     * Get authentication token for test user
     * Token is cached and validity period is checked
     */
    public static String getTestUserToken() {
        // Check if cached token is valid
        if (isTokenValid(cachedAuthToken)) {
            return cachedAuthToken;
        }

        // Get new token
        String newToken = fetchTokenFromApi();
        if (newToken != null) {
            cacheToken(newToken);
        }
        
        return newToken;
    }

    /**
     * Get authentication token for specific user
     */
    public static String getUserToken(String email, String password) {
        AuthRequest request = AuthRequest.builder()
                .email(email)
                .password(password)
                .build();

        Response response = given()
                .spec(com.mytodo.spec.RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);

        if (response.getStatusCode() == HttpStatusCodes.OK) {
            return response.jsonPath().getString("data.object.accessToken");
        }

        return null;
    }

    /**
     * Check if token is valid
     */
    public static boolean isTokenValid(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        // Check if token cache has expired
        if (System.currentTimeMillis() > tokenExpiryTime) {
            return false;
        }

        return true;
    }

    /**
     * Cache the token
     */
    private static void cacheToken(String token) {
        cachedAuthToken = token;
        tokenExpiryTime = System.currentTimeMillis() + TOKEN_VALIDITY_DURATION;
    }

    /**
     * Clear cached token
     */
    public static void clearCachedToken() {
        cachedAuthToken = null;
        tokenExpiryTime = 0;
    }

    /**
     * Get new token from API
     */
    private static String fetchTokenFromApi() {
        AuthRequest request = AuthRequest.builder()
                .email(TestData.TEST_USER_EMAIL)
                .password(TestData.TEST_USER_PASSWORD)
                .build();

        Response response = given()
                .spec(com.mytodo.spec.RequestSpec.getBaseRequestSpec())
                .body(request)
                .when()
                .post(ApiEndpoints.LOGIN);

        if (response.getStatusCode() == HttpStatusCodes.OK) {
            return response.jsonPath().getString("data.object.accessToken");
        }

        return null;
    }

    /**
     * Verify token validity with API
     */
    public static boolean verifyTokenWithApi(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        try {
            Response response = given()
                    .spec(com.mytodo.spec.RequestSpec.getAuthenticatedRequestSpec(token))
                    .when()
                    .get(ApiEndpoints.GET_TODO_SUMMARY);

            return response.getStatusCode() == HttpStatusCodes.OK;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Check token format
     */
    public static boolean isValidTokenFormat(String token) {
        return token != null && 
               token.startsWith("eyJ") && 
               token.split("\\.").length == 3;
    }

    /**
     * Print token information (for debug)
     */
    public static void printTokenInfo(String token) {
        if (token == null) {
            System.out.println("Token: null");
            return;
        }

        System.out.println("Token: " + token.substring(0, Math.min(20, token.length())) + "...");
        System.out.println("Token Length: " + token.length());
        System.out.println("Valid Format: " + isValidTokenFormat(token));
        System.out.println("Valid with API: " + verifyTokenWithApi(token));
    }
} 