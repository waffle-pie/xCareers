package org.example.scraper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.example.recruitment.RecruitmentNotice;
import org.example.setting.DynamicSiteSettingCollection;
import org.example.setting.SiteSetting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DynamicJobScraper extends JobScraper<DynamicSiteSettingCollection> {
	private final WebDriver webDriver;

	public DynamicJobScraper(DynamicSiteSettingCollection setting, WebDriver webDriver) {
		super(setting);
		this.webDriver = webDriver;
	}

	@Override
	public List<RecruitmentNotice> scrapingBy(DynamicSiteSettingCollection setting) {

		List<RecruitmentNotice> allJobs = new ArrayList<>();

		for (SiteSetting site : setting.getSites()) {
			allJobs.addAll(scraping(site));
		}
		return allJobs;
	}

	private List<RecruitmentNotice> scraping(SiteSetting setting) {
		List<RecruitmentNotice> jobs = new ArrayList<>();
		try {
			webDriver.get(setting.getUrl());

			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
			WebElement jobList = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath(setting.getJobListSelector())));

			List<WebElement> jobElements = jobList.findElements(By.xpath(setting.getJobDetailSelector()));

			if (jobElements.isEmpty()) {
				System.out.println("❌ 공고 리스트가 비어 있음. XPath 확인 필요: " + setting.getJobListSelector());
			}

			for (WebElement jobElement : jobElements) {
				try {
					WebElement linkElement = null;
					String title = null;
					String link = null;

					// ✅ href를 포함한 a 태그 찾기
					// script 방식
					if (setting.getSiteName().equals("네이버")) {
						link = script(jobElement, link);
					} else {
						// a 태그 직접
						try {
							if (jobElement.getTagName().equals("a")) {
								linkElement = jobElement;
							} else {
								linkElement = jobElement.findElement(By.tagName("a"));
							}
							link = linkElement.getAttribute("href");
						} catch (NoSuchElementException e) {
							System.out.println("⚠️ a 태그 없음");
						}
					}

					title = jobElement.getAttribute("innerText");
					if (title != null && link != null) {
						jobs.add(RecruitmentNotice.create(setting.getSiteName(), title, link));
					}
				} catch (Exception e) {
					System.out.println("⚠️ 특정 jobElement에서 데이터를 가져올 수 없음: " + e.getMessage());
				}
			}
		} catch (Exception e) {
			System.out.println("❌ 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return jobs;
	}

	private String script(WebElement jobElement, String link) {
		WebElement linkElement;
		try {
			linkElement = jobElement.findElement(By.tagName("a"));
			String onClickValue = linkElement.getAttribute("onclick");
			link = linkElement.getAttribute("href");

			if (onClickValue != null && onClickValue.contains("show(")) {
				String jobId = onClickValue.replaceAll("[^0-9]", ""); // 숫자만 추출
				link = "https://recruit.navercorp.com/rcrt/view.do?annoId=" + jobId;
			}
		} catch (NoSuchElementException e) {
			System.out.println("⚠️ a 태그 없음");
		}
		return link;
	}

}