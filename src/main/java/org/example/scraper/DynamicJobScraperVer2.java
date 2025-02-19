package org.example.scraper;

import org.example.recruitment.RecruitmentNotice;
import org.example.setting.SiteSetting;
import org.example.setting.DynamicSiteSettingCollectionVer2;
import org.example.setting.DynamicSiteSettingCollectionVer2;
import org.example.setting.SiteSetting;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class DynamicJobScraperVer2 extends JobScraper<DynamicSiteSettingCollectionVer2> {
	private final WebDriver webDriver;

	public DynamicJobScraperVer2(DynamicSiteSettingCollectionVer2 setting, WebDriver webDriver) {
		super(setting);
		this.webDriver = webDriver;
	}

	@Override
	public List<RecruitmentNotice> scrapingBy(DynamicSiteSettingCollectionVer2 setting) {

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
			JavascriptExecutor js = (JavascriptExecutor) webDriver;

			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
			List<WebElement> elements = webDriver.findElements(By.xpath("//*[@id=\"naver\"]/div/section/div/div/div[2]/div[2]/ul/li[*]/a"));
			Actions actions = new Actions(webDriver);

			// * 메서드 뺄것
			while (true){
				actions.sendKeys(org.openqa.selenium.Keys.PAGE_DOWN).perform();  // 한 페이지 아래로 이동
				List<WebElement> elements2 = webDriver.findElements(By.xpath("//*[@id=\"naver\"]/div/section/div/div/div[2]/div[2]/ul/li[*]/a"));
				if (elements.size() == elements2.size()) {
					break;
				}
			}
			//


			for (int i = 0; i < elements.size(); i++) {

				while (true){
					actions.sendKeys(org.openqa.selenium.Keys.PAGE_DOWN).perform();  // 한 페이지 아래로 이동
					List<WebElement> elements2 = webDriver.findElements(By.xpath("//*[@id=\"naver\"]/div/section/div/div/div[2]/div[2]/ul/li[*]/a"));
					if (elements.size() == elements2.size()) {
						break;
					}
				}
//				Thread.sleep(1000);

				WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"naver\"]/div/section/div/div/div[2]/div[2]/ul/li[" + (i+1) + "]/a")));
				js.executeScript("arguments[0].scrollIntoView(true);", element);
				js.executeScript("arguments[0].click();", element);
				webDriver.get(setting.getUrl());
//				actions.moveToElement(element).click().perform();
				// 요소가 클릭 가능할 때까지 대기
//				element = wait.until(ExpectedConditions.elementToBeClickable(element));

//				// 클릭
//				element.click();

				break;
//
			}

//			WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
//			WebElement jobList = wait.until(
//				ExpectedConditions.presenceOfElementLocated(By.xpath(setting.getJobListSelector())));
//
//			List<WebElement> jobElements = jobList.findElements(By.xpath(setting.getJobDetailSelector()));
//
//			if (jobElements.isEmpty()) {
//				System.out.println("❌ 공고 리스트가 비어 있음. XPath 확인 필요: " + setting.getJobListSelector());
//			}
//
//			for (WebElement jobElement : jobElements) {
//				try {
//					WebElement linkElement = null;
//					String title = null;
//					String link = null;
//
//					// ✅ href를 포함한 a 태그 찾기
//					try {
//						if (jobElement.getTagName().equals("a")) {
//							linkElement = jobElement;
//						} else {
//							linkElement = jobElement.findElement(By.tagName("a"));
//						}
//						link = linkElement.getAttribute("href");
//					} catch (NoSuchElementException e) {
//						System.out.println("⚠️ a 태그 없음");
//					}
//
//					title = jobElement.getAttribute("innerText");
//					if (title != null && link != null) {
//						jobs.add(RecruitmentNotice.create(setting.getSiteName(), title, link));
//					}
//				} catch (Exception e) {
//					System.out.println("⚠️ 특정 jobElement에서 데이터를 가져올 수 없음: " + e.getMessage());
//				}
//			}
		} catch (Exception e) {
			System.out.println("❌ 오류 발생: " + e.getMessage());
			e.printStackTrace();
		}
		return jobs;
	}

}