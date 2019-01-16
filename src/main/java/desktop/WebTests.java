package desktop;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import Base.BaseTestBrowser;

public class WebTests extends BaseTestBrowser {

	@BeforeClass
	public void setUpEnv() throws Exception {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		init();
	}


	@BeforeMethod
	@Parameters({ "browser", "version", "platform" })
	public void setupBrowser(@Optional("chrome") String browser, @Optional("Any") String version,
			@Optional("Any") String platform, Method method) throws Exception {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		DesiredCapabilities dc = new DesiredCapabilities(this.dc);
		dc.setCapability(CapabilityType.BROWSER_NAME, browser);
		dc.setCapability(CapabilityType.VERSION, version);
		dc.setCapability(CapabilityType.PLATFORM, platform);
		dc.setCapability("testName", "Parallel execution demo for "+ browser + " : " + method.getName());
		ldriver.set(new RemoteWebDriver(new URL(getProperty("url", cloudProperties) + "/wd/hub"), dc));
		RemoteWebDriver driver = ldriver.get();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.manage().window().maximize();
	}

	@Test
	public void googleTest() throws InterruptedException {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		RemoteWebDriver driver = ldriver.get();
		driver.get("https://www.google.com/");
		WebElement searchbar = driver.findElement(By.name("q"));
		searchbar.sendKeys("Experitest");
		WebElement searchBtn = driver.findElement(By.xpath("//div[@class='FPdoLc VlcLAe']//input[contains(@value, 'Google')]"));
		driver.executeScript("arguments[0].click();", searchBtn);
		driver.findElement(By.xpath("//div[@class='rc']//*[contains(text(),'Experitest')]")).click();
		Thread.sleep(5000);
	}
	@Test
	public void amazonTest() throws InterruptedException {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		RemoteWebDriver driver = ldriver.get();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.get("https://www.amazon.com/");
		driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("peace");
		driver.findElement(By.xpath("//input[@value='Go']")).click();
		Select sort = new Select(driver.findElement(By.id("sort")));
		sort.selectByValue("price-asc-rank");
		Thread.sleep(3000);
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
		Thread.sleep(1000);
		js.executeScript("window.scrollTo(0, -1*document.body.scrollHeight)");
		Thread.sleep(1000);
	}
	@Test
	public void premierLeagueTest() throws InterruptedException {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		RemoteWebDriver driver = ldriver.get();
		driver.get("https://www.premierleague.com/");
		Actions action = new Actions(driver);
		action.moveToElement(driver.findElement(By.xpath("//*[@id='mainNav']/nav/ul/li[1]"))).perform();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//a[@tabindex='0'][contains(text(),'Tables')]")).click();
		Thread.sleep(3000);
		WebElement table = driver.findElement(By.xpath("//*[@class='tableBodyContainer']"));
		List<WebElement> tablerows = table.findElements(By.tagName("tr"));
		for (WebElement row : tablerows) {
			if (row.getAttribute("data-filtered-table-row-name") != null) {
				List<WebElement> cols = row.findElements(By.tagName("td"));
				for (WebElement col : cols) {
					if (col.getText() != null) {
					}
				}
			}
		}
	}
	@Test
	public void experitestTest() throws InterruptedException {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		RemoteWebDriver driver = ldriver.get();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		driver.get("https://experitest.com/");
		WebElement solutions = driver.findElement(By.xpath("//*[@id='menu-item-13082']/a"));
		js.executeScript("arguments[0].click();", solutions);
		Thread.sleep(1000);
		WebElement mobile = driver.findElement(By.xpath("//*[@id='menu-item-13084']/a/div[4]"));
		js.executeScript("arguments[0].click();", mobile);
		Thread.sleep(1000);
		List<WebElement> stops = driver.findElements(By.xpath("//*[contains(@id, 'accordion')]"));
		for (WebElement stop : stops) {
			js.executeScript("arguments[0].scrollIntoView();", stop);
			Thread.sleep(1000);
		}
	}

	@AfterMethod
	public void releaseBrowser() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		RemoteWebDriver driver = ldriver.get();
		driver.quit();
	}

}
