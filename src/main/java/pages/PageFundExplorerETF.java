package pages;

import org.openqa.selenium.By;

/**
 * Page Object for VanEck Fund Explorer ETF page
 * Follows Page Object Model best practices
 */
public class PageFundExplorerETF extends BasePage {
	
	// Page locators
	private final By invTypeDropdown = By.id("investment-type");
	private final By invTypeDropdownValues = By.xpath("//div[@id='intestmentType']/div//button");
	private final By header = By.xpath("//h1[normalize-space(text()='Explore Our ETFs and Mutual Funds')]");
	
	// Page URLs
	private static final String FUND_EXPLORER_URL = "https://www.vaneck.com/us/en/etf-mutual-fund-finder/etfs/";
	private static final String HOME_URL = "https://www.vaneck.com/us/en/?country=us&audience=fa&disclaimer=true";
	
	/**
	 * Navigate to Fund Explorer page
	 * @return this page object for method chaining
	 */
	public PageFundExplorerETF navigateToFundExplorer() {
		navigateTo(FUND_EXPLORER_URL);
		handleCookieConsent();
		return this;
	}
	
	/**
	 * Navigate to home page
	 * @return this page object for method chaining
	 */
	public PageFundExplorerETF navigateToHome() {
		navigateTo(HOME_URL);
		handleCookieConsent();
		return this;
	}
	
	/**
	 * Select investment type from dropdown
	 * @param investmentType the investment type to select
	 * @return this page object for method chaining
	 */
	public PageFundExplorerETF selectInvestmentType(String investmentType) {
		elementUtil.click(invTypeDropdown);
		elementUtil.selectDropdownValue(invTypeDropdownValues, investmentType);
		return this;
	}
	
	/**
	 * Get selected value from investment type dropdown
	 * @return the selected dropdown value
	 */
	public String getInvTypeDropdownSelectedValue() {
		return elementUtil.getText(invTypeDropdown);
	}
	
	/**
	 * Check if page header is visible
	 * @return true if header is visible
	 */
	public boolean isHeaderVisible() {
		return isElementPresent(header);
	}
	
	/**
	 * Get page header text
	 * @return header text
	 */
	public String getHeaderText() {
		return elementUtil.getText(header);
	}
	
	/**
	 * Wait for page to load completely and return this page object
	 * @return this page object for method chaining
	 */
	public PageFundExplorerETF waitForPageToLoad() {
		super.waitForPageLoad();
		return this;
	}
}
