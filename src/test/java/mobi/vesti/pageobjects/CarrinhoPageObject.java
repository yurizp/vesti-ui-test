package mobi.vesti.pageobjects;

import mobi.vesti.pageobjects.detalhe.CamisetaDetalhePage;
import mobi.vesti.pageobjects.detalhe.PoloDetalhePage;
import mobi.vesti.properties.CarrinhoProperties;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CarrinhoPageObject {

    public PoloDetalhePage polo;
    public CamisetaDetalhePage camiseta;
    @FindBy(xpath = "//*[@id=\"navbar-cart-button\"]/i")
    public WebElement carrinhoIcone;
    @FindBy(xpath = "//cart-panel/div/div[2]/div[3]/div/button")
    public WebElement botaoFinalizarPedido;
    @FindBy(xpath = "//catalogue/success-panel/div/div/div[2]/h2")
    public WebElement tituloMensagemPedidoEnviado;
    @FindBy(xpath = "//catalogue/success-panel/div/div/div[3]/p[1]")
    public WebElement descricaoMensgemPedidoEnviado;
    @FindBy(xpath = "//item-grid/div/div")
    public WebElement mensagemMaximoDisponivel;
    @FindBy(xpath = "/html/body/app/catalogue/success-panel/div/nav/div/button")
    public WebElement botaoVoltar;

    public CarrinhoPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        polo = new PoloDetalhePage(driver);
        camiseta = new CamisetaDetalhePage(driver);
    }

    public void validaMensagemDePedidoEnviado() {
        String titulo = tituloMensagemPedidoEnviado.getText();
        String descricao = descricaoMensgemPedidoEnviado.getText();
        Assert.assertEquals(StringUtils.trim(titulo), CarrinhoProperties.TITULO_MENSAGEM_PEDIDO_ENVIADO);
        Assert.assertEquals(StringUtils.trim(descricao), CarrinhoProperties.DESCRICAO_MENSAGEM_PEDIDO_ENVIADO);
    }

}
