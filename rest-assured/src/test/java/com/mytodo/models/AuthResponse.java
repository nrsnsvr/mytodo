package com.mytodo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthResponse {
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("data")
    private AuthData data;
    
    @JsonProperty("error")
    private AuthError error;

    // Default constructor
    public AuthResponse() {}

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public AuthData getData() {
        return data;
    }

    public void setData(AuthData data) {
        this.data = data;
    }

    public AuthError getError() {
        return error;
    }

    public void setError(AuthError error) {
        this.error = error;
    }

    // Inner classes for nested objects
    public static class AuthData {
        @JsonProperty("object")
        private AuthObject object;
        
        @JsonProperty("message")
        private String message;

        public AuthObject getObject() {
            return object;
        }

        public void setObject(AuthObject object) {
            this.object = object;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }

    public static class AuthObject {
        @JsonProperty("username")
        private String username;
        
        @JsonProperty("email")
        private String email;
        
        @JsonProperty("phone")
        private String phone;
        
        @JsonProperty("accessToken")
        private String accessToken;
        
        @JsonProperty("role")
        private String role;
        
        @JsonProperty("categories")
        private String[] categories;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getAccessToken() {
            return accessToken;
        }

        public void setAccessToken(String accessToken) {
            this.accessToken = accessToken;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public String[] getCategories() {
            return categories;
        }

        public void setCategories(String[] categories) {
            this.categories = categories;
        }
    }

    public static class AuthError {
        @JsonProperty("message")
        private String message;
        
        @JsonProperty("object")
        private AuthObject object;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public AuthObject getObject() {
            return object;
        }

        public void setObject(AuthObject object) {
            this.object = object;
        }
    }
} 