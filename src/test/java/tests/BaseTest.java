package tests;

import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.DriverManager;
import utils.WebDriverFactory;
import utils.ExtentTestManager;
import utils.Logger;

/**
 * Base test class with ExtentReports integration
 * Provides setup, teardown, and reporting functionality for all tests
 */
public class BaseTest {
	
	@Parameters({"browser", "headless"})
	@BeforeMethod(alwaysRun = true)
	public void setup(@Optional String browser, @Optional String headless) {
		// Set system properties if parameters are provided
		if (browser != null) {
			System.setProperty("browser", browser);
		}
		if (headless != null) {
			System.setProperty("headless", headless);
		}
		
		// Create and set driver for this thread
		WebDriver driver = WebDriverFactory.createDriver();
		DriverManager.setDriver(driver);
		
		Logger.info("Test setup completed - Browser: " + (browser != null ? browser : "default") + 
			", Headless: " + (headless != null ? headless : "false"));
	}
	
	@AfterMethod(alwaysRun = true)
	public void teardown(ITestResult result) {
		try {
			// Handle test result in ExtentReports
			ExtentTestManager.handleTestResult(result);
			
			// Log test completion
			String testName = result.getMethod().getMethodName();
			String className = result.getTestClass().getName();
			Logger.info("Test completed: " + className + "." + testName + " - Status: " + result.getStatus());
			
		} catch (Exception e) {
			Logger.error("Error in test teardown: " + e.getMessage());
		} finally {
			// Clean up driver for this thread
			DriverManager.quitDriver();
			
			// Clean up ExtentTest thread local
			ExtentTestManager.cleanup();
		}
	}
	
	@AfterSuite(alwaysRun = true)
	public void suiteTeardown() {
		// Flush ExtentReports to generate final report
		utils.ExtentManager.flushExtentReports();
		Logger.info("Test suite completed - ExtentReports flushed");
	}

}
