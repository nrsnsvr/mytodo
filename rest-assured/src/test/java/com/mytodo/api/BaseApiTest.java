package com.mytodo.api;

import com.mytodo.constants.ApiEndpoints;
import com.mytodo.constants.TokenManager;
import com.mytodo.services.AuthClient;
import com.mytodo.services.TodoClient;
import com.mytodo.spec.RequestSpec;
import com.mytodo.spec.ResponseSpec;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;

import static io.restassured.RestAssured.given;

public class BaseApiTest {

    protected static String authToken;
    protected static AuthClient authClient;
    protected static TodoClient todoClient;

    @BeforeClass
    static void setUp() {
        RestAssured.baseURI = ApiEndpoints.BASE_URL;

        // Initialize clients
        authClient = new AuthClient();
        
        // Get token using TokenManager
        authToken = TokenManager.getTestUserToken();
        
        if (authToken != null) {
            // Create authenticated request specification
            RequestSpecification authenticatedRequest = RequestSpec.getAuthenticatedRequestSpec(authToken);
            todoClient = new TodoClient(authenticatedRequest);
        } else {
            System.err.println("Failed to obtain authentication token. Tests might fail.");
        }
    }

    @BeforeMethod
    void setAuthHeader() {
        if (authToken != null) {
            RequestSpec.updateAuthenticatedRequestSpec(authToken);
        }
    }

    protected RequestSpecification getAuthenticatedRequest() {
        return RequestSpec.getAuthenticatedRequestSpec(authToken);
    }

    protected AuthClient getAuthClient() {
        return authClient;
    }

    protected TodoClient getTodoClient() {
        return todoClient;
    }

    /**
     * Refresh token (if needed during test)
     */
    protected void refreshToken() {
        TokenManager.clearCachedToken();
        authToken = TokenManager.getTestUserToken();
        if (authToken != null) {
            RequestSpec.updateAuthenticatedRequestSpec(authToken);
        }
    }

    /**
     * Check if token is valid
     */
    protected boolean isTokenValid() {
        return TokenManager.isTokenValid(authToken);
    }

    /**
     * Print token information (for debug)
     */
    protected void printTokenInfo() {
        TokenManager.printTokenInfo(authToken);
    }
} 