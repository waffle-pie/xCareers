package org.example.scraper;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import org.example.recruitment.RecruitmentNotice;
import org.example.setting.DynamicSiteSetting;
import org.example.setting.DynamicSiteSettingCollection;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.fasterxml.jackson.databind.ObjectMapper;

public class DynamicJobScraper extends JobScraper<DynamicSiteSettingCollection> {
	private final String PROJECT_PATH = System.getProperty("user.dir");

	public DynamicJobScraper(DynamicSiteSettingCollection setting) {
		super(setting);
	}

	@Override
	public void setUp(DynamicSiteSettingCollection setting, ObjectMapper objectMapper) {
		try {
			List<RecruitmentNotice> allJobs = new ArrayList<>();

			// 프로젝트 상대 경로로 WebDriver 설정
			System.setProperty("webdriver.chrome.driver",
				PROJECT_PATH + "\\src\\main\\resources\\driver\\chromedriver.exe");

			for (DynamicSiteSetting site : setting.getSites()) {
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
			driver.get(setting.getUrl());

			WebElement jobList = driver.findElement(By.xpath(setting.getJobListSelector()));

			List<WebElement> jobElements = jobList.findElements(By.tagName("li"));
			List<WebElement> jobLinks = jobList.findElements(By.tagName("a"));

			String check = jobList.getText();

			for (WebElement jobElement : jobElements) {
				String str = jobElement.getText();
			}

			return List.of();
		} finally {
			driver.quit();
		}
	}
}

