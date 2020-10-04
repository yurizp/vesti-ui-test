package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import mobi.vesti.utils.RetentarUmaVez;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeTest extends TestContext {

    public HomePageObject homePage;
    private CadastroVendedorPageObject cadastroVendedorPage;
    public LoginPageObject loginPage;

    @BeforeClass
    public void before() {
        homePage = new HomePageObject(driver);
        cadastroVendedorPage = new CadastroVendedorPageObject(driver);
        loginPage = new LoginPageObject(driver);
    }

    /**
     * Deve validar que ao entrar na plataforma os produtos exibidos não estão com preço.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validaSeAnunciosEstaoSemPreco() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        List<ProdutosDto> produtosHome = homePage.getAnunciosSemPecoProdutosDto();
        System.out.println(produtosHome.toString());;
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosQaModasProperties.PRODUTOS_HOME_SEM_PRECO.toArray())
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

    /**
     * Deve validar o login utilizando E-mail.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarLoginComEmail() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);

        // Faz clica em um produto
        homePage.clicarEmAnuncioDeProdutoSemPreco();

        // Preenche os dados da tela de login utilizando o email
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO_EMAIL_QAMODAS.getEmail());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);

        // Valida que esta sendo exibido os produtos com os preço
        homePage.validarTituloEPrecoDeProdutosQaModas();
    }


    /**
     * Deve validar a exibição do banner para download do aplicativo
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarBannerDoAplicativo() {
        driver.navigate().to(ConfiguracoesGlobais.NAMIE);
        Thread.sleep(1000);

        // Validar exibição do banner do aplicativo
        validarExibicaoBannerAplicativo();

        // Rolar pagina para baixo
        AcoesCustomizadas.rolarPaginaParaBaixo();
        AcoesCustomizadas.rolarPaginaParaBaixo();
        AcoesCustomizadas.rolarPaginaParaBaixo();

        // Validar a não exibição do banner do aplicativo
        validarNaoExibicaoBannerAplicativo();

        // Rolar pagina para baixo
        AcoesCustomizadas.rolarPaginaParaCima();

        // Validar a não exibição do banner do aplicativo
        validarExibicaoBannerAplicativo();
    }

    @SneakyThrows
    private void validarNaoExibicaoBannerAplicativo() {
        Thread.sleep(1000);
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.botaoBaixar)).isFalse();
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.botaoFechar)).isFalse();
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.descricao)).isFalse();
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.icone)).isFalse();
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.titulo)).isFalse();
    }

    @SneakyThrows
    private void validarExibicaoBannerAplicativo() {
        Thread.sleep(1000);
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.botaoBaixar)).isTrue();
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.botaoFechar)).isTrue();
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.descricao)).isTrue();
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.icone)).isTrue();
        assertThat(AcoesCustomizadas.elementoExiste(homePage.bannerAplicativo.titulo)).isTrue();
    }
}
