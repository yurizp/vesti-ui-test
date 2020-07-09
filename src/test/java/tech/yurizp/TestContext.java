package tech.yurizp;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.io.File;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestContext {

    public static WebDriver driver;

    @BeforeSuite
    public void ini() {
        if (driver == null) {
            Map<String, String> mobileEmulation = Collections.singletonMap("deviceName", "Nexus 5");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
            System.setProperty(
                    "webdriver.chrome.driver", new File("src/main/resources/chromedriver").getAbsolutePath());
            driver = new ChromeDriver(chromeOptions);
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
            driver.manage().timeouts().pageLoadTimeout(1, TimeUnit.MINUTES);
            driver.manage().timeouts().setScriptTimeout(1, TimeUnit.MINUTES);
            driver.manage().window().setSize(new Dimension(400, 1000));
        }
    }

    @AfterSuite
    public void close() {
        driver.quit();
    }

    public void clearCache() {
        driver.manage().deleteAllCookies();
        driver.get("chrome://settings/clearBrowserData");
    }

}
