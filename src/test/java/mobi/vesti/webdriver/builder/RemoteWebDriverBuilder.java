package mobi.vesti.webdriver.builder;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mobi.vesti.webdriver.prototype.AwsSeleniumApiPrototype;
import mobi.vesti.webdriver.prototype.TestingBotPrototype;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
public class RemoteWebDriverBuilder {

    private static final String PREFIXO = "[RemoteWebDriver Builder] - ";
    private static final AwsSeleniumApiPrototype awsSeleniumApiPrototype = new AwsSeleniumApiPrototype();
    private static final String OS = System.getProperty("os.name").toLowerCase();
    private static final String RUNNER = System.getenv("RUNNER");
    private static RemoteWebDriver remoteWebDriver;
    private static TestingBotPrototype testingBotPrototype = new TestingBotPrototype();

    public static WebDriver instance() throws MalformedURLException {
        return init();
    }

    private static RemoteWebDriver init() throws MalformedURLException {
        if (RUNNER != null) {
            log.info("{} Testes marcados para serem executados em Cloud.", PREFIXO);
            remoteWebDriver = cloudRunner();
            log.info("{} Criado o WebDriver para execução em CLOUD.", PREFIXO);
        } else {
            log.info("{} Testes marcados para serem executados localmente.", PREFIXO);
            remoteWebDriver = localRunner();
            log.info("{} Criado o WebDriver para execução LOCAL.", PREFIXO);
        }
        defaultConfigs();
        return remoteWebDriver;
    }

    private static void defaultConfigs() {
        remoteWebDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        remoteWebDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
        remoteWebDriver.manage().timeouts().setScriptTimeout(30, TimeUnit.SECONDS);
    }

    private static RemoteWebDriver cloudRunner() throws MalformedURLException {
        URL seleniumUrl;
        ChromeOptions chromeOptions = getChromeOptions();
        switch (RUNNER.toUpperCase()) {
            case "AWS":
                seleniumUrl = awsSeleniumApiPrototype.URL();
                break;
            case "TESTING_BOT":
                chromeOptions.setCapability("platform", "WIN10");
                seleniumUrl = testingBotPrototype.URL();
                break;
            default:
                throw new MalformedURLException(String.format("%sNão foi possivel identificar o serviço de cloud do runner.", PREFIXO));
        }

        return new RemoteWebDriver(seleniumUrl, chromeOptions);
    }

    @SneakyThrows
    private static ChromeDriver localRunner() {
        if (isWindows()) {
            log.info("{} Sistema operacional Windows.", PREFIXO);
            System.setProperty("webdriver.chrome.driver", new File("src/test/resources/chromedriver.exe").getAbsolutePath());
        }
        if (isUnix()) {
            log.info("{} Sistema operacional Linux.", PREFIXO);
            System.setProperty("webdriver.chrome.driver", new File("src/test/resources/chromedriver").getAbsolutePath());
        }
        return new ChromeDriver(getChromeOptions());
    }

    private static ChromeOptions getChromeOptions() {
        Map<String, String> mobileEmulation = Collections.singletonMap("deviceName", "Nexus 5");
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
        Map<String, Integer> timeouts = new HashMap<>();
        timeouts.put("implicit", 120000);
        chromeOptions.setCapability("timeouts", timeouts);
//        chromeOptions.addArguments("--headless");
        return chromeOptions;
    }

    private static boolean isWindows() {
        return (OS.indexOf("win") >= 0);
    }

    private static boolean isUnix() {
        return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
    }
}