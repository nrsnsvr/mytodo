package com.mytodo.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class TodoResponse {
    @JsonProperty("success")
    private boolean success;
    
    @JsonProperty("message")
    private Object message;
    
    @JsonProperty("tasks")
    private List<TodoItem> tasks;
    
    @JsonProperty("counts")
    private TodoCounts counts;

    // Default constructor
    public TodoResponse() {}

    // Getters and Setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getMessage() {
        return message;
    }

    public void setMessage(Object message) {
        this.message = message;
    }

    public List<TodoItem> getTasks() {
        return tasks;
    }

    public void setTasks(List<TodoItem> tasks) {
        this.tasks = tasks;
    }

    public TodoCounts getCounts() {
        return counts;
    }

    public void setCounts(TodoCounts counts) {
        this.counts = counts;
    }

    // Inner classes for nested objects
    public static class TodoItem {
        @JsonProperty("_id")
        private String id;
        
        @JsonProperty("task")
        private String task;
        
        @JsonProperty("category")
        private String category;
        
        @JsonProperty("isCompleted")
        private boolean isCompleted;
        
        @JsonProperty("datetime")
        private String datetime;
        
        @JsonProperty("user")
        private String user;
        
        @JsonProperty("createdAt")
        private String createdAt;
        
        @JsonProperty("updatedAt")
        private String updatedAt;
        
        @JsonProperty("__v")
        private int version;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public boolean isCompleted() {
            return isCompleted;
        }

        public void setCompleted(boolean completed) {
            isCompleted = completed;
        }

        public String getDatetime() {
            return datetime;
        }

        public void setDatetime(String datetime) {
            this.datetime = datetime;
        }

        public String getUser() {
            return user;
        }

        public void setUser(String user) {
            this.user = user;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }

        public String getUpdatedAt() {
            return updatedAt;
        }

        public void setUpdatedAt(String updatedAt) {
            this.updatedAt = updatedAt;
        }

        public int getVersion() {
            return version;
        }

        public void setVersion(int version) {
            this.version = version;
        }
    }

    public static class TodoCounts {
        @JsonProperty("today")
        private int today;
        
        @JsonProperty("overdue")
        private int overdue;
        
        @JsonProperty("upcoming")
        private int upcoming;
        
        @JsonProperty("completed")
        private int completed;

        public int getToday() {
            return today;
        }

        public void setToday(int today) {
            this.today = today;
        }

        public int getOverdue() {
            return overdue;
        }

        public void setOverdue(int overdue) {
            this.overdue = overdue;
        }

        public int getUpcoming() {
            return upcoming;
        }

        public void setUpcoming(int upcoming) {
            this.upcoming = upcoming;
        }

        public int getCompleted() {
            return completed;
        }

        public void setCompleted(int completed) {
            this.completed = completed;
        }
    }
} 