package com.mytodo.enums;

/**
 * Todo kategorileri için enum
 */
public enum TodoCategory {
    
    PERSONAL("personal"),
    WORK("work"),
    URGENT("urgent"),
    HOME("home"),
    SHOPPING("shopping"),
    HEALTH("health"),
    EDUCATION("education"),
    TRAVEL("travel");
    
    private final String value;
    
    TodoCategory(String value) {
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
    public static TodoCategory fromString(String text) {
        for (TodoCategory category : TodoCategory.values()) {
            if (category.value.equalsIgnoreCase(text)) {
                return category;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
} 