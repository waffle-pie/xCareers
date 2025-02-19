package org.example.setting.collection;

import java.io.IOException;
import java.util.List;

import org.example.mapper.DataMapper;
import org.example.setting.PaginationSiteSetting;
import org.example.setting.SettingCollection;

import lombok.Getter;

@Getter
public class PaginationSiteSettingCollection implements SettingCollection<PaginationSiteSettingCollection> {
	private List<PaginationSiteSetting> sites;

	@Override
	public PaginationSiteSettingCollection init(DataMapper mapper) throws IOException {
		PaginationSiteSettingCollection loaded = mapper.readFromJson(PaginationSiteSettingCollection.class,
			"pagination_site_config.json");
		this.sites = loaded.getSites();
		return this;
	}
}
