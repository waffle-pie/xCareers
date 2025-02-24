package org.example.setting.collection;

import java.io.IOException;
import java.util.List;

import org.example.mapper.DataMapper;
import org.example.setting.SettingCollection;
import org.example.setting.SiteSetting;

import lombok.Getter;

@Getter
public class ScrollSiteSettingCollection implements SettingCollection<ScrollSiteSettingCollection> {
	private List<SiteSetting> sites;

	@Override
	public ScrollSiteSettingCollection init(DataMapper mapper) throws IOException {
		ScrollSiteSettingCollection loaded = mapper.readFromJson(ScrollSiteSettingCollection.class,
			"dynamic_site_config.json");
		this.sites = loaded.getSites();
		return this;
	}
}
