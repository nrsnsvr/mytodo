package com.mytodo.enums;

/**
 * Todo durumları için enum
 */
public enum TodoStatus {
    
    PENDING(false),
    COMPLETED(true);
    
    private final boolean value;
    
    TodoStatus(boolean value) {
        this.value = value;
    }
    
    public boolean getValue() {
        return value;
    }
    
    public boolean isCompleted() {
        return value;
    }
    
    public boolean isPending() {
        return !value;
    }
    
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    /**
     * Boolean değerinden enum'a dönüştürme
     */
    public static TodoStatus fromBoolean(boolean value) {
        return value ? COMPLETED : PENDING;
    }
} 