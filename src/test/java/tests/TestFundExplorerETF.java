package tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.PageFundExplorerETF;
import utils.ExtentTestManager;
import utils.Logger;

/**
 * Test class for VanEck Fund Explorer ETF functionality
 * Demonstrates proper Page Object Model usage with ExtentReports integration
 */
public class TestFundExplorerETF extends BaseTest {
	
	@Test
	public void verifyInvTypeFilter() {
		// Create ExtentTest for reporting
		ExtentTestManager.createTest("Verify Investment Type Filter", 
			"Test to verify that investment type dropdown filter works correctly", "Smoke Test");
		
		ExtentTestManager.logInfo("Starting verifyInvTypeFilter test");
		
		try {
			// Create page object and navigate
			PageFundExplorerETF page = new PageFundExplorerETF();
			page.navigateToFundExplorer();
			ExtentTestManager.logPass("Successfully navigated to Fund Explorer page");
			
			// Verify page loaded
			Assert.assertTrue(page.isHeaderVisible(), "Page header should be visible");
			ExtentTestManager.logPass("Page header is visible");
			
			// Select investment type
			page.selectInvestmentType("etfs");
			ExtentTestManager.logPass("Selected investment type: etfs");
			
			// Verify selection
			String selectedText = page.getInvTypeDropdownSelectedValue();
			Assert.assertTrue(selectedText.equalsIgnoreCase("etfs"), 
				"Investment type should be selected as ETFs, but was: " + selectedText);
			ExtentTestManager.logPass("Investment type filter working correctly - Selected: " + selectedText);
			
			ExtentTestManager.logPass("Test completed successfully - Investment type filter working correctly");
			
		} catch (Exception e) {
			ExtentTestManager.logFail("Test failed: " + e.getMessage());
			// Take screenshot on failure
			ExtentTestManager.takeScreenshot(utils.DriverManager.getDriver(), "verifyInvTypeFilter_failure");
			throw e;
		}
	}
	
	@Test
	public void verifyPageNavigation() {
		// Create ExtentTest for reporting
		ExtentTestManager.createTest("Verify Page Navigation", 
			"Test to verify navigation between home page and fund explorer page", "Navigation Test");
		
		ExtentTestManager.logInfo("Starting verifyPageNavigation test");
		
		try {
			PageFundExplorerETF page = new PageFundExplorerETF();
			
			// Test home page navigation
			page.navigateToHome();
			String homePageTitle = page.getPageTitle();
			Assert.assertNotNull(homePageTitle, "Page title should not be null");
			ExtentTestManager.logPass("Home page navigation successful - Title: " + homePageTitle);
			
			// Test fund explorer navigation
			page.navigateToFundExplorer();
			Assert.assertTrue(page.isHeaderVisible(), "Fund Explorer page should be visible");
			ExtentTestManager.logPass("Fund Explorer page navigation successful");
			
			ExtentTestManager.logPass("Page navigation test completed successfully");
			
		} catch (Exception e) {
			ExtentTestManager.logFail("Navigation test failed: " + e.getMessage());
			// Take screenshot on failure
			ExtentTestManager.takeScreenshot(utils.DriverManager.getDriver(), "verifyPageNavigation_failure");
			throw e;
		}
	}
}
