package org.example;

import java.io.IOException;

import org.example.scraper.DynamicJobScraper;
import org.example.scraper.StaticJobScraper;
import org.example.setting.StaticSiteSettingCollection;
import org.example.setting.DynamicSiteSettingCollection;

public class Main {
	public static void main(String[] args) throws IOException {

		DynamicSiteSettingCollection setting = new DynamicSiteSettingCollection();
		DynamicJobScraper scraper = new DynamicJobScraper(setting);

		StaticSiteSettingCollection staticSetting = new StaticSiteSettingCollection();
		StaticJobScraper scraper2 = new StaticJobScraper(staticSetting);

		scraper.run();
		// scraper2.run();
	}
}