package org.example.scraper;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

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

		for (PaginationSiteSetting site : setting.getSites()) {
			allJobs.addAll(scraping(site));
		}
		return allJobs;
	}

	protected List<RecruitmentNotice> scraping(PaginationSiteSetting setting) {
		List<RecruitmentNotice> jobs = new ArrayList<>();
		try {
			webDriver.get(setting.getUrl());
			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(5));

			while (true) { // 페이지 끝까지 반복
				try {
					// 페이지가 완전히 로드될 때까지 대기
					wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

					// 현재 페이지에서 jobList 가져오기
					WebElement jobList = wait.until(
						ExpectedConditions.presenceOfElementLocated(By.xpath(setting.getJobListSelector())));
					List<WebElement> jobElements = (jobList.findElements(By.xpath(setting.getJobDetailSelector())));

					for (WebElement jobElement : jobElements) {
						try {
							WebElement linkElement = null;
							String title = null;
							String link = null;

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
					if (!existNextPage(setting))
						break;

				} catch (Exception e) {
					System.out.println("❌ 페이지네이션 중 오류 발생: " + e.getMessage());
					break;
				}
			}

		} catch (Exception e) {
			System.out.println("❌ 전체 크롤링 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return jobs;
	}

	private boolean existNextPage(PaginationSiteSetting setting) throws InterruptedException {
		try {
			// "다음" 버튼이 클릭 가능한지 기다리기
			List<WebElement> paginationButton = webDriver.findElements(
				By.xpath(setting.getPaginationNextButtonSelector()));
			paginationButton.get(0).findElement(By.className("next")).click();
			Thread.sleep(2000);

			return true;
		} catch (NoSuchElementException e) {
			System.out.println("✅ 페이지네이션 끝, 더 이상 페이지 없음");
			return false;  // 더 이상 페이지가 없으면 종료
		} catch (Exception e) {
			System.out.println("⚠️ 페이지네이션 버튼 클릭 실패: " + e.getMessage());
			return false;  // 클릭 실패 시 종료
		}
	}

}