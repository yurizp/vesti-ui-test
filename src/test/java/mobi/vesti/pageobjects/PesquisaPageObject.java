package mobi.vesti.pageobjects;

import mobi.vesti.utils.AcoesCustomizadas;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PesquisaPageObject {

    @FindBy(xpath = "//*[@id=\"catalogue-navbar\"]/div/div[3]")
    public WebElement botaoLupa;

    @FindBy(xpath = "//*[@id=\"key\"]")
    public WebElement caixaDePesquisa;

    @FindBy(xpath = "//*[@id=\"searchForm\"]/button")
    public WebElement botaoVoltar;

    @FindBy(xpath = "//*[@class=\"icon icon-limpar\"]")
    public WebElement botaoFechar;

    public PesquisaPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void botaoFecharClick() {
        AcoesCustomizadas.clicarViaJavaScript(botaoFechar);
    }
}
