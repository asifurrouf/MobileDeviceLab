package com.automation.testapp.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.Assert;

import com.automation.testapp.base.BasePage;

public class HomePage extends BasePage {

	public HomePage(RemoteWebDriver driver) {
		super(driver);
	}

	public LoginPage openLoginPage() {
		if (!isElementPresent(By.name("Sign in to IMDb")))
		openHamburgerMenu();
		clickElement(By.name("Sign in to IMDb"), 0);
		return new LoginPage(driver);
	}

	public SearchPage searchMovie() {
		clickElement(By.name("Search IMDb"), 0);
		sendKey(By.name("   Search IMDb"), 0, testappConf.getMovieName() + "\n");
		Assert.assertTrue(getText(By.id("com.imdb.mobile:id/label"), 0)
				.contains(testappConf.getMovieName()));
		return new SearchPage(driver);

	}

	public void allowStorage() {

		if (isElementPresent(By
				.id("com.android.packageinstaller:id/permission_allow_button"))) {
			clickElement(
					By.id("com.android.packageinstaller:id/permission_allow_button"),
					0);
			goHomePage();
		}

	}
}
