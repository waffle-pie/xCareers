package org.example.setting;

import lombok.Getter;
import org.example.mapper.DataMapper;

import java.io.IOException;
import java.util.List;

@Getter
public class ScrollSiteSettingCollectionVer2 implements SettingCollection<ScrollSiteSettingCollectionVer2> {
	private List<SiteSetting> sites;

	@Override
	public ScrollSiteSettingCollectionVer2 init(DataMapper mapper) throws IOException {
		ScrollSiteSettingCollectionVer2 loaded = mapper.readFromJson(ScrollSiteSettingCollectionVer2.class,
			"dynamic_site_config_test.json");
		this.sites = loaded.getSites();
		return this;
	}
}
