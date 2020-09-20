package mobi.vesti.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MenuPageObject {

    @FindBy(xpath = "//*[@id=\"navbar-filter-button\"]")
    public WebElement botaoHamburguer;

    @FindBy(xpath = "/html/body/app/catalogue/filter-panel/div/div[2]/p[1]")
    public WebElement botaoInformacoesUteis;

    @FindBy(xpath = "/html/body/app/catalogue/filter-panel/div/div[2]/p[2]")
    public WebElement botaoSair;

    public MenuPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
