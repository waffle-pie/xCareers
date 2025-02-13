package org.example.setting;

import java.io.IOException;
import java.util.List;

import org.example.mapper.DataMapper;

import lombok.Getter;

@Getter
public class DynamicSiteSettingCollection implements SettingCollection<DynamicSiteSettingCollection> {
	private List<DynamicSiteSetting> sites;

	@Override
	public DynamicSiteSettingCollection init(DataMapper mapper) throws IOException {
		DynamicSiteSettingCollection loaded = mapper.readFromJson(DynamicSiteSettingCollection.class,
			"dynamic_site_config.json");
		this.sites = loaded.getSites();
		return this;
	}
}
