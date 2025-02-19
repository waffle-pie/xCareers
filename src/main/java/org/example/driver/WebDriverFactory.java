package org.example.driver;

import java.net.URL;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

	private static final String GRID_URL = "http://localhost:4444/wd/hub";

	public static WebDriver createChromeDriver() {
		try {
			ChromeOptions options = new ChromeOptions();
			options.setCapability("browserName", "chrome");
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("--disable-dev-shm-usage");
			return new RemoteWebDriver(new URL(GRID_URL), options);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create Chrome WebDriver", e);
		}
	}
	public static WebDriver localChromeDriver() {
		try {
			ChromeOptions options = new ChromeOptions();
			options.setCapability("browserName", "chrome");
			options.setPageLoadStrategy(PageLoadStrategy.EAGER);
			options.addArguments("--disable-dev-shm-usage");
			return new ChromeDriver(options);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create Chrome WebDriver", e);
		}
	}
}
