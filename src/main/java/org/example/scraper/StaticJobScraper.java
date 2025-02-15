package org.example.scraper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.recruitment.RecruitmentNotice;
import org.example.setting.StaticSiteSetting;
import org.example.setting.StaticSiteSettingCollection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class StaticJobScraper extends JobScraper<StaticSiteSettingCollection> {

	public StaticJobScraper(StaticSiteSettingCollection setting) {
		super(setting);
	}

	@Override
	public List<RecruitmentNotice> scrapingBy(StaticSiteSettingCollection setting) {
		List<RecruitmentNotice> allJobs = new ArrayList<>();

		for (StaticSiteSetting site : setting.getSites()) {
			allJobs.addAll(scrapeSite(site));
		}
		return allJobs;
	}

	private List<RecruitmentNotice> scrapeSite(StaticSiteSetting setting) {
		List<RecruitmentNotice> jobs = new ArrayList<>();
		try {
			// 4️⃣ 해당 사이트에서 HTML 가져오기
			Document doc = Jsoup.connect(setting.getUrl()).get();

			// 5️⃣ 채용 공고 목록 가져오기
			Elements jobElements = doc.select(setting.getJobListSelector());

			for (Element jobElement : jobElements) {
				// 6️⃣ 개별 채용 공고 정보 추출
				RecruitmentNotice job = RecruitmentNotice.create(
					setting.getSiteName(),
					getText(jobElement, setting.getTitleSelector()),
					getText(jobElement, setting.getCompanySelector()),
					getText(jobElement, setting.getJobSelector()),
					getText(jobElement, setting.getCareerSelector()),
					getText(jobElement, setting.getRegularSelector()),
					getText(jobElement, setting.getLocationSelector()),
					getText(jobElement, setting.getDeadlineSelector())
				);
				jobs.add(job);
			}

		} catch (IOException e) {
			System.err.println("❌ 오류 발생: " + setting.getSiteName() + " (" + e.getMessage() + ")");
		}
		return jobs;
	}

	private String getText(Element parent, String selector) {
		Element element = parent.selectFirst(selector);
		return element != null ? element.text().trim() : "정보 없음";
	}

}
