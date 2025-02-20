package org.example.scraper;

import org.example.recruitment.RecruitmentNotice;
import org.example.setting.PaginationSiteSetting;

import org.example.setting.DynamicSiteSettingCollectionVer2;
import org.example.setting.collection.PaginationClickSiteSettingCollection;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class PaginationJobClickScraper extends JobScraper<PaginationClickSiteSettingCollection> {
    private final WebDriver webDriver;

    public PaginationJobClickScraper(PaginationClickSiteSettingCollection setting, WebDriver webDriver) {
        super(setting);
        this.webDriver = webDriver;
    }

    @Override
    public List<RecruitmentNotice> scrapingBy(PaginationClickSiteSettingCollection setting) {

        List<RecruitmentNotice> allJobs = new ArrayList<>();

        for (PaginationSiteSetting site : setting.getSites()) {
            allJobs.addAll(scraping(site));
        }
        return allJobs;
    }

    private List<RecruitmentNotice> scraping(PaginationSiteSetting setting) {
        List<RecruitmentNotice> jobs = new ArrayList<>();
        try {
            webDriver.get(setting.getUrl());
            JavascriptExecutor js = (JavascriptExecutor) webDriver;
            WebDriverWait wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
            int cnt = 0;
            for (int i = 0; i < 10; i++) {
                try {
                    WebElement element1 = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"jobPage\"]/a[3]")));
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contWrap\"]/div[2]/div/div[2]/div[2]/ul/li[*]/a")));
                    List<WebElement> elements = webDriver.findElements(By.xpath("//*[@id=\"contWrap\"]/div[2]/div/div[2]/div[2]/ul/li[*]/a"));
                    for (int j = 0; j < elements.size(); j++) {
                        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"contWrap\"]/div[2]/div/div[2]/div[2]/ul/li[" + (i + 1) + "]/a")));

                        js.executeScript("arguments[0].scrollIntoView(true);", element);
                        js.executeScript("arguments[0].click();", element);
                        js.executeScript("window.history.back();");
                        Thread.sleep(1000);// 테스트용
                        System.out.println(++cnt);
                    }

                    js.executeScript("arguments[0].scrollIntoView(true);", element1);
                    js.executeScript("arguments[0].click();", element1);
                    // 버튼이 있으면...? 만약에 있는데... 마지막 페이지라면...?( 현대 자동차가 버튼은 존재함.

                }catch (NoSuchElementException e){
                    System.out.println("요소 없음"+ e.getMessage());
                    Thread.sleep(20000);// 테스트용
                    break;
                }catch (Exception e){
                    Thread.sleep(20000);// 테스트용
                    System.out.println(e.getMessage());
                    break;
                }

            }
        } catch (Exception e) {
            System.out.println("❌ 오류 발생: " + e.getMessage());

            e.printStackTrace();
        }
        return jobs;
    }

}