package mobi.vesti.pageobjects.detalhe;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PackJeansDetalhePage {

    @FindBy(xpath = "//item-grid/div/div[2]/div/button[2]")
    public WebElement quantidade;
    @FindBy(xpath = "//item-grid/div/div[2]/div/button[3]")
    public WebElement botaoAdicionarItem;
    @FindBy(xpath = "//item-grid/div/div[2]/div/button[1]")
    public WebElement botaoRemoverItem;

    @FindBy(xpath = "//catalogue/color-zoom-box/div[1]/div/h3")
    public WebElement tituloCorZoom;

    public PackJeansDetalhePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }
}
