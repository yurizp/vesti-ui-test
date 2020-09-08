package mobi.vesti.pageobjects;

import lombok.Getter;
import mobi.vesti.properties.CarrinhoProperties;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class CarrinhoPageObject {

    public TamanhoP tamanhoP;
    public TamanhoM tamanhoM;
    public TamanhoG tamanhoG;
    public TamanhoGG tamanhoGG;
    @FindBy(xpath = "//*[@id=\"navbar-cart-button\"]/i")
    public WebElement carrinhoIcone;
    @FindBy(xpath = "//cart-panel/div/div[2]/div[3]/div/button")
    public WebElement botaoFinalizarPedido;
    @FindBy(xpath = "//catalogue/success-panel/div/div/div[2]/h2")
    public WebElement tituloMensagemPedidoEnviado;
    @FindBy(xpath = "//catalogue/success-panel/div/div/div[3]/p[1]")
    public WebElement descricaoMensgemPedidoEnviado;

    public CarrinhoPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        tamanhoP = new TamanhoP(driver);
        tamanhoM = new TamanhoM(driver);
        tamanhoG = new TamanhoG(driver);
        tamanhoGG = new TamanhoGG(driver);
    }

    public void validaMensagemDePedidoEnviado() {
        String titulo = tituloMensagemPedidoEnviado.getText();
        String descricao = descricaoMensgemPedidoEnviado.getText();
        Assert.assertEquals(StringUtils.trim(titulo), CarrinhoProperties.TITULO_MENSAGEM_PEDIDO_ENVIADO);
        Assert.assertEquals(StringUtils.trim(descricao), CarrinhoProperties.DESCRICAO_MENSAGEM_PEDIDO_ENVIADO);
    }

    @Getter
    public class TamanhoP {
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[2]/td[2]/button")
        public WebElement rosa;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[3]/td[2]/button")
        public WebElement verde;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[4]/td[2]/button")
        public WebElement azul;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[5]/td[2]/button")
        public WebElement cinza;

        public TamanhoP(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoM {
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[2]/td[3]/button")
        public WebElement rosa;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[3]/td[3]/button")
        public WebElement verde;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[4]/td[3]/button")
        public WebElement azul;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[5]/td[3]/button")
        public WebElement cinza;

        public TamanhoM(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoG {
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[2]/td[4]/button")
        public WebElement rosa;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[3]/td[4]/button")
        public WebElement verde;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[4]/td[4]/button")
        public WebElement azul;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[5]/td[4]/button")
        public WebElement cinza;

        public TamanhoG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoGG {
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[2]/td[5]/button")
        public WebElement rosa;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[3]/td[5]/button")
        public WebElement verde;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[4]/td[5]/button")
        public WebElement azul;
        @FindBy(xpath = "//item-grid/div/table/tbody/tr[5]/td[5]/button")
        public WebElement cinza;

        public TamanhoGG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }
}
