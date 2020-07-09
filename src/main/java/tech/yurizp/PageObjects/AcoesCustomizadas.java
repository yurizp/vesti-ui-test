package tech.yurizp.PageObjects;

import org.openqa.selenium.WebElement;

public abstract class AcoesCustomizadas {

    public void sendKeys(String text, WebElement webElement) throws InterruptedException {
        for (char c:  text.toCharArray()) {
            webElement.sendKeys(String.valueOf(c));
            Thread.sleep(80);
        }
    }
}
