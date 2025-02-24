package org.example;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PaginationHandler {
	private final WebDriver driver;

	public PaginationHandler(WebDriver driver) {
		this.driver = driver;
	}

	public boolean clickNextPage(String nextButtonSelector) {
		try {
			WebElement nextButton = findNextButton(nextButtonSelector);
			if (existNextButton(nextButton))
				return false;

			clickNextButton(nextButton);

			Thread.sleep(1000);
			return true;
		} catch (Exception e) {
			System.out.println("⚠️ 페이지네이션 버튼 클릭 실패: " + e.getMessage());
			return false;
		}
	}

	private void clickNextButton(WebElement nextButton) {
		String onClickAttribute = nextButton.getAttribute("onclick");

		if (isScriptType(onClickAttribute)) {
			((JavascriptExecutor)driver).executeScript(onClickAttribute);
		} else {
			nextButton.click();
		}
	}

	private WebElement findNextButton(String nextButtonSelector) {
		List<WebElement> nextButtons = driver.findElements(By.className(nextButtonSelector));
		if (nextButtons.isEmpty()) {
			return null;
		}
		return nextButtons.getFirst();
	}

	private boolean existNextButton(WebElement nextButton) {
		return nextButton == null;
	}

	private boolean isScriptType(String onClickAttribute) {
		return onClickAttribute != null && !onClickAttribute.isEmpty();
	}
}
