package org.example.scraper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.example.recruitment.RecruitmentNotice;
import org.example.site.SiteSetting;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;

public class StaticJobScraper implements JobScraper {

	private List<SiteSetting> settings;
	private ObjectMapper objectMapper;

	public StaticJobScraper(List<SiteSetting> settings, ObjectMapper objectMapper) {
		this.settings = settings;
		this.objectMapper = objectMapper;
	}

	@Override
	public void run() {
		try {
			// 1️⃣ JSON 설정 파일 읽기
			List<RecruitmentNotice> allJobs = new ArrayList<>();

			// 2️⃣ 각 사이트 크롤링 실행
			for (SiteSetting site : settings) {
				allJobs.addAll(scrapeSite(site));
			}

			// 3️⃣ 결과 JSON 저장
			objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("jobs.json"), allJobs);
			System.out.println("📁 크롤링 완료: jobs.json 파일 저장됨");

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private List<RecruitmentNotice> scrapeSite(SiteSetting setting) {
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
