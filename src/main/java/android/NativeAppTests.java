package android;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.experitest.appium.SeeTestClient;

import Base.BaseTestMobile;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.AndroidMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class NativeAppTests extends BaseTestMobile {
	
	private final String DEFAULT_QUERY = "@os='android' and @category='PHONE'";
	private final String APPLICATION = "cloud:com.experitest.uicatalog/.MainActivity";
	private final String PACKAGE = "com.experitest.uicatalog";
	private final String ACTIVITY = ".MainActivity";
	private final String VERSION = "3.2740";
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

	@BeforeClass
	@Parameters("deviceQuery")
	public void setupDevice(@Optional(DEFAULT_QUERY) String deviceQuery) throws Exception {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		init(deviceQuery);
		dc.setCapability("testName", "Android Native test suite");
		dc.setCapability(MobileCapabilityType.APP, APPLICATION);
		dc.setCapability("appVersion", VERSION);
		dc.setCapability(AndroidMobileCapabilityType.APP_PACKAGE, PACKAGE);
		dc.setCapability(AndroidMobileCapabilityType.APP_ACTIVITY, ACTIVITY);
		dc.setCapability("instrumentApp", true);
		driver = new AndroidDriver<>(new URL(getProperty("url",cloudProperties) + "/wd/hub"), dc);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		client = new SeeTestClient(driver);
		wait = new WebDriverWait(driver, 10);
	}
	
	@BeforeMethod
	public void setupTest(Method method) {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		client.launch(APPLICATION, true, false);
	}
	
	@Test
	public void mapTest() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		client.swipe("DOWN", 300, 500);
        driver.findElement(By.xpath("//*[@text='GoogleMaps']")).click();
        try {
        	driver.findElement(By.xpath("//*[@id='permission_allow_button']")).click();
        } catch (NoSuchElementException e) {
		}
        String location = driver.findElement(By.xpath("//*[@id='locationText']")).getAttribute("text");
        client.report(location, true);
        client.setLocation("0", "0");
	}
	
	@Test
	public void webElementsTest() throws InterruptedException {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		driver.findElement(By.xpath("//*[@text='WebElements']")).click();
		driver.findElement(By.xpath("//*[@id='UrlText']")).sendKeys("www.espn.com");
		driver.findElement(By.xpath("//*[@id='urlLockApplier']")).sendKeys("experitest1");
	}
	
	@Test
	public void alertTest() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
        driver.findElement(By.xpath("//*[@text='Alerts']")).click();
        driver.findElement(By.id("button2")).click();
        driver.findElement(By.id("button3")).click();
        driver.findElement(By.id("button3")).click();
	}
	
	@Test
	public void listsTest() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
        driver.findElement(By.xpath("//*[@text='Lists']")).click();
        client.swipe("DOWN", 500, 500);
		client.swipe("UP", 500, 500);
        List<WebElement> listitems = driver.findElements(By.xpath("//*[@id='listView2']//*[@id='text1']"));
        for(int i=0;i<5;i++) {
        	listitems.get(i).click();
        	driver.findElement(By.xpath("//*[@id='button1']")).click();
        }
	}
	
	@AfterMethod
	public void cleanUpTest() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		client.applicationClose(PACKAGE);
	}

	@AfterClass
	public void releaseDevice() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		driver.quit();
	}

}
