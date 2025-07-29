package com.mytodo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthRequest {
    @JsonProperty("email")
    private String email;
    
    @JsonProperty("password")
    private String password;
    
    @JsonProperty("username")
    private String username;
    
    @JsonProperty("phone")
    private String phone;

    // Default constructor
    public AuthRequest() {}

    // Constructor for login
    public AuthRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }

    // Constructor for register
    public AuthRequest(String email, String password, String username, String phone) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phone = phone;
    }

    // Getters and Setters
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // Builder pattern for easy object creation
    public static class Builder {
        private String email;
        private String password;
        private String username;
        private String phone;

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Builder password(String password) {
            this.password = password;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder phone(String phone) {
            this.phone = phone;
            return this;
        }

        public AuthRequest build() {
            return new AuthRequest(email, password, username, phone);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
} 