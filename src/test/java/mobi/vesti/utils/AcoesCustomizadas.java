package mobi.vesti.utils;

import lombok.SneakyThrows;
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
        driver.switchTo().window(driver.getWindowHandle());

    }

    @SneakyThrows
    public static void clicarEManterPressionado(WebElement element) {
        new Actions(TestContext.driver)
                .clickAndHold(element)
                .pause(1000)
                .release().build().perform();
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

    /**
     * Clica um numero de vezes definido em um elemento.
     *
     * @param element       Elemento a ser clicado.
     * @param numeroCliques Numero de cliques no elemento.
     */
    @SneakyThrows
    public static void cliarRepetidasVezes(WebElement element, int numeroCliques) {
        for (int i = 1; i <= numeroCliques; i++) {
            element.click();
            Thread.sleep(200);
        }
    }
}
