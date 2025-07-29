package com.mytodo.services;

import com.mytodo.constants.ApiEndpoints;
import com.mytodo.constants.HttpStatusCodes;
import com.mytodo.constants.TestData;
import com.mytodo.enums.TodoCategory;
import com.mytodo.enums.TodoStatus;
import com.mytodo.models.TodoRequest;
import com.mytodo.models.TodoResponse;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

/**
 * Client class for Todo API operations
 */
public class TodoClient {

    private final RequestSpecification authenticatedRequest;

    public TodoClient(RequestSpecification authenticatedRequest) {
        this.authenticatedRequest = authenticatedRequest;
    }

    /**
     * Get todo summary
     */
    public Response getTodoSummary(String type) {
        return authenticatedRequest
                .when()
                .get(ApiEndpoints.GET_TODO_SUMMARY + "?type=" + type);
    }

    /**
     * Create new todo
     */
    public Response createTodo(String task, TodoCategory category, String datetime) {
        TodoRequest request = TodoRequest.builder()
                .task(task)
                .category(category.getValue())
                .datetime(datetime)
                .build();

        return authenticatedRequest
                .body(request)
                .when()
                .post(ApiEndpoints.CREATE_TODO);
    }

    /**
     * Create todo with full request
     */
    public Response createTodo(TodoRequest request) {
        return authenticatedRequest
                .body(request)
                .when()
                .post(ApiEndpoints.CREATE_TODO);
    }

    /**
     * Get todo by ID
     */
    public Response getTodoById(String todoId) {
        if (todoId == null || todoId.isEmpty()) {
            throw new IllegalArgumentException("Todo ID cannot be null or empty");
        }
        return authenticatedRequest
                .when()
                .get(ApiEndpoints.GET_TODO_BY_ID.replace("{id}", todoId));
    }

    /**
     * Update todo
     */
    public Response updateTodo(String todoId, String task, TodoCategory category, String datetime, TodoStatus status) {
        if (todoId == null || todoId.isEmpty()) {
            throw new IllegalArgumentException("Todo ID cannot be null or empty");
        }
        
        TodoRequest request = TodoRequest.builder()
                .task(task)
                .category(category.getValue())
                .datetime(datetime)
                .isCompleted(status.getValue())
                .build();

        return authenticatedRequest
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put(ApiEndpoints.UPDATE_TODO.replace("{id}", todoId));
    }

    /**
     * Update todo with full request
     */
    public Response updateTodo(String todoId, TodoRequest request) {
        if (todoId == null || todoId.isEmpty()) {
            throw new IllegalArgumentException("Todo ID cannot be null or empty");
        }
        
        return authenticatedRequest
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put(ApiEndpoints.UPDATE_TODO.replace("{id}", todoId));
    }

    /**
     * Delete todo
     */
    public Response deleteTodo(String todoId) {
        if (todoId == null || todoId.isEmpty()) {
            throw new IllegalArgumentException("Todo ID cannot be null or empty");
        }
        
        return authenticatedRequest
                .when()
                .delete(ApiEndpoints.DELETE_TODO.replace("{id}", todoId));
    }

    /**
     * Create todo with missing fields (for error testing)
     */
    public Response createTodoWithMissingFields(TodoCategory category, String datetime) {
        TodoRequest request = TodoRequest.builder()
                .category(category.getValue())
                .datetime(datetime)
                .build();

        return authenticatedRequest
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ApiEndpoints.CREATE_TODO);
    }

    /**
     * Create todo with empty task (for error testing)
     */
    public Response createTodoWithEmptyTask(TodoCategory category, String datetime) {
        TodoRequest request = TodoRequest.builder()
                .task(TestData.EMPTY_STRING)
                .category(category.getValue())
                .datetime(datetime)
                .build();

        return authenticatedRequest
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .post(ApiEndpoints.CREATE_TODO);
    }

    /**
     * Get non-existent todo (for error testing)
     */
    public Response getNonExistentTodo() {
        return authenticatedRequest
                .when()
                .get(ApiEndpoints.GET_TODO_BY_ID.replace("{id}", TestData.NON_EXISTENT_TODO_ID));
    }

    /**
     * Update non-existent todo (for error testing)
     */
    public Response updateNonExistentTodo(String task, TodoCategory category, String datetime) {
        TodoRequest request = TodoRequest.builder()
                .task(task)
                .category(category.getValue())
                .datetime(datetime)
                .build();

        return authenticatedRequest
                .contentType(ContentType.JSON)
                .body(request)
                .when()
                .put(ApiEndpoints.UPDATE_TODO.replace("{id}", TestData.NON_EXISTENT_TODO_ID));
    }

    /**
     * Delete non-existent todo (for error testing)
     */
    public Response deleteNonExistentTodo() {
        return authenticatedRequest
                .when()
                .delete(ApiEndpoints.DELETE_TODO.replace("{id}", TestData.NON_EXISTENT_TODO_ID));
    }

    /**
     * API response format: {"tasks": [...], "counts": {...}}
     */
    public String findTodoIdByTaskName(String taskName) {
        if (taskName == null || taskName.isEmpty()) {
            return null;
        }
        Response response = getTodoSummary(TestData.QUERY_TYPE_TODAY);
        if (response.getStatusCode() == HttpStatusCodes.OK) {
            // API response format: {"tasks": [...], "counts": {...}}
            java.util.List<java.util.Map<String, Object>> tasks = response.jsonPath().getList("tasks");
            if (tasks != null) {
                for (java.util.Map<String, Object> task : tasks) {
                    String taskNameFromResponse = (String) task.get("task");
                    String taskId = (String) task.get("_id");
                    if (taskName.equals(taskNameFromResponse)) {
                        return taskId;
                    }
                }
            }
        }
        return null;
    }

    /**
     * First create todo
     */
    public String createTodoAndGetId(String taskName, TodoCategory category, String datetime) {
        // First create todo
        Response createResponse = createTodo(taskName, category, datetime);
        if (createResponse.getStatusCode() == HttpStatusCodes.CREATED) {
            // Then find its ID
            return findTodoIdByTaskName(taskName);
        }
        return null;
    }

    /**
     * Verify todo created successfully
     */
    public boolean verifyTodoCreated(String taskName) {
        String todoId = findTodoIdByTaskName(taskName);
        return todoId != null && !todoId.isEmpty();
    }

    /**
     * Verify todo deleted successfully
     */
    public boolean verifyTodoDeleted(String todoId) {
        if (todoId == null || todoId.isEmpty()) {
            return false;
        }
        
        Response response = getTodoById(todoId);
        return response.getStatusCode() == HttpStatusCodes.NOT_FOUND;
    }

    /**
     * Verify todo updated successfully
     */
    public boolean verifyTodoUpdated(String todoId, String expectedTask) {
        if (todoId == null || todoId.isEmpty()) {
            return false;
        }
        
        Response response = getTodoById(todoId);
        
        if (response.getStatusCode() == HttpStatusCodes.OK) {
            String actualTask = response.jsonPath().getString("message.task");
            return expectedTask.equals(actualTask);
        }
        
        return false;
    }
} 