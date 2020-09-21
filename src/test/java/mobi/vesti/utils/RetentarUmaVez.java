package mobi.vesti.utils;

import lombok.extern.slf4j.Slf4j;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

@Slf4j
public class RetentarUmaVez implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = 1;

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