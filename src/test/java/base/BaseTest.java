package base;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Listeners;
import org.testobject.appium.testng.RemoteWebDriverProvider;
import org.testobject.appium.testng.TestObjectTestNGTestResultWatcher;
import com.automation.testapp.configuration.TestAppConfiguration;
import com.automation.testapp.pages.HomePage;
import io.appium.java_client.android.AndroidDriver;

@Listeners({ TestObjectTestNGTestResultWatcher.class })
public class BaseTest implements RemoteWebDriverProvider {

	protected static RemoteWebDriver driver;
	protected static HomePage homePage;
	protected static TestAppConfiguration testAppConf;

	protected void InitWebDriver() {
		driver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(100, TimeUnit.SECONDS);
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(100, TimeUnit.SECONDS);
	}

	@BeforeSuite(alwaysRun = true)
	public void AndroidDriverSet() {
		setup();
		openApp();
	}

	public void openApp() {
		homePage = HomePage.GetInstance(driver);
		homePage.allowStorage();
	}

	public void setup() {

		boolean localExecution = false;

		testAppConf = TestAppConfiguration.GetInstance();
		DesiredCapabilities capabilities = new DesiredCapabilities();
		capabilities = new DesiredCapabilities();
		if (localExecution) {
			capabilities.setCapability("deviceName",
					testAppConf.getDeviceName());
			capabilities.setCapability(CapabilityType.VERSION,
					testAppConf.getCapabilityVersion());
			capabilities.setCapability("platformName",
					testAppConf.getPlatformName());
			capabilities.setCapability("appPackage",
					testAppConf.getAppPackage());
			capabilities.setCapability("appActivity",
					testAppConf.getAppActivity());
			capabilities.setCapability("newCommandTimeout", 600);
			try {
				driver = new AndroidDriver(new URL(testAppConf.getAppiumURL()),
						capabilities);
			} catch (MalformedURLException e) {
				System.out.println("Pleasr check the local Appium URL");
			}
		} else {
			capabilities.setCapability("testobject_api_key",
					testAppConf.getApikey());
			capabilities.setCapability("testobject_app_id",
					testAppConf.getAppid());
			capabilities.setCapability("testobject_device",
					testAppConf.getDevice());
			try {
				driver = new RemoteWebDriver(new URL(
						testAppConf.getTestobjectURL()), capabilities);
			} catch (MalformedURLException e) {
				System.out.println("Pleasr check remote Appium URL");
			}
		}

	}

	protected void ExceptionHandle() {

	}

	@AfterMethod
	public void WebDriverQuit() {
		driver.quit();
	}

	public URL getRemoteAddress() {
		URL url = null;
		try {
			url = new URL(testAppConf.getTestobjectURL());
		} catch (MalformedURLException e1) {
			System.out.println("Pleasr check the remote Appium URL");
		}
		return url;
	}

	public RemoteWebDriver getRemoteWebDriver() {
		// TODO Auto-generated method stub
		return this.driver;
	}

}
