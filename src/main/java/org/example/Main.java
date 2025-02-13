package org.example;

import java.io.IOException;

import org.example.driver.WebDriverFactory;
import org.example.scraper.DynamicJobScraper;
import org.example.setting.DynamicSiteSettingCollection;
import org.openqa.selenium.WebDriver;

public class Main {
	public static void main(String[] args) throws IOException {

		DynamicSiteSettingCollection setting = new DynamicSiteSettingCollection();
		WebDriver webDriver = WebDriverFactory.createChromeDriver();

		DynamicJobScraper scraper = new DynamicJobScraper(setting, webDriver);

		try {
			scraper.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webDriver.quit();
		}
	}
}