package mobi.vesti.utils;

import mobi.vesti.test.TestContext;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;

import static mobi.vesti.test.TestContext.driver;

public class AcoesCustomizadas {

    public static void sendKeys(String text, WebElement webElement) throws InterruptedException {
        for (char c : text.toCharArray()) {
            webElement.sendKeys(String.valueOf(c));
            Thread.sleep(80);
        }
    }

    public static void focarNoElemento(WebElement element) {
        element.sendKeys(Keys.SHIFT);
        Actions actions = new Actions(TestContext.driver);
        actions.moveToElement(element);
        element.sendKeys(Keys.SHIFT);
        actions.perform();
    }

    public static void clicarEManterPressionado(WebElement element) {
        Actions action = new Actions(TestContext.driver);
        action.clickAndHold(element).contextClick(element).build().perform();
        action.release();
    }

    public static void clicarViaJavaScript(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click();", element);
    }

    public static boolean elementoExiste(WebElement element) {
        try {
            element.isDisplayed();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
