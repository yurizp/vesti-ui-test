package mobi.vesti.test.pages;

import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.FiltroPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosProperties;
import mobi.vesti.test.TestContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FiltroSemEstarLogadoTest extends TestContext {

    public HomePageObject homePage;
    public FiltroPageObject filtroPageObject;

    @BeforeClass
    public void before() {
        homePage = PageFactory.initElements(driver, HomePageObject.class);
        filtroPageObject = new FiltroPageObject(driver);
    }

    @Test(retryAnalyzer = mobi.vesti.utils.RetryAnalyzer.class)
    public void testarFiltroDaHome() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("blusa"), ProdutosProperties.BLUSA_SEM_PRECO);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("camiseta"), ProdutosProperties.CAMISETA_ESTAMPADA_SEM_PRECO, ProdutosProperties.CAMISETA_SEM_PRECO);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("vestido"), ProdutosProperties.VESTIDO_LONGO_SEM_PRECO);
    }

    @Test(retryAnalyzer = mobi.vesti.utils.RetryAnalyzer.class)
    public void testarTelaDeFiltro() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("vestido").click();
        filtroPageObject.adicionarFiltros.categorias("blusa").click();
        filtroPageObject.adicionarFiltros.categorias("calça jeans").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        List<ProdutosDto> produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosProperties.PACK_JEANS_SEM_PRECO,
                        ProdutosProperties.CALCA_JEANS_PACK_SEM_PRECO,
                        ProdutosProperties.JAQUETA_SEM_PRECO,
                        ProdutosProperties.VESTIDO_LONGO_SEM_PRECO,
                        ProdutosProperties.BLUSA_SEM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.categoriasDto().forEach(categoriaDto -> categoriaDto.fechar());
        List<ProdutosDto> produtosHome = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome.toArray()).containsAnyOf(ProdutosProperties.PRODUTOS_HOME_SEM_PRECO.toArray()).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

    @Test(retryAnalyzer = mobi.vesti.utils.RetryAnalyzer.class)
    public void testarTelaDeFiltroClicandoNoMenuHamburguer() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        filtroPageObject.adicionarFiltros.botaoMenuHamburguer.click();
        filtroPageObject.adicionarFiltros.categorias("camiseta").click();
        filtroPageObject.adicionarFiltros.categorias("básica").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("básica")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isFalse();
        List<ProdutosDto> produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos.toArray())
                .containsOnly(ProdutosProperties.CAMISETA_SEM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("estampada").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("básica")).isTrue();
        assertThat(filtroPageObject.existeCategoria("estampada")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isFalse();
        produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos.toArray())
                .containsExactlyInAnyOrder(ProdutosProperties.CAMISETA_SEM_PRECO,
                        ProdutosProperties.CAMISETA_ESTAMPADA_SEM_PRECO)
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
        produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosProperties.CAMISETA_SEM_PRECO,
                        ProdutosProperties.JAQUETA_SEM_PRECO,
                        ProdutosProperties.CAMISETA_ESTAMPADA_SEM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
    }

    public void selecionarEValidarFiltrosTelaHome(WebElement filtro, ProdutosDto... anunciosVendasDto) throws InterruptedException {
        filtro.click();
        List<ProdutosDto> produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos).containsExactlyInAnyOrder(anunciosVendasDto).withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtro.click();
        List<ProdutosDto> produtosHome = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome.toArray()).containsAnyOf(ProdutosProperties.PRODUTOS_HOME_SEM_PRECO.toArray()).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

}
