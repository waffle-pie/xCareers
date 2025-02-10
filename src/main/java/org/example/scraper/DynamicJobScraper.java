package org.example.scraper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.example.recruitment.RecruitmentNotice;
import org.example.site.dynamic.DynamicSiteSetting;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DynamicJobScraper implements JobScraper {
	private final String PROJECT_PATH = System.getProperty("user.dir");

	List<DynamicSiteSetting> settings;
	private ObjectMapper objectMapper;

	public DynamicJobScraper(List<DynamicSiteSetting> settings, ObjectMapper objectMapper) {
		this.settings = settings;
		this.objectMapper = objectMapper;
	}

	@Override
	public void run() {
		try {
			List<RecruitmentNotice> allJobs = new ArrayList<>();

			// 프로젝트 상대 경로로 WebDriver 설정
			System.setProperty("webdriver.chrome.driver",
				PROJECT_PATH + "\\src\\main\\resources\\driver\\chromedriver.exe");

			for (DynamicSiteSetting site : settings) {
				WebDriver driver = new ChromeDriver();
				allJobs.addAll(scrapeSite(driver, site));
			}

			objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("jobs.json"), allJobs);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private List<RecruitmentNotice> scrapeSite(WebDriver driver, DynamicSiteSetting setting) {
		try {

			List<RecruitmentNotice> jobs = new ArrayList<>();
			// 웹사이트 접속
			driver.get(setting.getUrl());

			// XPath로 요소 찾기
			WebElement jobList = driver.findElement(By.xpath(setting.getJobListSelector()));

			// 리스트 안의 개별 항목 찾기
			List<WebElement> jobElements = jobList.findElements(By.tagName("li"));
			// 각 채용 공고 출력
			for (WebElement jobElement : jobElements) {
				String str = jobElement.getText();
				String[] jobDetails = str.split("\n");
				// 배열의 길이가 맞는지 확인하고 값을 추출
				if (jobDetails.length >= 6) {
					RecruitmentNotice recruitmentNotice = RecruitmentNotice.create(
						"무신사",
						jobDetails[0],  // title
						jobDetails[1],  // company
						jobDetails[2],  // jobRole
						jobDetails[3],  // career
						jobDetails[4],  // regular
						jobDetails[5]   // location
					);

					jobs.add(recruitmentNotice);
				}
			}
			return jobs;
		} finally {
			// 브라우저 종료
			driver.quit();
		}
	}
}

