package org.example.setting;

import java.io.IOException;
import java.util.List;

import org.example.mapper.DataMapper;

import lombok.Getter;

@Deprecated
@Getter
public class StaticSiteSettingCollection implements SettingCollection<StaticSiteSettingCollection> {
	private List<StaticSiteSetting> sites;

	@Override
	public StaticSiteSettingCollection init(DataMapper mapper) throws IOException {
		StaticSiteSettingCollection loaded = mapper.readFromJson(StaticSiteSettingCollection.class,
			"site_config.json");
		this.sites = loaded.getSites();
		return this;
	}
}
