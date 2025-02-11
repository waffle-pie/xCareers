package org.example.setting;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

import org.example.scraper.StaticJobScraper;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;

@Getter
public class StaticSiteSettingCollection implements SettingCollection<StaticSiteSettingCollection> {
	private List<StaticSiteSetting> sites;

	@Override
	public StaticSiteSettingCollection init(ObjectMapper objectMapper) throws IOException {
		ClassLoader classLoader = StaticJobScraper.class.getClassLoader();
		File file = new File(Objects.requireNonNull(classLoader.getResource("site_config.json")).getFile());
		StaticSiteSettingCollection loaded = objectMapper.readValue(file,
			StaticSiteSettingCollection.class);
		this.sites = loaded.sites;
		return this;
	}

}
