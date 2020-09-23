package mobi.vesti.pageobjects;

import mobi.vesti.pageobjects.detalhe.BlusaDetalhePage;
import mobi.vesti.pageobjects.detalhe.CalcaJeansMilandaDetalhePage;
import mobi.vesti.pageobjects.detalhe.CalcaJeansPackDetalhePage;
import mobi.vesti.pageobjects.detalhe.CamisetaDetalhePage;
import mobi.vesti.pageobjects.detalhe.CamisetaEstampadaDetalhe;
import mobi.vesti.pageobjects.detalhe.CamisetaMangaLongDetalhePage;
import mobi.vesti.pageobjects.detalhe.PackJeansDetalhePage;
import mobi.vesti.pageobjects.detalhe.PoloDetalhePage;
import mobi.vesti.pageobjects.detalhe.ShortDetalhePage;
import mobi.vesti.pageobjects.detalhe.VestidoLongoDetalhePage;
import mobi.vesti.properties.CarrinhoProperties;
import mobi.vesti.properties.ProdutosProperties;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CarrinhoPageObject {

    @FindBy(xpath = "//*[@id=\"navbar-cart-button\"]/i")
    public WebElement carrinhoIcone;
    @FindBy(xpath = "//*[@id=\"navbar-cart-button\"]/span")
    public WebElement carrinhoQuantidadeItens;
    @FindBy(xpath = "//*[@class=\"cart-summary-container container-fluid\"]//button")
    public WebElement botaoFinalizarPedido;
    @FindBy(xpath = "//catalogue/success-panel/div/div/div[2]/h2")
    public WebElement tituloMensagemPedidoEnviado;
    @FindBy(xpath = "//catalogue/success-panel/div/div/div[3]/p[1]")
    public WebElement descricaoMensgemPedidoEnviado;
    @FindBy(xpath = "//item-grid/div/div")
    public WebElement mensagemMaximoDisponivel;
    @FindBy(xpath = "//*[@class=\"icon icon-arrow-left\"]")
    public WebElement botaoVoltar;
    @FindBy(xpath = "//*[@class=\"alert alert-vesti show\"]")
    public WebElement mensagemCarrinhoAtualizado;
    @FindBy(xpath = "//*[@class=\"btn-block btn btn-vesti\"]")
    public WebElement botaoContinuarComprando;
    @FindBy(xpath = "//*[@class=\"cart-summary-item\"]//span")
    public WebElement totalItens;
    @FindBy(xpath = "//*[@class=\"seller-name\"]")
    public WebElement nomeVendedor;
    @FindBy(xpath = "//*[@class=\"seller-phone\"]")
    public WebElement telefoneVendedor;
    @FindBy(xpath = "//*[@class=\"seller-initials\"]")
    public WebElement iniciaisVendedor;

    public PecasEsgotadas popUpMensagem;
    public PoloDetalhePage polo;
    public ShortDetalhePage shorts;
    public VestidoLongoDetalhePage vestidoLongo;
    public CamisetaDetalhePage camiseta;
    public CalcaJeansMilandaDetalhePage calcaJeansMilanda;
    public CalcaJeansPackDetalhePage calcaJeansPack;
    public PackJeansDetalhePage packJeans;
    public BlusaDetalhePage blusa;
    public CamisetaMangaLongDetalhePage camisetaMangaLong;
    public CamisetaEstampadaDetalhe camisetaEstampada;

    public CarrinhoPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        polo = new PoloDetalhePage(driver);
        camiseta = new CamisetaDetalhePage(driver);
        calcaJeansMilanda = new CalcaJeansMilandaDetalhePage(driver);
        vestidoLongo = new VestidoLongoDetalhePage(driver);
        blusa = new BlusaDetalhePage(driver);
        packJeans = new PackJeansDetalhePage(driver);
        shorts = new ShortDetalhePage(driver);
        popUpMensagem = new PecasEsgotadas(driver);
        calcaJeansPack = new CalcaJeansPackDetalhePage(driver);
        camisetaMangaLong = new CamisetaMangaLongDetalhePage(driver);
        camisetaEstampada = new CamisetaEstampadaDetalhe(driver);
    }

    /**
     * Valida a mensagem de sucesso para um pedido enviado.
     */
    public void validaMensagemDePedidoEnviado() {
        String titulo = tituloMensagemPedidoEnviado.getText();
        String descricao = descricaoMensgemPedidoEnviado.getText();
        Assert.assertEquals(StringUtils.trim(titulo), CarrinhoProperties.TITULO_MENSAGEM_PEDIDO_ENVIADO);
        Assert.assertEquals(StringUtils.trim(descricao), CarrinhoProperties.DESCRICAO_MENSAGEM_PEDIDO_ENVIADO);
    }

    public class PecasEsgotadas {
        @FindBy(xpath = "//prompt-modal/div[2]/div[1]/div/p")
        public WebElement mensagem;
        @FindBy(xpath = "//prompt-modal/div[2]/div[2]/button")
        public WebElement botaoOk;

        public PecasEsgotadas(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

}
