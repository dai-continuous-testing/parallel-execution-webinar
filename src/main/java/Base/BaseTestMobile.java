package Base;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.experitest.appium.SeeTestClient;

import io.appium.java_client.AppiumDriver;

public class BaseTestMobile {

	protected AppiumDriver<WebElement> driver = null;
	protected WebDriverWait wait = null;
	protected SeeTestClient client = null;
	protected DesiredCapabilities dc = new DesiredCapabilities();
	protected Properties cloudProperties = new Properties();
	private final Logger log = LoggerFactory.getLogger(this.getClass().getName());


	public void init(String deviceQuery) throws Exception {
		initCloudProperties();
		String adhocDeviceQuery = System.getenv("deviceQuery");
		if (adhocDeviceQuery != null) {
			System.out.println("[INFO] Redirecting test to the current device.");
			deviceQuery = adhocDeviceQuery;
		}
		dc.setCapability("deviceQuery", deviceQuery);
		dc.setCapability("reportDirectory", "reports");
		dc.setCapability("reportFormat", "xml");
		String accessKey = getProperty("accessKey", cloudProperties);
		if (accessKey != null && !accessKey.isEmpty()) {
			dc.setCapability("accessKey", accessKey);
		} else {
			dc.setCapability("user", getProperty("username", cloudProperties));
			dc.setCapability("password", getProperty("password", cloudProperties));
		}
		// In case your user is assign to a single project leave empty,
		// otherwise please specify the project name
		dc.setCapability("project", getProperty("project", cloudProperties));
	}

	protected String getProperty(String property, Properties props) {
		if (System.getProperty(property) != null) {
			return System.getProperty(property);
		} else if (System.getenv().containsKey(property)) {
			return System.getenv(property);
		} else if (props != null) {
			return props.getProperty(property);
		}
		return null;
	}

	private void initCloudProperties() throws FileNotFoundException, IOException {
		FileReader fr = new FileReader("cloud.properties");
		cloudProperties.load(fr);
		fr.close();
	}
	
	protected void logMethodInfo(Object arg1, Object arg2) {
		log.info("{} called from thread {}",arg1, arg2);
	}
}
