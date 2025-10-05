package utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Simple logging utility for the test framework
 * Provides consistent logging format across all components
 */
public final class Logger {
    
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSS");
    
    private Logger() { }
    
    /**
     * Log info message
     */
    public static void info(String message) {
        log("INFO", message);
    }
    
    /**
     * Log warning message
     */
    public static void warn(String message) {
        log("WARN", message);
    }
    
    /**
     * Log error message
     */
    public static void error(String message) {
        log("ERROR", message);
    }
    
    /**
     * Log error message with exception
     */
    public static void error(String message, Throwable throwable) {
        log("ERROR", message + " - " + throwable.getMessage());
    }
    
    /**
     * Log debug message
     */
    public static void debug(String message) {
        log("DEBUG", message);
    }
    
    /**
     * Internal logging method
     */
    private static void log(String level, String message) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        String threadName = Thread.currentThread().getName();
        System.out.println(String.format("[%s] [%s] [%s] %s", timestamp, level, threadName, message));
    }
}
