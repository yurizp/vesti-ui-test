package mobi.vesti.test;

import lombok.SneakyThrows;
import mobi.vesti.webdriver.builder.RemoteWebDriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;

public class TestContext {

    public static WebDriver driver;

    @BeforeClass
    public static void ini() throws MalformedURLException {
        driver = RemoteWebDriverBuilder.instance();
    }

    @SneakyThrows
    @AfterClass
    public static void close() {
        driver.close();
        driver.quit();
    }

    @AfterMethod
    public void clearCache() {
        try {
            ((RemoteWebDriver) driver).executeScript("localStorage.clear();");
            driver.get("chrome://settings/clearBrowserData");
            driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
