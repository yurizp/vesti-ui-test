package mobi.vesti.pageobjects.detalhe;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CalcaJeansMilandaDetalhePage {

    @FindBy(xpath = "//item-grid/div/div[2]/div/button[2]")
    public WebElement quantidade;
    @FindBy(xpath = "//item-grid/div/div[2]/div/button[3]")
    public WebElement botaoAdicionarItem;
    @FindBy(xpath = "//item-grid/div/div[2]/div/button[1]")
    public WebElement botaoRemoverItem;

    public CalcaJeansMilandaDetalhePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
