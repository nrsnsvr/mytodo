package com.mytodo.api;

import com.mytodo.constants.HttpStatusCodes;
import com.mytodo.constants.TestData;
import com.mytodo.enums.TodoCategory;
import com.mytodo.enums.TodoStatus;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ItemsApiTest extends BaseApiTest {

    private static String createdTodoId;
    private static String createdTaskName;

    // --- Negative Tests (priority 1-10) ---

    @Test(priority = 1)
    @Description("Create todo with missing required fields")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateTodoWithMissingFields() {
        Response response = getTodoClient().createTodoWithMissingFields(TodoCategory.PERSONAL, TestData.TODO_DATETIME);
        response.then()
                .statusCode(HttpStatusCodes.INTERNAL_SERVER_ERROR)
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.INTERNAL_SERVER_ERROR);
    }

    @Test(priority = 2)
    @Description("Create todo with empty task")
    @Severity(SeverityLevel.NORMAL)
    public void testCreateTodoWithEmptyTask() {
        Response response = getTodoClient().createTodoWithEmptyTask(TodoCategory.PERSONAL, TestData.TODO_DATETIME);
        response.then()
                .statusCode(HttpStatusCodes.INTERNAL_SERVER_ERROR)
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.INTERNAL_SERVER_ERROR);
    }

    @Test(priority = 3)
    @Description("Get non-existent todo")
    @Severity(SeverityLevel.NORMAL)
    public void testGetNonExistentTodo() {
        Response response = getTodoClient().getNonExistentTodo();
        response.then()
                .statusCode(HttpStatusCodes.NOT_FOUND)
                .body("success", equalTo(false))
                .body("message", containsString(TestData.ERROR_TASK_NOT_FOUND))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.NOT_FOUND);
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
    }

    @Test(priority = 4)
    @Description("Update non-existent todo")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateNonExistentTodo() {
        Response response = getTodoClient().updateNonExistentTodo("Updated Task", TodoCategory.WORK, TestData.TODO_DATETIME_FUTURE);
        response.then()
                .statusCode(HttpStatusCodes.NOT_FOUND)
                .body("success", equalTo(false))
                .body("message", containsString(TestData.ERROR_TASK_NOT_FOUND))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.NOT_FOUND);
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
    }

    @Test(priority = 5)
    @Description("Delete non-existent todo")
    @Severity(SeverityLevel.NORMAL)
    public void testDeleteNonExistentTodo() {
        Response response = getTodoClient().deleteNonExistentTodo();
        response.then()
                .statusCode(HttpStatusCodes.NOT_FOUND)
                .body("success", equalTo(false))
                .body("message", containsString(TestData.ERROR_TASK_NOT_FOUND))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.NOT_FOUND);
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
    }

    @Test(priority = 6)
    @Description("Access todo endpoints without authentication")
    @Severity(SeverityLevel.CRITICAL)
    public void testUnauthorizedAccess() {
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/todo/summary")
                .then()
                .statusCode(HttpStatusCodes.FORBIDDEN)
                .log().ifValidationFails();
    }

    // --- Positive Tests (priority 11+) ---

    /**
     * Helper: Create a todo and return its ID (with retry for consistency)
     */
    private String createTodoAndGetId(String taskName, TodoCategory category, String datetime) {
        getTodoClient().createTodo(taskName, category, datetime)
                .then().statusCode(HttpStatusCodes.CREATED);
        String todoId = null;
        int retries = 5;
        for (int i = 0; i < retries; i++) {
            todoId = getTodoClient().findTodoIdByTaskName(taskName);
            if (todoId != null && !todoId.isEmpty()) break;
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        Assert.assertNotNull(todoId, "Todo ID should not be null after creation");
        return todoId;
    }

    private void assertTodoDeleted(String todoId) {
        Response response = getTodoClient().getTodoById(todoId);
        response.then()
                .statusCode(HttpStatusCodes.NOT_FOUND)
                .body("success", equalTo(false))
                .body("message", containsString(TestData.ERROR_TASK_NOT_FOUND))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.NOT_FOUND);
        Assert.assertFalse(response.jsonPath().getBoolean("success"));
    }

    @Test(priority = 11)
    @Description("Create new todo successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testCreateTodo() {
        createdTaskName = TestData.TODO_TASK_PREFIX + " " + System.currentTimeMillis();
        Response response = getTodoClient().createTodo(createdTaskName, TodoCategory.PERSONAL, TestData.TODO_DATETIME);
        response.then()
                .statusCode(HttpStatusCodes.CREATED)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("message", containsString(TestData.SUCCESS_TODO_CREATED))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.CREATED);
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        // Find and store the created todo ID for later tests
        createdTodoId = null;
        int retries = 5;
        for (int i = 0; i < retries; i++) {
            createdTodoId = getTodoClient().findTodoIdByTaskName(createdTaskName);
            if (createdTodoId != null && !createdTodoId.isEmpty()) break;
            try { Thread.sleep(200); } catch (InterruptedException ignored) {}
        }
        Assert.assertNotNull(createdTodoId, "Todo ID should not be null after creation");
    }

    @Test(priority = 12, dependsOnMethods = "testCreateTodo")
    @Description("Get specific todo by ID")
    @Severity(SeverityLevel.NORMAL)
    public void testGetTodoById() {
        Assert.assertNotNull(createdTodoId, "Created todo ID should not be null");
        Response response = getTodoClient().getTodoById(createdTodoId);
        response.then()
                .statusCode(HttpStatusCodes.OK)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("message._id", equalTo(createdTodoId))
                .body("message.task", equalTo(createdTaskName))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.OK);
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        Assert.assertEquals(response.jsonPath().getString("message._id"), createdTodoId);
        Assert.assertEquals(response.jsonPath().getString("message.task"), createdTaskName);
    }

    @Test(priority = 13, dependsOnMethods = "testCreateTodo")
    @Description("Update todo successfully")
    @Severity(SeverityLevel.NORMAL)
    public void testUpdateTodo() {
        Assert.assertNotNull(createdTodoId, "Created todo ID should not be null");
        String updatedTask = "Updated Todo Task " + System.currentTimeMillis();
        Response response = getTodoClient().updateTodo(
                createdTodoId,
                updatedTask,
                TodoCategory.WORK,
                TestData.TODO_DATETIME_FUTURE,
                TodoStatus.COMPLETED
        );
        response.then()
                .statusCode(HttpStatusCodes.OK)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("message", containsString(TestData.SUCCESS_TODO_UPDATED))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.OK);
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
        // Update the task name for later verification
        createdTaskName = updatedTask;
    }

    @Test(priority = 14, dependsOnMethods = "testCreateTodo")
    @Description("Delete todo successfully")
    @Severity(SeverityLevel.CRITICAL)
    public void testDeleteTodo() {
        Assert.assertNotNull(createdTodoId, "Created todo ID should not be null");
        Response response = getTodoClient().deleteTodo(createdTodoId);
        response.then()
                .statusCode(HttpStatusCodes.OK)
                .contentType(ContentType.JSON)
                .body("success", equalTo(true))
                .body("message", containsString(TestData.SUCCESS_TODO_DELETED))
                .log().ifValidationFails();
        Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.OK);
        Assert.assertTrue(response.jsonPath().getBoolean("success"));
    }

    @Test(priority = 15, dependsOnMethods = "testDeleteTodo")
    @Description("Verify todo is deleted")
    @Severity(SeverityLevel.NORMAL)
    public void testVerifyTodoDeleted() {
        if (createdTodoId != null) {
            Response response = getTodoClient().getTodoById(createdTodoId);
            response.then()
                    .statusCode(HttpStatusCodes.NOT_FOUND)
                    .body("success", equalTo(false))
                    .body("message", containsString(TestData.ERROR_TASK_NOT_FOUND))
                    .log().ifValidationFails();
            Assert.assertEquals(response.getStatusCode(), HttpStatusCodes.NOT_FOUND);
            Assert.assertFalse(response.jsonPath().getBoolean("success"));
        }
    }
} 