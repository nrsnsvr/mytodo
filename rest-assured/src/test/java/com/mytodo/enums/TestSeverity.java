package com.mytodo.enums;

/**
 * Test severity seviyeleri için enum
 */
public enum TestSeverity {
    
    CRITICAL("Critical"),
    HIGH("High"),
    MEDIUM("Medium"),
    LOW("Low"),
    MINOR("Minor");
    
    private final String value;
    
    TestSeverity(String value) {
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
    public static TestSeverity fromString(String text) {
        for (TestSeverity severity : TestSeverity.values()) {
            if (severity.value.equalsIgnoreCase(text)) {
                return severity;
            }
        }
        throw new IllegalArgumentException("No constant with text " + text + " found");
    }
    
    /**
     * Check if severity is critical
     */
    public boolean isCritical() {
        return this == CRITICAL;
    }
    
    /**
     * Check if severity is high
     */
    public boolean isHigh() {
        return this == HIGH;
    }
    
    /**
     * Check if severity is medium
     */
    public boolean isMedium() {
        return this == MEDIUM;
    }
    
    /**
     * Check if severity is low
     */
    public boolean isLow() {
        return this == LOW;
    }
} 