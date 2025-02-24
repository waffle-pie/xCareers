package org.example.scraper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.example.PaginationHandler;
import org.example.recruitment.RecruitmentNotice;
import org.example.setting.PaginationSiteSetting;
import org.example.setting.collection.PaginationSiteSettingCollection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class PaginationJobScraper extends JobScraper<PaginationSiteSettingCollection> {
	private final WebDriver webDriver;

	public PaginationJobScraper(PaginationSiteSettingCollection siteCandidates, WebDriver webDriver) {
		super(siteCandidates);
		this.webDriver = webDriver;
	}

	@Override
	public List<RecruitmentNotice> scrapingBy(PaginationSiteSettingCollection setting) {

		List<RecruitmentNotice> allJobs = new ArrayList<>();
		PaginationHandler paginationHandler = new PaginationHandler(webDriver);
		for (PaginationSiteSetting site : setting.getSites()) {
			allJobs.addAll(scraping(site, paginationHandler));
		}
		return allJobs;
	}

	protected List<RecruitmentNotice> scraping(PaginationSiteSetting setting, PaginationHandler paginationHandler) {
		List<RecruitmentNotice> jobs = new ArrayList<>();
		try {
			webDriver.get(setting.getUrl());
			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));
			wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
			int prevPageSize = -1; // 첫 페이지 이전 값 없음

			while (isClicked(setting, paginationHandler)) { // 페이지 끝까지 반복
				try {
					WebElement jobList = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath(setting.getJobListSelector())));
					List<WebElement> jobElements = (jobList.findElements(By.xpath(setting.getJobDetailSelector())));

					for (WebElement jobElement : jobElements) {
						try {
							WebElement linkElement = null;
							String title = null;
							String link = "";

							title = jobElement.getAttribute("innerText");
							if (title != null && link != null) {
								jobs.add(RecruitmentNotice.create(setting.getSiteName(), title, link));
							}
						} catch (Exception e) {
							System.out.println(
								"⚠️ 특정 jobElement에서 데이터를 가져올 수 없음: " + e.getMessage() + "Object"
									+ jobElement.toString());
						}
					}

					int pageSize = jobElements.size();
					if (prevPageSize != -1 && pageSize < prevPageSize) {
						break;
					}
					prevPageSize = pageSize;

				} catch (Exception e) {
					System.out.println("❌ 페이지네이션 중 오류 발생: " + e.getMessage());
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("❌ 전체 크롤링 오류 발생: " + e.getMessage());
		}
		return jobs;
	}

	private boolean isClicked(PaginationSiteSetting setting, PaginationHandler paginationHandler) {
		return paginationHandler.clickNextPage(setting.getPaginationNextButtonCssSelector());
	}
}