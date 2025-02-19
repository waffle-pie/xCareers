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

public class PaginationJobClickScraper extends JobScraper<DynamicSiteSettingCollectionVer2> {
    private final WebDriver webDriver;

    public PaginationJobClickScraper(DynamicSiteSettingCollectionVer2 setting, WebDriver webDriver) {
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


        } catch (Exception e) {
            System.out.println("❌ 오류 발생: " + e.getMessage());
            e.printStackTrace();
        }
        return jobs;
    }

}