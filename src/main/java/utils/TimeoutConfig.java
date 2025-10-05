package utils;

/**
 * Centralized timeout configuration for the test framework
 * Eliminates magic numbers and provides consistent timeout values
 */
public final class TimeoutConfig {
    
    private TimeoutConfig() { }
    
    // Default timeouts in seconds
    public static final int DEFAULT_TIMEOUT = 10;
    public static final int SHORT_TIMEOUT = 5;
    public static final int LONG_TIMEOUT = 30;
    public static final int VERY_SHORT_TIMEOUT = 2;
    
    // Page load timeout
    public static final int PAGE_LOAD_TIMEOUT = 30;
    
    // Implicit wait timeout
    public static final int IMPLICIT_WAIT_TIMEOUT = 5;
    
    // Cookie consent wait timeout
    public static final int COOKIE_CONSENT_TIMEOUT = 5;
    
    // Element visibility timeout
    public static final int ELEMENT_VISIBILITY_TIMEOUT = 10;
    
    // Element clickable timeout
    public static final int ELEMENT_CLICKABLE_TIMEOUT = 10;
}
