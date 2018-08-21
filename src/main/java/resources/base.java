package resources;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import org.openqa.selenium.chrome.ChromeOptions;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class base {
	public WebDriver driver;
	public Properties prop;

	public WebDriver initializeDriver() throws IOException {
		prop = new Properties();
		FileInputStream fis = new FileInputStream(new File(System.getProperty("user.dir")+
				"/src/main/java/resources/data.properties/"));
		prop.load(fis);
		String browserName = prop.getProperty("browser");
		if (browserName.equals("firefox")) {
			System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "/src/main/java/resources/geckodriver");
			driver = new FirefoxDriver();
		} else if (browserName.equals("chrome")) {
			System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "/src/main/java/resources/chromedriver");
			ChromeOptions options = new ChromeOptions();
			options.addArguments("start-maximized");
			options.addArguments("--window-size=1920,1080");
			driver = new ChromeDriver(options);
		}
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		return driver;
	}
}
