package org.example.scraper;

import java.io.File;
import java.net.URL;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.example.recruitment.RecruitmentNotice;
import org.example.setting.DynamicSiteSetting;
import org.example.setting.DynamicSiteSettingCollection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DynamicJobScraper extends JobScraper<DynamicSiteSettingCollection> {
	private final String GRID_URL = "http://localhost:4444/wd/hub";

	public DynamicJobScraper(DynamicSiteSettingCollection setting) {
		super(setting);
	}

	@Override
	public void setUp(DynamicSiteSettingCollection setting, ObjectMapper objectMapper) {
		try {
			List<RecruitmentNotice> allJobs = new ArrayList<>();

			ChromeOptions options = new ChromeOptions();
			options.setCapability("browserName", "chrome");

			WebDriver driver = null;
			try {
				driver = new RemoteWebDriver(new URL(GRID_URL), options);
				for (DynamicSiteSetting site : setting.getSites()) {
					allJobs.addAll(scrapeSite(driver, site));
				}

				objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("jobs.json"), allJobs);
			} catch (NoSuchElementException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (driver != null) {
					driver.quit();
				}
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private List<RecruitmentNotice> scrapeSite(WebDriver driver, DynamicSiteSetting setting) {
		List<RecruitmentNotice> jobs = new ArrayList<>();
		try {
			driver.get(setting.getUrl());

			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
			WebElement jobList = wait.until(
				ExpectedConditions.presenceOfElementLocated(By.xpath(setting.getJobListSelector())));

			// ✅ 공고 리스트 확인
			// 무신사 Xpath <ul> -> for <a> 당근 -> <ul> -> <div> -> <li>
			List<WebElement> jobElements = jobList.findElements(By.xpath("./div/li | ./div/li/a | ./a"));

			if (jobElements.isEmpty()) {
				System.out.println("❌ 공고 리스트가 비어 있음. XPath 확인 필요: " + setting.getJobListSelector());
			}

			for (WebElement jobElement : jobElements) {
				try {
					WebElement linkElement = null;
					String title = null;
					String link = null;

					// ✅ href를 포함한 a 태그 찾기
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

					try {
						title = jobElement.findElement(By.tagName("h3")).getText().trim();
					} catch (NoSuchElementException e) {
						title = jobElement.getText().trim(); // h3이 없으면 텍스트 직접 가져오기
					}

					// ✅ 결과 저장
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

}