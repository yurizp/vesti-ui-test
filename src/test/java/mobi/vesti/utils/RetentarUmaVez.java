package mobi.vesti.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mobi.vesti.test.TestContext;
import org.openqa.selenium.WebDriverException;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Slf4j
public class RetentarUmaVez implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = 1;

    @SneakyThrows
    @Override
    public boolean retry(ITestResult result) {
        if (counter < retryLimit) {
            counter++;
            log.error(result.getTestName(), result.getThrowable());
            return true;
        }
        return false;
    }
}