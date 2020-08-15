package tech.yurizp;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import tech.yurizp.Builder.RemoteWebDriverBuilder;

import java.net.MalformedURLException;

public class TestContext {

    public static WebDriver driver;

    @BeforeSuite
    public void ini() throws MalformedURLException {
        driver = RemoteWebDriverBuilder.instance();
    }

    @AfterSuite
    public void close() {
        driver.close();
        driver.quit();
    }

    public void clearCache() {
        driver.manage().deleteAllCookies();
        driver.get("chrome://settings/clearBrowserData");
    }


}
