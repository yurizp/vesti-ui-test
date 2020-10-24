package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.FiltroPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.RetentarUmaVez;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FiltroLogadoTest extends TestContext {

    public HomePageObject homePage;
    public FiltroPageObject filtroPageObject;
    private LoginPageObject loginPage;

    @BeforeClass
    public void before() {
        homePage = PageFactory.initElements(driver, HomePageObject.class);
        filtroPageObject = new FiltroPageObject(driver);
        loginPage = new LoginPageObject(driver);
    }

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarFiltroDaHome() throws InterruptedException {
        loginPage.logarQaModas();
        Thread.sleep(2000);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("blusa"), ProdutosQaModasProperties.BLUSA.COM_PRECO);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("camiseta"), ProdutosQaModasProperties.CAMISETA_ESTAMPADA.COM_PRECO, ProdutosQaModasProperties.CAMISETA.COM_PRECO, ProdutosQaModasProperties.CAMISETA_MANGA_LON.COM_PRECO);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("vestido"), ProdutosQaModasProperties.VESTIDO_LONGO.COM_PRECO);
    }

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarTelaDeFiltro() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        loginPage.logarQaModas();
        Thread.sleep(2000);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("vestido").click();
        filtroPageObject.adicionarFiltros.categorias("blusa").click();
        filtroPageObject.adicionarFiltros.categorias("calça jeans").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        List<ProdutosDto> produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosQaModasProperties.PACK_JEANS.COM_PRECO,
                        ProdutosQaModasProperties.JAQUETA.COM_PRECO,
                        ProdutosQaModasProperties.VESTIDO_LONGO.COM_PRECO,
                        ProdutosQaModasProperties.BLUSA.COM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.categoriasDto().forEach(categoriaDto -> categoriaDto.fechar());
        List<ProdutosDto> produtosHome = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtosHome.toArray()).containsAnyOf(ProdutosQaModasProperties.PRODUTOS_HOME_COM_PRECO.toArray()).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

    /**
     * Esse teste esta ignorado devido ao bug encontrado nele.
     * Aguardando ok para .
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarTelaDeFiltroClicandoNoMenuHamburguer() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        loginPage.logarQaModas();
        Thread.sleep(2000);
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        filtroPageObject.adicionarFiltros.menuPageObject.botaoHamburguer.click();
        filtroPageObject.adicionarFiltros.categorias("camiseta").click();
        Thread.sleep(1000);
        filtroPageObject.adicionarFiltros.categorias("básica").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("básica")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isFalse();
        List<ProdutosDto> produtos = homePage.getAnunciosComPrecoProdutosDto();
        produtos.forEach(produtosDto -> System.out.println(produtosDto.toString()));
        assertThat(produtos.toArray())
                .containsOnly(ProdutosQaModasProperties.CAMISETA.COM_PRECO, ProdutosQaModasProperties.CAMISETA_MANGA_LON.COM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("estampada").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        Thread.sleep(2000);
        assertThat(filtroPageObject.existeCategoria("básica")).isTrue();
        assertThat(filtroPageObject.existeCategoria("estampada")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isFalse();
        produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos.toArray())
                .containsExactlyInAnyOrder(
                        ProdutosQaModasProperties.CAMISETA.COM_PRECO,
                        ProdutosQaModasProperties.CAMISETA_MANGA_LON.COM_PRECO,
                        ProdutosQaModasProperties.CAMISETA_ESTAMPADA.COM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("jaqueta de cou").click();
        assertThat(filtroPageObject.adicionarFiltros.existeCategorias("estampada")).isFalse()
                .withFailMessage(MensgensProperties.FILTRO_CATEGORIA_NAO_DEVERIA_EXISTIR);
        assertThat(filtroPageObject.adicionarFiltros.existeCategorias("básica")).isFalse()
                .withFailMessage(MensgensProperties.FILTRO_CATEGORIA_NAO_DEVERIA_EXISTIR);
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("jaqueta de cou")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isTrue();
        assertThat(filtroPageObject.existeCategoria("básica")).isFalse();
        assertThat(filtroPageObject.existeCategoria("estampada")).isFalse();
        produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosQaModasProperties.CAMISETA.COM_PRECO,
                        ProdutosQaModasProperties.JAQUETA.COM_PRECO,
                        ProdutosQaModasProperties.CAMISETA_ESTAMPADA.COM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        // Limpa os filtros
        filtroPageObject.categorias("jaqueta de cou").click();
        Thread.sleep(1000);
        filtroPageObject.categorias("camiseta").click();
        Thread.sleep(1000);
        //Abrir menu hamburguer
        filtroPageObject.adicionarFiltros.menuPageObject.botaoHamburguer.click();
        Thread.sleep(500);

        // Selecionar as categorias
        filtroPageObject.adicionarFiltros.categorias("blusa").click();
        filtroPageObject.adicionarFiltros.categorias("jaqueta").click();
        filtroPageObject.adicionarFiltros.categorias("algadao").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        Thread.sleep(1000);

        // Validar o filtro aplicado
        assertThat(filtroPageObject.existeCategoria("algadao")).isTrue();
        produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosQaModasProperties.BLUSA.COM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
    }

    public void selecionarEValidarFiltrosTelaHome(WebElement filtro, ProdutosDto... anunciosVendasDto) throws InterruptedException {
        filtro.click();
        Thread.sleep(1000);
        List<ProdutosDto> produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos).containsExactlyInAnyOrder(anunciosVendasDto).withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtro.click();
        Thread.sleep(1000);
        List<ProdutosDto> produtosHome = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosQaModasProperties.PRODUTOS_HOME_COM_PRECO.toArray()).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

}
