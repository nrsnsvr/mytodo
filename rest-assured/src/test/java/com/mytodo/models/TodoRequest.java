package com.mytodo.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TodoRequest {
    @JsonProperty("task")
    private String task;
    
    @JsonProperty("category")
    private String category;
    
    @JsonProperty("datetime")
    private String datetime;
    
    @JsonProperty("isCompleted")
    private Boolean isCompleted;

    // Default constructor
    public TodoRequest() {}

    // Constructor for creating todo
    public TodoRequest(String task, String category, String datetime) {
        this.task = task;
        this.category = category;
        this.datetime = datetime;
    }

    // Constructor for updating todo
    public TodoRequest(String task, String category, String datetime, Boolean isCompleted) {
        this.task = task;
        this.category = category;
        this.datetime = datetime;
        this.isCompleted = isCompleted;
    }

    // Getters and Setters
    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Boolean getIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(Boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    // Builder pattern for easy object creation
    public static class Builder {
        private String task;
        private String category;
        private String datetime;
        private Boolean isCompleted;

        public Builder task(String task) {
            this.task = task;
            return this;
        }

        public Builder category(String category) {
            this.category = category;
            return this;
        }

        public Builder datetime(String datetime) {
            this.datetime = datetime;
            return this;
        }

        public Builder isCompleted(Boolean isCompleted) {
            this.isCompleted = isCompleted;
            return this;
        }

        public TodoRequest build() {
            return new TodoRequest(task, category, datetime, isCompleted);
        }
    }

    public static Builder builder() {
        return new Builder();
    }
} 