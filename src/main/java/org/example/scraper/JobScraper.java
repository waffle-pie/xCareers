package org.example.scraper;

import java.io.IOException;
import java.util.List;

import org.example.mapper.DataMapper;
import org.example.recruitment.RecruitmentNotice;
import org.example.setting.SettingCollection;

public abstract class JobScraper<T extends SettingCollection<T>> {
	private final DataMapper mapper;
	private final T siteCandidates;

	public JobScraper(T siteCandidates) {
		this.siteCandidates = siteCandidates;
		this.mapper = new DataMapper();
	}

	public void run() throws IOException {
		List<RecruitmentNotice> recruitmentNotices = scrapingBy(siteCandidates.init(mapper));
		mapper.writeToJson(recruitmentNotices, "jobs.json");
	}

	public abstract List<RecruitmentNotice> scrapingBy(T setting) throws
		IOException;
}
