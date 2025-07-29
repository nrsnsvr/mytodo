package com.mytodo.constants;

/**
 * API endpoint'leri için constants
 */
public final class ApiEndpoints {
    
    // Private constructor to prevent instantiation
    private ApiEndpoints() {}
    
    // Base URLs
    public static final String BASE_URL = "http://localhost:9000";
    public static final String AUTH_BASE = "/auth";
    public static final String TODO_BASE = "/todo";
    public static final String USER_BASE = "/user";
    
    // Authentication endpoints
    public static final String LOGIN = AUTH_BASE + "/login";
    public static final String REGISTER = AUTH_BASE + "/register";
    public static final String LOGOUT = AUTH_BASE + "/logout";
    public static final String REFRESH_TOKEN = AUTH_BASE + "/refresh";
    
    // Todo endpoints
    public static final String CREATE_TODO = TODO_BASE + "/new";
    public static final String GET_TODO_BY_ID = TODO_BASE + "/{id}";
    public static final String UPDATE_TODO = TODO_BASE + "/{id}";
    public static final String DELETE_TODO = TODO_BASE + "/{id}";
    public static final String GET_TODO_SUMMARY = TODO_BASE + "/summary";
    
    // User endpoints
    public static final String GET_USER_PROFILE = USER_BASE + "/profile";
    public static final String UPDATE_USER_PROFILE = USER_BASE + "/profile";
    
    // Query parameters
    public static final String TYPE_PARAM = "type";
    public static final String CATEGORY_PARAM = "category";
    public static final String DUE_PARAM = "due";
    
    // Path parameters
    public static final String ID_PATH_PARAM = "id";
} 