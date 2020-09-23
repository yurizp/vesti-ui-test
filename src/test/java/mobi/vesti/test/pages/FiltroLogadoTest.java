package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.FiltroPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosProperties;
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
        loginPage.logar();
        Thread.sleep(2000);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("blusa"), ProdutosProperties.BLUSA.COM_PRECO);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("camiseta"), ProdutosProperties.CAMISETA_ESTAMPADA.COM_PRECO, ProdutosProperties.CAMISETA.COM_PRECO, ProdutosProperties.CAMISETA_MANGA_LON.COM_PRECO);
        selecionarEValidarFiltrosTelaHome(filtroPageObject.categorias("vestido"), ProdutosProperties.VESTIDO_LONGO.COM_PRECO);
    }

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarTelaDeFiltro() throws InterruptedException {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        Thread.sleep(2000);
        loginPage.logar();
        Thread.sleep(2000);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("vestido").click();
        filtroPageObject.adicionarFiltros.categorias("blusa").click();
        filtroPageObject.adicionarFiltros.categorias("calça jeans").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        List<ProdutosDto> produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosProperties.PACK_JEANS.COM_PRECO,
                        ProdutosProperties.JAQUETA.COM_PRECO,
                        ProdutosProperties.VESTIDO_LONGO.COM_PRECO,
                        ProdutosProperties.BLUSA.COM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.categoriasDto().forEach(categoriaDto -> categoriaDto.fechar());
        List<ProdutosDto> produtosHome = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtosHome.toArray()).containsAnyOf(ProdutosProperties.PRODUTOS_HOME_COM_PRECO.toArray()).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

    /**
     * Esse teste esta ignorado devido ao bug encontrado nele.
     * Aguardando ok para retestar.
     */
    @Ignore
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarTelaDeFiltroClicandoNoMenuHamburguer() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);
        filtroPageObject.adicionarFiltros.menuPageObject.botaoHamburguer.click();
        filtroPageObject.adicionarFiltros.categorias("camiseta").click();
        Thread.sleep(1000);
        filtroPageObject.adicionarFiltros.categorias("básica").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("básica")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isFalse();
        List<ProdutosDto> produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos.toArray())
                .containsOnly(ProdutosProperties.CAMISETA.COM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("estampada").click();
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("básica")).isTrue();
        assertThat(filtroPageObject.existeCategoria("estampada")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isFalse();
        produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos.toArray())
                .containsExactlyInAnyOrder(ProdutosProperties.CAMISETA.COM_PRECO,
                        ProdutosProperties.CAMISETA_ESTAMPADA.COM_PRECO)
                .withFailMessage(MensgensProperties.FILTRO_ERRO_AO_FILTRAR);
        filtroPageObject.adicionarFiltros.botaoAdicionar.click();
        filtroPageObject.adicionarFiltros.categorias("jaqueta").click();
        assertThat(filtroPageObject.adicionarFiltros.existeCategorias("estampada")).isFalse()
                .withFailMessage(MensgensProperties.FILTRO_CATEGORIA_NAO_DEVERIA_EXISTIR);
        assertThat(filtroPageObject.adicionarFiltros.existeCategorias("básica")).isFalse()
                .withFailMessage(MensgensProperties.FILTRO_CATEGORIA_NAO_DEVERIA_EXISTIR);
        filtroPageObject.adicionarFiltros.botaoVoltar.click();
        assertThat(filtroPageObject.existeCategoria("jaqueta")).isTrue();
        assertThat(filtroPageObject.existeCategoria("camiseta")).isTrue();
        assertThat(filtroPageObject.existeCategoria("básica")).isFalse();
        assertThat(filtroPageObject.existeCategoria("estampada")).isFalse();
        produtos = homePage.getAnunciosComPrecoProdutosDto();
        assertThat(produtos.toArray())
                .containsAnyOf(ProdutosProperties.CAMISETA.COM_PRECO,
                        ProdutosProperties.JAQUETA.COM_PRECO,
                        ProdutosProperties.CAMISETA_ESTAMPADA.COM_PRECO)
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
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosProperties.PRODUTOS_HOME_COM_PRECO.toArray()).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

}
