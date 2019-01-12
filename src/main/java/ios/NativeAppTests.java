package ios;

import java.net.URL;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.experitest.appium.SeeTestClient;

import Base.BaseTestMobile;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.IOSElement;
import io.appium.java_client.remote.IOSMobileCapabilityType;
import io.appium.java_client.remote.MobileCapabilityType;

public class NativeAppTests extends BaseTestMobile {
	
	private final String DEFAULT_QUERY = "@os='ios' and @category='PHONE'";
	private final String APPLICATION = "cloud:com.experitest.UICatalog";
	private final String BUNDLE_ID = "com.experitest.UICatalog";
	private final String VERSION = "2942";

	@BeforeClass
	@Parameters("deviceQuery")
	public void setupDevice(@Optional(DEFAULT_QUERY) String deviceQuery) throws Exception {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		init(deviceQuery);
        dc.setCapability("testName", "iOS Native test suite");
		dc.setCapability(MobileCapabilityType.APP, APPLICATION );
		dc.setCapability(IOSMobileCapabilityType.BUNDLE_ID, BUNDLE_ID);
		dc.setCapability("appVersion", VERSION);
		dc.setCapability("instrumentApp", true);
		driver = new IOSDriver<>(new URL(getProperty("url",cloudProperties) + "/wd/hub"), dc);
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		client = new SeeTestClient(driver);
		wait = new WebDriverWait(driver, 10);
	}
	
	@BeforeMethod
	public void setupTest() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		client.launch(APPLICATION, true, false);
	}
	
	public void mapTest() throws InterruptedException {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		client.swipe("DOWN", 300, 500);
		driver.findElement(By.xpath("//*[@accessibilityLabel='Map']")).click();
		try{
			driver.findElement(By.xpath("//*[@id='Allow']")).click();
		} catch (NoSuchElementException e) {
			// TODO: handle exception
		}
		Thread.sleep(2000);
	}

	public void webElementsTest() throws InterruptedException {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		driver.findElement(By.xpath("//*[@accessibilityLabel='Web']")).click();
		driver.findElement(By.xpath("//*[@class='_UITextFieldContentView']")).clear();
		driver.findElement(By.xpath("//*[@accessibilityLabel='<enter a URL>']")).sendKeys("www.espn.com");
		driver.findElement(By.xpath("(//*[@class='UIKBKeyplaneView']/*[@class='UIKBKeyView'])[8]")).click();
		Thread.sleep(3000);
	}
	
	public void toolBarTest() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		driver.findElement(By.xpath("//*[@accessibilityLabel='Toolbar']")).click();
		driver.findElement(By.xpath("//*[@accessibilityLabel='Black']")).click();
		driver.findElement(By.xpath("//*[@accessibilityLabel='Translucent']")).click();
		driver.findElement(By.xpath("//*[@accessibilityLabel='Tinted']")).click();
		driver.findElement(By.xpath("//*[@accessibilityLabel='Tinted']")).click();
		driver.findElement(By.xpath("//*[@accessibilityLabel='Image']")).click();
		driver.findElement(By.xpath("//*[@accessibilityLabel='Bordered']")).click();
		driver.findElement(By.xpath("//*[@accessibilityLabel='Done']")).click();
		driver.findElement(By.xpath("//*[@accessibilityLabel='Image']")).click();
	}
	
	@Test
	public void imagesTest() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		driver.findElement(By.xpath("//*[@accessibilityLabel='Images']")).click();
		IOSElement slider = (IOSElement) driver.findElement(By.xpath("//*[@accessibilityLabel='Duration']"));
		slider.click();
		
	}


	@AfterMethod
	public void cleanUpTest() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		client.applicationClose(APPLICATION);
	}
	
	@AfterClass
	public void releaseDevice() {
		logMethodInfo(Thread.currentThread().getStackTrace()[1].getMethodName(), Thread.currentThread().getId());
		driver.quit();
	}
}
