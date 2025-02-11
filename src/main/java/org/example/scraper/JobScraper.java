package org.example.scraper;

import java.io.IOException;

import org.example.setting.SettingCollection;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class JobScraper<T extends SettingCollection<T>> {
	private static final ObjectMapper objectMapper = new ObjectMapper();
	private final T setting;

	public JobScraper(T setting) {
		this.setting = setting;
	}

	public void run() throws IOException {
		T init = setting.init(objectMapper);
		setUp(init, objectMapper);
	}

	public abstract void setUp(T setting, ObjectMapper objectMapper) throws IOException;
}
