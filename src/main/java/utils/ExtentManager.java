package utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

/**
 * ExtentManager for configuring and managing ExtentReports
 * Provides thread-safe report initialization and configuration
 */
public final class ExtentManager {
    
    private static ExtentReports extent;
    private static final String REPORT_DIR = "test-output/reports/";
    private static final String SCREENSHOT_DIR = "test-output/screenshots/";
    
    private ExtentManager() { }
    
    /**
     * Get ExtentReports instance (singleton pattern)
     */
    public static synchronized ExtentReports getExtentReports() {
        if (extent == null) {
            extent = createExtentReports();
        }
        return extent;
    }
    
    /**
     * Create and configure ExtentReports
     */
    private static ExtentReports createExtentReports() {
        // Create reports directory if it doesn't exist
        createDirectory(REPORT_DIR);
        createDirectory(SCREENSHOT_DIR);
        
        // Generate timestamp for unique report names
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm-ss"));
        String reportName = "ExtentReport_" + timestamp + ".html";
        String reportPath = REPORT_DIR + reportName;
        
        // Create ExtentSparkReporter
        ExtentSparkReporter sparkReporter = new ExtentSparkReporter(reportPath);
        
        // Configure report appearance
        sparkReporter.config().setDocumentTitle("Selenium Test Report");
        sparkReporter.config().setReportName("VanEck Fund Explorer Test Suite");
        sparkReporter.config().setTheme(Theme.STANDARD);
        sparkReporter.config().setTimeStampFormat("yyyy-MM-dd HH:mm:ss");
        
        // Additional configurations
//        sparkReporter.config().setCSS("body { font-family: Arial, sans-serif; }");
//        sparkReporter.config().setJS("document.title = 'VanEck Test Report';");
        
        // Create ExtentReports instance
        ExtentReports extent = new ExtentReports();
        extent.attachReporter(sparkReporter);
        
        // Set system information
        extent.setSystemInfo("Environment", ConfigManager.get("environment", "Test"));
        extent.setSystemInfo("Browser", ConfigManager.get("browser", "chrome"));
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
        extent.setSystemInfo("User", System.getProperty("user.name"));
        extent.setSystemInfo("Application", "VanEck Fund Explorer");
        
        Logger.info("ExtentReports initialized with report path: " + reportPath);
        
        return extent;
    }
    
    /**
     * Create directory if it doesn't exist
     */
    private static void createDirectory(String path) {
        File directory = new File(path);
        if (!directory.exists()) {
            directory.mkdirs();
            Logger.info("Created directory: " + path);
        }
    }
    
    /**
     * Get screenshot directory path
     */
    public static String getScreenshotDir() {
        return SCREENSHOT_DIR;
    }
    
    /**
     * Get report directory path
     */
    public static String getReportDir() {
        return REPORT_DIR;
    }
    
    /**
     * Flush and close ExtentReports
     */
    public static synchronized void flushExtentReports() {
        if (extent != null) {
            extent.flush();
            Logger.info("ExtentReports flushed and closed");
        }
    }
}
