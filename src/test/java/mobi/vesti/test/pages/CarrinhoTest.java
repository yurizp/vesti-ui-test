package mobi.vesti.test.pages;

import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.CarrinhoPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class CarrinhoTest extends TestContext {

    private HomePageObject homePage;
    private CadastroVendedorPageObject cadastroVendedorPage;
    private CarrinhoPageObject carrinhoPage;
    private LoginPageObject loginPage;

    @BeforeClass
    public void before() {
        homePage = PageFactory.initElements(driver, HomePageObject.class);
        loginPage = PageFactory.initElements(driver, LoginPageObject.class);
        carrinhoPage = new CarrinhoPageObject(driver);
        cadastroVendedorPage = PageFactory.initElements(driver, CadastroVendedorPageObject.class);
    }

    @Test
    public void testarAdicionarERemoverProdutosDoCarrinho() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        homePage.clicarEmAnuncioDeProdutoSemPreco("POLO");
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("POLO");
        carrinhoPage.tamanhoP.verde.click();
        carrinhoPage.tamanhoP.azul.click();
        carrinhoPage.tamanhoG.rosa.click();
        carrinhoPage.tamanhoG.rosa.click();
        carrinhoPage.carrinhoIcone.click();
        carrinhoPage.tamanhoGG.cinza.click();
        Thread.sleep(2000);
        AcoesCustomizadas.clicarEManterPressionado(carrinhoPage.tamanhoG.rosa);
        AcoesCustomizadas.focarNoElemento(carrinhoPage.tamanhoG.azul);
        carrinhoPage.tamanhoG.rosa.click();
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(2000);
        carrinhoPage.validaMensagemDePedidoEnviado();
    }
}
