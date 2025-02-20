package org.example.setting.collection;

import lombok.Getter;
import org.example.mapper.DataMapper;
import org.example.setting.PaginationSiteSetting;
import org.example.setting.SettingCollection;

import java.io.IOException;
import java.util.List;

@Getter
public class PaginationClickSiteSettingCollection implements SettingCollection<PaginationClickSiteSettingCollection> {
	private List<PaginationSiteSetting> sites;

	@Override
	public PaginationClickSiteSettingCollection init(DataMapper mapper) throws IOException {
		PaginationClickSiteSettingCollection loaded = mapper.readFromJson(PaginationClickSiteSettingCollection.class,
			"pagination_click_site_config.json");
		this.sites = loaded.getSites();
		return this;
	}
}
