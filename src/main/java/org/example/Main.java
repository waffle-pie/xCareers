package org.example;

import java.io.IOException;

import org.example.scraper.DynamicJobScraper;
import org.example.scraper.StaticJobScraper;
import org.example.site.dynamic.DynamicSiteSettingCollection;
import org.example.site.SiteSettingCollection;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
	public static void main(String[] args) throws IOException {

		ObjectMapper objectMapper = new ObjectMapper();
		SiteSettingCollection setting = SiteSettingCollection.init(objectMapper);
		DynamicSiteSettingCollection dynamicSetting = DynamicSiteSettingCollection.init(objectMapper);

		StaticJobScraper scraper1 = new StaticJobScraper(setting.getSites(),objectMapper);

		DynamicJobScraper scraper2 = new DynamicJobScraper(dynamicSetting.getSites(),objectMapper);

		scraper2.run();
	}
}