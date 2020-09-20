package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.client.VestClient;
import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.CarrinhoPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.CarrinhoProperties;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.properties.ProdutosProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import mobi.vesti.utils.RetentarUmaVez;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarrinhoTest extends TestContext {

    private HomePageObject homePage;
    private CadastroVendedorPageObject cadastroVendedorPage;
    private CarrinhoPageObject carrinhoPage;
    private LoginPageObject loginPage;

    @BeforeClass
    public void before() {
        homePage = PageFactory.initElements(driver, HomePageObject.class);
        loginPage = new LoginPageObject(driver);
        carrinhoPage = new CarrinhoPageObject(driver);
        cadastroVendedorPage = PageFactory.initElements(driver, CadastroVendedorPageObject.class);
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarAdicionarERemoverProdutosDoCarrinho() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        VestClient.adicionarEstoque(ProdutosProperties.POLO.ID, ProdutosProperties.POLO.ESTOQUE_REQUEST);
        homePage.clicarEmAnuncioDeProdutoSemPreco("POLO");
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("POLO");
        carrinhoPage.polo.tamanhoP.verde.click();
        carrinhoPage.polo.tamanhoP.azul.click();
        carrinhoPage.polo.tamanhoG.rosa.click();
        carrinhoPage.polo.tamanhoG.rosa.click();
        carrinhoPage.carrinhoIcone.click();
        carrinhoPage.polo.tamanhoGG.cinza.click();
        Thread.sleep(2000);
        AcoesCustomizadas.clicarEManterPressionado(carrinhoPage.polo.tamanhoG.rosa);
        AcoesCustomizadas.focarNoElemento(carrinhoPage.polo.tamanhoG.azul);
        carrinhoPage.polo.tamanhoG.rosa.click();
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(2000);
        carrinhoPage.validaMensagemDePedidoEnviado();
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarRealizarPedidoClientePossuiCadastro() {
        VestClient.adicionarEstoque(ProdutosProperties.CAMISETA.ID, ProdutosProperties.CAMISETA.ESTOQUE_REQUEST);
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        homePage.clicarEmAnuncioDeProdutoSemPreco("CAMISETA");
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        carrinhoPage.camiseta.tamanhoP.getBranco().click();
        carrinhoPage.camiseta.tamanhoP.getAmarelo().click();
        carrinhoPage.camiseta.tamanhoM.getAzul().click();
        carrinhoPage.camiseta.tamanhoG.getPreto().click();
        carrinhoPage.camiseta.tamanhoGG.getVinho().click();
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(1000);
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();

    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarProdutoMaximoDisponivel() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        VestClient.adicionarEstoque(ProdutosProperties.CAMISETA.ID, ProdutosProperties.CAMISETA.ESTOQUE_REQUEST);
        homePage.clicarEmAnuncioDeProdutoSemPreco("CAMISETA");
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        validarMensagemDeLimiteAtingido();
        assertThat("3").isEqualTo(carrinhoPage.camiseta.tamanhoGG.getVinho().getText());
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(1000);
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();
        carrinhoPage.botaoVoltar.click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        carrinhoPage.camiseta.tamanhoGG.getVinho().click();
        assertThat(" ").isEqualTo(carrinhoPage.camiseta.tamanhoGG.getVinho().getText());
    }

    private void validarMensagemDeLimiteAtingido() {
        int numeroCliques = 0;
        while (numeroCliques < 10) {
            carrinhoPage.camiseta.tamanhoGG.getVinho().click();
            numeroCliques++;
            String text = carrinhoPage.mensagemMaximoDisponivel.getText();
            if(StringUtils.isNotBlank(text)){
                assertThat(CarrinhoProperties.MENSAGEM_MAXIMO_DISPONIVEL).isEqualTo(text);
                return;
            }
        }
        throw new RuntimeException("Não foi possivel validar a mensagem de limite maximo.");
    }
}
