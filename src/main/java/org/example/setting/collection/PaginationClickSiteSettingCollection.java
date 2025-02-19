package org.example.setting.collection;

import lombok.Getter;
import org.example.mapper.DataMapper;
import org.example.setting.SettingCollection;
import org.example.setting.SiteSetting;

import java.io.IOException;
import java.util.List;

@Getter
public class PaginationClickSiteSettingCollection implements SettingCollection<PaginationClickSiteSettingCollection> {
	private List<SiteSetting> sites;

	@Override
	public PaginationClickSiteSettingCollection init(DataMapper mapper) throws IOException {
		PaginationClickSiteSettingCollection loaded = mapper.readFromJson(PaginationClickSiteSettingCollection.class,
			"page_click_site_config.json");
		this.sites = loaded.getSites();
		return this;
	}
}
