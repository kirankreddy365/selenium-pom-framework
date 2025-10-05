package utils;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

public final class WebDriverFactory {
    private WebDriverFactory() { }

    public static WebDriver createDriver() {
        String browser = ConfigManager.get("browser", "chrome").toLowerCase();
        boolean headless = Boolean.parseBoolean(ConfigManager.get("headless", "false"));

        switch (browser) {
            case "edge":
                return createEdge(headless);
            case "firefox":
                return createFirefox(headless);
            case "chrome":
            default:
                return createChrome(headless);
        }
    }

    private static WebDriver createChrome(boolean headless) {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        options.addArguments("--start-maximized");
        if (headless) {
            options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
        }
        return new ChromeDriver(options);
    }

    private static WebDriver createEdge(boolean headless) {
        EdgeOptions options = new EdgeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        if (headless) {
            options.addArguments("--headless=new", "--disable-gpu", "--window-size=1920,1080");
        }
        return new EdgeDriver(options);
    }

    private static WebDriver createFirefox(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
        if (headless) {
            options.addArguments("--headless", "--width=1920", "--height=1080");
        }
        return new FirefoxDriver(options);
    }
}
