package mobi.vesti.test;

import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.webdriver.builder.RemoteWebDriverBuilder;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.html5.LocalStorage;
import org.openqa.selenium.html5.WebStorage;
import org.openqa.selenium.remote.RemoteExecuteMethod;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.html5.RemoteWebStorage;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

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

    @BeforeMethod
    public void clearCache() {
        driver.get(ConfiguracoesGlobais.BASE_URL);
        ((RemoteWebDriver) driver).executeScript("localStorage.clear();");
        driver.manage().deleteAllCookies();
        driver.get("chrome://settings/clearBrowserData");
        driver.findElement(By.xpath("//settings-ui")).sendKeys(Keys.ENTER);
        if (driver instanceof WebStorage) {
            WebStorage webStorage = (WebStorage) driver;
            webStorage.getSessionStorage().clear();
            webStorage.getLocalStorage().clear();
            webStorage.getSessionStorage().clear();
        }
        final RemoteExecuteMethod executeMethod = new RemoteExecuteMethod((RemoteWebDriver) driver);
        final RemoteWebStorage webStorage = new RemoteWebStorage(executeMethod);
        final LocalStorage storage = webStorage.getLocalStorage();
        storage.removeItem("sessionState");
        storage.removeItem("localStorage");
    }

    @Override
    protected void finalize() throws Throwable {
        driver.close();
        driver.quit();
    }
}
