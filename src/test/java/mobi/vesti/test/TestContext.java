package mobi.vesti.test;

import lombok.SneakyThrows;
import mobi.vesti.webdriver.builder.RemoteWebDriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;

public class TestContext {

    public static WebDriver driver;

    @BeforeSuite
    public void ini() throws MalformedURLException {
        if(driver == null){
            driver = RemoteWebDriverBuilder.instance();
        }
    }

    @SneakyThrows
    @AfterSuite
    public void close() {
        driver.close();
        driver.quit();
    }

    @AfterMethod
    public void clearCache() {
        try {
            ((RemoteWebDriver) driver).executeScript("localStorage.clear();");
            driver.get("chrome://settings/clearBrowserData");
            driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
            Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
