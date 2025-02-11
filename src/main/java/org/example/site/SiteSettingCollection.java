package org.example.site;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.example.scraper.StaticJobScraper;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class SiteSettingCollection {
	private List<SiteSetting> sites;

	public static SiteSettingCollection init(ObjectMapper objectMapper) throws IOException {
		ClassLoader classLoader = StaticJobScraper.class.getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("site_config.json")).getFile());
		return (SiteSettingCollection) objectMapper.readValue(file, SiteSettingCollection.class);
	}

}
