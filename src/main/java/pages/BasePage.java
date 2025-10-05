package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import utils.DriverManager;
import utils.ElementUtil;
import utils.TimeoutConfig;

/**
 * Base page class providing common functionality for all page objects
 * Follows Page Object Model best practices
 */
public abstract class BasePage {
    
    protected final ElementUtil elementUtil;
    protected final WebDriver driver;
    
    protected BasePage() {
        this.driver = DriverManager.getDriver();
        this.elementUtil = new ElementUtil();
    }
    
    /**
     * Wait for page to load completely
     */
    protected void waitForPageLoad() {
        elementUtil.waitForPageLoad();
    }
    
    /**
     * Wait for element to be visible
     */
    protected void waitForElementVisible(By locator) {
        elementUtil.waitForElementVisible(locator, TimeoutConfig.DEFAULT_TIMEOUT);
    }
    
    /**
     * Wait for element to be clickable
     */
    protected void waitForElementClickable(By locator) {
        elementUtil.waitForElementClickable(locator, TimeoutConfig.DEFAULT_TIMEOUT);
    }
    
    /**
     * Check if element is present on page
     */
    protected boolean isElementPresent(By locator) {
        return elementUtil.isElementPresent(locator, TimeoutConfig.SHORT_TIMEOUT);
    }
    
    /**
     * Get page title
     */
    public String getPageTitle() {
        return driver.getTitle();
    }
    
    /**
     * Get current URL
     */
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
    
    /**
     * Navigate to URL
     */
    protected void navigateTo(String url) {
        elementUtil.navigate(url);
        waitForPageLoad();
    }
    
    /**
     * Handle cookie consent if present
     */
    protected void handleCookieConsent() {
        elementUtil.handleCookieConsent();
    }
}