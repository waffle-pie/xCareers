package org.example.driver;

import java.net.URL;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

public class WebDriverFactory {

	private static final String GRID_URL = "http://localhost:4444/wd/hub";

	public static WebDriver createChromeDriver() {
		try {
			ChromeOptions options = new ChromeOptions();
			options.setCapability("browserName", "chrome");
			return new RemoteWebDriver(new URL(GRID_URL), options);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create Chrome WebDriver", e);
		}
	}

	public static WebDriver createFirefoxDriver() {
		try {
			FirefoxOptions options = new FirefoxOptions();
			options.setCapability("browserName", "firefox");
			return new RemoteWebDriver(new URL(GRID_URL), options);
		} catch (Exception e) {
			throw new RuntimeException("Failed to create Firefox WebDriver", e);
		}
	}
}
