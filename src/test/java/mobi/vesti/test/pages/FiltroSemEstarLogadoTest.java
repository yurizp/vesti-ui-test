package mobi.vesti.test.pages;

import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.FiltroPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.RetentarUmaVez;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
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

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarFiltroDaHome() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("blusa"), ProdutosQaModasProperties.BLUSA.SEM_PRECO);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("camiseta"),
                ProdutosQaModasProperties.CAMISETA_ESTAMPADA.SEM_PRECO,
                ProdutosQaModasProperties.CAMISETA_MANGA_LON.SEM_PRECO,
                ProdutosQaModasProperties.CAMISETA.SEM_PRECO);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("vestido"), ProdutosQaModasProperties.VESTIDO_LONGO.SEM_PRECO);
    }

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarTelaDeFiltro() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        Thread.sleep(2000);
        filtroPageObject.adicionarFiltros.categorias("vestido").click();
        filtroPageObject.adicionarFiltros.categorias("blusa").click();
        filtroPageObject.adicionarFiltros.categorias("calça jeans").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        Thread.sleep(1000);
        List<ProdutosDto> produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosQaModasProperties.PACK_JEANS.SEM_PRECO,
                        ProdutosQaModasProperties.JAQUETA.SEM_PRECO,
                        ProdutosQaModasProperties.VESTIDO_LONGO.SEM_PRECO,
                        ProdutosQaModasProperties.BLUSA.SEM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.categoriasDto().forEach(categoriaDto -> categoriaDto.fechar());
        List<ProdutosDto> produtosHome = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome.toArray()).containsAnyOf(ProdutosQaModasProperties.PRODUTOS_HOME_SEM_PRECO.toArray()).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarTelaDeFiltroClicandoNoMenuHamburguer() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        filtroPageObject.adicionarFiltros.menuPageObject.botaoHamburguer.click();
        filtroPageObject.adicionarFiltros.categorias("camiseta").click();
        filtroPageObject.adicionarFiltros.categorias("básica").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("básica")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isFalse();
        List<ProdutosDto> produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos.toArray())
                .containsOnly(ProdutosQaModasProperties.CAMISETA.SEM_PRECO, ProdutosQaModasProperties.CAMISETA_MANGA_LON.SEM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("estampada").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("básica")).isTrue();
        assertThat(filtroPageObject.existeCategoria("estampada")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isFalse();
        produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos.toArray())
                .containsExactlyInAnyOrder(ProdutosQaModasProperties.CAMISETA.SEM_PRECO,
                        ProdutosQaModasProperties.CAMISETA_MANGA_LON.SEM_PRECO,
                        ProdutosQaModasProperties.CAMISETA_ESTAMPADA.SEM_PRECO)
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
                .containsAnyOf(ProdutosQaModasProperties.CAMISETA.SEM_PRECO,
                        ProdutosQaModasProperties.JAQUETA.SEM_PRECO,
                        ProdutosQaModasProperties.CAMISETA_ESTAMPADA.SEM_PRECO)
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
        produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosQaModasProperties.BLUSA.SEM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
    }

    public void selecionarEValidarFiltrosTelaHome(WebElement filtro, ProdutosDto... anunciosVendasDto) throws InterruptedException {
        filtro.click();
        Thread.sleep(1000);
        List<ProdutosDto> produtos = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtos).containsExactlyInAnyOrder(anunciosVendasDto).withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtro.click();
        List<ProdutosDto> produtosHome = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome.toArray()).containsAnyOf(ProdutosQaModasProperties.PRODUTOS_HOME_SEM_PRECO.toArray()).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

}
