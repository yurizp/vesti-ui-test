package mobi.vesti.pageobjects;

import lombok.Getter;
import mobi.vesti.pageobjects.detalhe.CamisetaDetalhePage;
import mobi.vesti.pageobjects.detalhe.VestidoLongoDetalhePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Getter
public class PaginaProdutoPageObject {

    @FindBy(xpath = "//swiper-slide/div/img")
    public WebElement imagem;
    @FindBy(xpath = "//*[@id=\"catalogue-navbar\"]/div/button[1]")
    public WebElement botaoVoltar;
    @FindBy(xpath = "//*[@class=\"col-xs-12\"]/h2")
    public WebElement titulo;
    @FindBy(xpath = "//div/h3/span")
    public WebElement valor;
    @FindBy(xpath = "//div/p[2]")
    public WebElement descricao;
    @FindBy(xpath = "/html/body/app/catalogue/zoom-box/div/nav/button")
    public WebElement botaoFechar;
    public CamisetaDetalhePage camiseta;
    public VestidoLongoDetalhePage vestidoLongo;


    public PaginaProdutoPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        camiseta = new CamisetaDetalhePage(driver);
        vestidoLongo = new VestidoLongoDetalhePage(driver);
    }

}
