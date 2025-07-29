package com.mytodo.enums;

/**
 * Enum for HTTP methods
 */
public enum HttpMethod {
    
    GET("GET"),
    POST("POST"),
    PUT("PUT"),
    DELETE("DELETE"),
    PATCH("PATCH"),
    HEAD("HEAD"),
    OPTIONS("OPTIONS");
    
    private final String value;
    
    HttpMethod(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    @Override
    public String toString() {
        return value;
    }
    
    /**
     * String değerinden enum'a dönüştürme
     */
    public static HttpMethod fromString(String text) {
        for (HttpMethod method : HttpMethod.values()) {
            if (method.value.equalsIgnoreCase(text)) {
                return method;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
    
    /**
     * Check if method is GET
     */
    public boolean isGet() {
        return this == GET;
    }
    
    /**
     * Check if method is POST
     */
    public boolean isPost() {
        return this == POST;
    }
    
    /**
     * Check if method is PUT
     */
    public boolean isPut() {
        return this == PUT;
    }
    
    /**
     * Check if method is DELETE
     */
    public boolean isDelete() {
        return this == DELETE;
    }
} 