package com.mytodo.api;

import com.mytodo.constants.HttpStatusCodes;
import com.mytodo.constants.TestData;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.*;

public class AuthenticationApiTest extends BaseApiTest {

    private void assertLoginFailure(Response response, int expectedStatus) {
        response.then()
                .statusCode(expectedStatus)
                .body("success", equalTo(false))
                .body("error.message", containsString(TestData.ERROR_INCORRECT_CREDENTIALS))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), expectedStatus);
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
    }

    @Test
    @Description("Test successful login with valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    public void testSuccessfulLogin() {
        Response response = getAuthClient().login(TestData.TEST_USER_EMAIL, TestData.TEST_USER_PASSWORD);
        response.then()
                .statusCode(HttpStatusCodes.OK)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("data.object.accessToken", notNullValue())
                .body("data.object.email", equalTo(TestData.TEST_USER_EMAIL))
                .log().ifValidationFails();
        Assert.assertNotNull(response.jsonPath().getString("data.object.accessToken"), "Access token should not be null");
        Assert.assertEquals(response.jsonPath().getString("data.object.email"), TestData.TEST_USER_EMAIL, "Email should match");
    }

    @Test
    @Description("Test failed login with invalid email")
    @Severity(SeverityLevel.NORMAL)
    public void testFailedLoginWithInvalidEmail() {
        Response response = getAuthClient().loginWithInvalidEmail(TestData.INVALID_EMAIL, TestData.TEST_USER_PASSWORD);
        assertLoginFailure(response, HttpStatusCodes.NOT_FOUND);
    }

    @Test
    @Description("Test failed login with invalid password")
    @Severity(SeverityLevel.NORMAL)
    public void testFailedLoginWithInvalidPassword() {
        Response response = getAuthClient().loginWithInvalidPassword(TestData.TEST_USER_EMAIL, TestData.INVALID_PASSWORD);
        assertLoginFailure(response, HttpStatusCodes.UNAUTHORIZED);
    }

    @Test
    @Description("Test failed login with empty email")
    @Severity(SeverityLevel.NORMAL)
    public void testFailedLoginWithEmptyEmail() {
        Response response = getAuthClient().loginWithEmptyEmail(TestData.TEST_USER_PASSWORD);
        assertLoginFailure(response, HttpStatusCodes.NOT_FOUND);
    }

    @Test
    @Description("Test failed login with empty password")
    @Severity(SeverityLevel.NORMAL)
    public void testFailedLoginWithEmptyPassword() {
        Response response = getAuthClient().loginWithEmptyPassword(TestData.TEST_USER_EMAIL);
        assertLoginFailure(response, HttpStatusCodes.UNAUTHORIZED);
    }

    @Test
    @Description("Test failed login with malformed JSON")
    @Severity(SeverityLevel.NORMAL)
    public void testFailedLoginWithMalformedJson() {
        String malformedPayload = "{\n" +
                "    \"email\": \"" + TestData.TEST_USER_EMAIL + "\",\n" +
                "    \"password\": \"" + TestData.TEST_USER_PASSWORD + "\",\n" +
                "}"; // Extra comma
        Response response = getAuthClient().loginWithMalformedJson(malformedPayload);
        response.then()
                .statusCode(HttpStatusCodes.INTERNAL_SERVER_ERROR)
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.INTERNAL_SERVER_ERROR);
    }

    @Test
    @Description("Test failed login with missing email field")
    @Severity(SeverityLevel.NORMAL)
    public void testFailedLoginWithMissingEmail() {
        Response response = getAuthClient().loginWithMissingEmail(TestData.TEST_USER_PASSWORD);
        assertLoginFailure(response, HttpStatusCodes.NOT_FOUND);
    }

    @Test
    @Description("Test failed login with missing password field")
    @Severity(SeverityLevel.NORMAL)
    public void testFailedLoginWithMissingPassword() {
        Response response = getAuthClient().loginWithMissingPassword(TestData.TEST_USER_EMAIL);
        response.then()
                .statusCode(HttpStatusCodes.INTERNAL_SERVER_ERROR)
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.INTERNAL_SERVER_ERROR);
    }
} 