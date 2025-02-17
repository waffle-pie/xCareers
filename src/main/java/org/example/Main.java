package org.example;

import java.io.IOException;

import org.example.driver.WebDriverFactory;
import org.example.mapper.DataMapper;
import org.example.scraper.DynamicJobScraper;
import org.example.scraper.PaginationJobScraper;
import org.example.setting.collection.DynamicSiteSettingCollection;
import org.example.setting.collection.PaginationSiteSettingCollection;
import org.openqa.selenium.WebDriver;

public class Main {
	public static void main(String[] args) throws IOException {
		DataMapper mapper = new DataMapper();
		WebDriver webDriver = WebDriverFactory.createChromeDriver();

		//
		DynamicSiteSettingCollection setting = new DynamicSiteSettingCollection();
		DynamicJobScraper scraper = new DynamicJobScraper(setting, webDriver);

		// Page
		PaginationSiteSettingCollection paginationSetting = new PaginationSiteSettingCollection();
		PaginationJobScraper paginationScraper = new PaginationJobScraper(paginationSetting, webDriver);

		try {
			scraper.run(mapper);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			webDriver.quit();
		}
	}
}