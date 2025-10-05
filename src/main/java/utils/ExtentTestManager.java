package utils;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

/**
 * ExtentTestManager for thread-safe test reporting
 * Manages individual test instances and logging
 */
public final class ExtentTestManager {
    
    private static final ThreadLocal<ExtentTest> testThreadLocal = new ThreadLocal<>();
    private static final ThreadLocal<String> testNameThreadLocal = new ThreadLocal<>();
    
    private ExtentTestManager() { }
    
    /**
     * Create a new test in ExtentReports
     */
    public static synchronized ExtentTest createTest(String testName, String description) {
        ExtentTest test = ExtentManager.getExtentReports().createTest(testName, description);
        testThreadLocal.set(test);
        testNameThreadLocal.set(testName);
        
        Logger.info("Created ExtentTest: " + testName);
        return test;
    }
    
    /**
     * Create a new test in ExtentReports with category
     */
    public static synchronized ExtentTest createTest(String testName, String description, String category) {
        ExtentTest test = ExtentManager.getExtentReports().createTest(testName, description);
        test.assignCategory(category);
        testThreadLocal.set(test);
        testNameThreadLocal.set(testName);
        
        Logger.info("Created ExtentTest: " + testName + " with category: " + category);
        return test;
    }
    
    /**
     * Get current test instance
     */
    public static ExtentTest getTest() {
        return testThreadLocal.get();
    }
    
    /**
     * Log info message to current test
     */
    public static void logInfo(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.log(Status.INFO, message);
            Logger.info(message);
        }
    }
    
    /**
     * Log pass message to current test
     */
    public static void logPass(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.log(Status.PASS, MarkupHelper.createLabel(message, ExtentColor.GREEN));
            Logger.info("PASS: " + message);
        }
    }
    
    /**
     * Log fail message to current test
     */
    public static void logFail(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.log(Status.FAIL, MarkupHelper.createLabel(message, ExtentColor.RED));
            Logger.error("FAIL: " + message);
        }
    }
    
    /**
     * Log skip message to current test
     */
    public static void logSkip(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.log(Status.SKIP, MarkupHelper.createLabel(message, ExtentColor.ORANGE));
            Logger.warn("SKIP: " + message);
        }
    }
    
    /**
     * Log warning message to current test
     */
    public static void logWarning(String message) {
        ExtentTest test = getTest();
        if (test != null) {
            test.log(Status.WARNING, MarkupHelper.createLabel(message, ExtentColor.YELLOW));
            Logger.warn(message);
        }
    }
    
    /**
     * Attach screenshot to current test
     */
    public static void attachScreenshot(String screenshotPath, String title) {
        ExtentTest test = getTest();
        if (test != null) {
            test.addScreenCaptureFromPath(screenshotPath, title);
            Logger.info("Screenshot attached: " + title);
        }
    }
    
    /**
     * Take screenshot and attach to report
     */
    public static String takeScreenshot(WebDriver driver, String testName) {
        try {
            String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
            String fileName = testName + "_" + timestamp + ".png";
            String screenshotPath = ExtentManager.getScreenshotDir() + fileName;
            
            // Take screenshot using Selenium
            org.openqa.selenium.TakesScreenshot takesScreenshot = (org.openqa.selenium.TakesScreenshot) driver;
            File screenshotFile = takesScreenshot.getScreenshotAs(org.openqa.selenium.OutputType.FILE);
            
            // Copy file to target location
            org.apache.commons.io.FileUtils.copyFile(screenshotFile, new File(screenshotPath));
            
            // Attach to report
            attachScreenshot(screenshotPath, "Screenshot at " + timestamp);
            
            return screenshotPath;
        } catch (Exception e) {
            Logger.error("Failed to take screenshot: " + e.getMessage());
            return null;
        }
    }
    
    /**
     * Handle test result and update ExtentReports
     */
    public static void handleTestResult(ITestResult result) {
        ExtentTest test = getTest();
        if (test != null) {
            switch (result.getStatus()) {
                case ITestResult.SUCCESS:
                    test.log(Status.PASS, "Test passed successfully");
                    break;
                case ITestResult.FAILURE:
                    test.log(Status.FAIL, "Test failed: " + result.getThrowable().getMessage());
                    // Take screenshot on failure
                    if (DriverManager.getDriver() != null) {
                        takeScreenshot(DriverManager.getDriver(), testNameThreadLocal.get());
                    }
                    break;
                case ITestResult.SKIP:
                    test.log(Status.SKIP, "Test was skipped: " + result.getSkipCausedBy());
                    break;
            }
        }
    }
    
    /**
     * Clean up thread local variables
     */
    public static void cleanup() {
        testThreadLocal.remove();
        testNameThreadLocal.remove();
    }
}
