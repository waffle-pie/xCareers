package org.example.scraper;

import java.io.IOException;
import java.util.List;

import org.example.mapper.DataMapper;
import org.example.recruitment.RecruitmentNotice;
import org.example.setting.SettingCollection;

public abstract class JobScraper<T extends SettingCollection<T>> {
	private final T siteCandidates;

	public JobScraper(T siteCandidates) {
		this.siteCandidates = siteCandidates;
	}

	public void run(DataMapper mapper) throws IOException {
		siteCandidates.init(mapper);
		List<RecruitmentNotice> recruitmentNotices = scrapingBy(siteCandidates);
		mapper.writeToJson(recruitmentNotices, "jobs.json");
	}

	public abstract List<RecruitmentNotice> scrapingBy(T siteCandidates) throws
		IOException;
}
