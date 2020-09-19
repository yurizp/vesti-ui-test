package mobi.vesti.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetentarUmaVez implements IRetryAnalyzer {

    int counter = 0;
    int retryLimit = 1;

    @Override
    public boolean retry(ITestResult result) {

        if (counter < retryLimit) {
            counter++;
            return true;
        }
        return false;
    }
}