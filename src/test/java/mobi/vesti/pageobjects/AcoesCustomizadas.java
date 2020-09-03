package mobi.vesti.pageobjects;

import mobi.vesti.test.TestContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

public class AcoesCustomizadas {

    public static void sendKeys(String text, WebElement webElement) throws InterruptedException {
        for (char c:  text.toCharArray()) {
            webElement.sendKeys(String.valueOf(c));
            Thread.sleep(80);
        }
    }

    public static void focarNoElemento(WebElement element) {
        element.sendKeys(Keys.SHIFT);
        Actions actions = new Actions(TestContext.driver);
        actions.moveToElement(element);
        actions.perform();
    }
}
