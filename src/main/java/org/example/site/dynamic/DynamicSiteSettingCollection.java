package org.example.site.dynamic;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.example.scraper.StaticJobScraper;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class DynamicSiteSettingCollection {
	private List<DynamicSiteSetting> sites;

	public static DynamicSiteSettingCollection init(ObjectMapper objectMapper) throws IOException {
		ClassLoader classLoader = StaticJobScraper.class.getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("dynamic_site_config.json")).getFile());
		return (DynamicSiteSettingCollection) objectMapper.readValue(file, DynamicSiteSettingCollection.class);
	}

}
