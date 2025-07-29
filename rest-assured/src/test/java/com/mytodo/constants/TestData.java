package com.mytodo.constants;

/**
 * Test data için constants
 */
public final class TestData {
    
    // Private constructor to prevent instantiation
    private TestData() {}
    
    // Test user credentials
    public static final String TEST_USER_EMAIL = "apitest@example.com";
    public static final String TEST_USER_PASSWORD = "testpassword123";
    public static final String TEST_USER_USERNAME = "apitestuser";
    public static final String TEST_USER_PHONE = "1234567890";
    
    // Invalid test data
    public static final String INVALID_EMAIL = "invalid@example.com";
    public static final String INVALID_PASSWORD = "wrongpassword";
    public static final String EMPTY_STRING = "";
    
    // Todo test data
    public static final String TODO_TASK_PREFIX = "Test Todo Item";
    public static final String TODO_CATEGORY_PERSONAL = "personal";
    public static final String TODO_CATEGORY_WORK = "work";
    public static final String TODO_CATEGORY_URGENT = "urgent";
    public static final String TODO_DATETIME = "2025-07-29T12:00:00.000Z";
    public static final String TODO_DATETIME_FUTURE = "2025-07-30T14:00:00.000Z";
    
    // Non-existent IDs
    public static final String NON_EXISTENT_TODO_ID = "507f1f77bcf86cd799439011";
    public static final String NON_EXISTENT_USER_ID = "507f1f77bcf86cd799439012";
    
    // Error messages
    public static final String ERROR_INCORRECT_CREDENTIALS = "Incorrect email or password";
    public static final String ERROR_TASK_NOT_FOUND = "Task not found";
    public static final String ERROR_TASK_ALREADY_EXISTS = "Task already exists";
    public static final String ERROR_USER_EXISTS = "User with this email exists. Try Login.";
    
    // Success messages
    public static final String SUCCESS_LOGIN = "Login successful";
    public static final String SUCCESS_REGISTER = "Registration successful";
    public static final String SUCCESS_TODO_CREATED = "successfully added";
    public static final String SUCCESS_TODO_UPDATED = "successfully edited";
    public static final String SUCCESS_TODO_DELETED = "successfully deleted";
    
    // Headers
    public static final String HEADER_AUTHORIZATION = "Authorization";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String HEADER_ACCEPT = "Accept";
    public static final String BEARER_PREFIX = "Bearer ";
    
    // Content types
    public static final String CONTENT_TYPE_JSON = "application/json";
    
    // Query parameters
    public static final String QUERY_TYPE_TODAY = "today";
    public static final String QUERY_TYPE_OVERDUE = "overdue";
    public static final String QUERY_TYPE_UPCOMING = "upcoming";
    public static final String QUERY_TYPE_COMPLETED = "completed";
    public static final String QUERY_DUE_ASC = "1";
    public static final String QUERY_DUE_DESC = "-1";
    public static final String CATEGORY_PARAM = "category";
    public static final String DUE_PARAM = "due";
} 