package org.example.setting;

import lombok.Getter;
import org.example.mapper.DataMapper;

import java.io.IOException;
import java.util.List;

@Getter
public class DynamicSiteSettingCollectionVer2 implements SettingCollection<DynamicSiteSettingCollectionVer2> {
	private List<DynamicSiteSetting> sites;

	@Override
	public DynamicSiteSettingCollectionVer2 init(DataMapper mapper) throws IOException {
		DynamicSiteSettingCollectionVer2 loaded = mapper.readFromJson(DynamicSiteSettingCollectionVer2.class,
			"dynamic_site_config_test.json");
		this.sites = loaded.getSites();
		return this;
	}
}
