package utils;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.TimeoutException;

/**
 * Utility class for common Selenium operations
 * Uses DriverManager internally for thread-safe driver access
 */
public class ElementUtil {
	
	private WebDriver getDriver() {
		return DriverManager.getDriver();
	}
    
    /**
     * Navigate to URL
     */
    public void navigate(String URL) {
    	getDriver().navigate().to(URL);
    }
    
    /**
     * Wait for page to load completely
     */
    public void waitForPageLoad() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(TimeoutConfig.PAGE_LOAD_TIMEOUT));
        wait.until(ExpectedConditions.jsReturnsValue("return document.readyState == 'complete'"));
    }

    /**
     * Click element with fallback to JavaScript click
     */
    public void click(By locator) {
        try {
            waitForElementClickable(locator, TimeoutConfig.ELEMENT_CLICKABLE_TIMEOUT).click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            WebElement element = waitForElementVisible(locator, TimeoutConfig.ELEMENT_VISIBILITY_TIMEOUT);
            ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", element);
        }
    }

    /**
     * Send keys to element
     */
    public void sendKeys(By locator, String text) {
        WebElement element = waitForElementVisible(locator, TimeoutConfig.ELEMENT_VISIBILITY_TIMEOUT);
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Get text from element
     */
    public String getText(By locator) {
        return waitForElementVisible(locator, TimeoutConfig.ELEMENT_VISIBILITY_TIMEOUT).getText();
    }

    /**
     * Wait for element to be visible
     */
    public WebElement waitForElementVisible(By locator, int timeout) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    /**
     * Wait for element to be clickable
     */
    public WebElement waitForElementClickable(By locator, int timeout) {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(timeout))
                .until(ExpectedConditions.elementToBeClickable(locator));
    }
    
    /**
     * Select dropdown value by text
     */
    public void selectDropdownValue(By elementsLocator, String expValue) {
    	List<WebElement> elements = getDriver().findElements(elementsLocator);
    	
    	for(WebElement ele : elements) {
    		if(ele.getText().equalsIgnoreCase(expValue)) {
    			try {
    				ele.click();
    			} catch (Exception e) {
    				// Fallback to JavaScript click
    				((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].click();", ele);
    			}
    			break;
    		}
    	}
    }
    
    /**
     * Handle cookie consent with explicit waits instead of Thread.sleep
     */
    public void handleCookieConsent() {
        try {
            // Try multiple selectors for cookie consent with explicit waits
            String[] selectors = {
                "//button[contains(text(), 'Accept') or contains(text(), 'Accept All')]",
                "//button[contains(@id, 'accept') or contains(@class, 'accept')]",
                "//button[contains(@data-testid, 'accept')]",
                "//div[@id='usercentrics-cmp-ui']//button[contains(text(), 'Accept')]",
                "//*[@id='usercentrics-cmp-ui']//button[1]"
            };
            
            for (String selector : selectors) {
                By cookieConsent = By.xpath(selector);
                if (isElementPresent(cookieConsent, TimeoutConfig.VERY_SHORT_TIMEOUT)) {
                    click(cookieConsent);
                    // Wait for consent to be processed using explicit wait
                    WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(2));
                    wait.until(ExpectedConditions.invisibilityOfElementLocated(cookieConsent));
                    break;
                }
            }
        } catch (TimeoutException e) {
            // No cookie consent found, continue with test
        } catch (Exception e) {
            // Log error but don't fail the test
            System.err.println("Error handling cookie consent: " + e.getMessage());
        }
    }
    
    /**
     * Check if element is present on page
     */
    public boolean isElementPresent(By locator, int timeout) {
        try {
            waitForElementVisible(locator, timeout);
            return true;
        } catch (TimeoutException e) {
            return false;
        }
    }
    
    /**
     * Scroll element into view
     */
    public void scrollIntoView(By locator) {
        WebElement element = waitForElementVisible(locator, TimeoutConfig.ELEMENT_VISIBILITY_TIMEOUT);
        ((org.openqa.selenium.JavascriptExecutor) getDriver()).executeScript("arguments[0].scrollIntoView(true);", element);
    }
    
    /**
     * Get element attribute value
     */
    public String getAttribute(By locator, String attributeName) {
        WebElement element = waitForElementVisible(locator, TimeoutConfig.ELEMENT_VISIBILITY_TIMEOUT);
        return element.getAttribute(attributeName);
    }
    
    /**
     * Check if element is displayed
     */
    public boolean isDisplayed(By locator) {
        try {
            WebElement element = waitForElementVisible(locator, TimeoutConfig.VERY_SHORT_TIMEOUT);
            return element.isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }
}
