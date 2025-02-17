package org.example;

import java.io.IOException;

import org.example.driver.WebDriverFactory;
import org.example.scraper.DynamicJobScraper;
import org.example.scraper.PaginationJobScraper;
import org.example.setting.DynamicSiteSettingCollection;
import org.example.setting.PaginationSiteSettingCollection;
import org.openqa.selenium.WebDriver;

public class Main {
	public static void main(String[] args) throws IOException {

		WebDriver webDriver = WebDriverFactory.createChromeDriver();

		//
		DynamicSiteSettingCollection setting = new DynamicSiteSettingCollection();
		DynamicJobScraper scraper = new DynamicJobScraper(setting, webDriver);

		// Page
		PaginationSiteSettingCollection paginationSetting = new PaginationSiteSettingCollection();
		PaginationJobScraper paginationScraper = new PaginationJobScraper(paginationSetting, webDriver);

		try {
			paginationScraper.run();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webDriver.quit();
		}
	}
}