package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.pageobjects.PaginaProdutoPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.RetentarUmaVez;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DetalheProdutoTest extends TestContext {

    private PaginaProdutoPageObject produtoPageObject;
    private LoginPageObject loginPageObject;
    private HomePageObject homePage;


    @BeforeClass
    public void before() {
        produtoPageObject = new PaginaProdutoPageObject(driver);
        loginPageObject = new LoginPageObject(driver);
        homePage = new HomePageObject(driver);
    }


    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void telaProdutoVerificarFotoEstampaClientePossuiCadastro() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(3000);
        validarProdutosSemPrecoNaHome();
        loginPageObject.logarQaModas();
        Thread.sleep(3000);
        validarProdutosComPrecoNaHome();
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        assertThat(produtoPageObject.descricao.getText()).isEqualTo(ProdutosQaModasProperties.CAMISETA.COM_PRECO.getDescricao());
        assertThat(produtoPageObject.titulo.getText()).isEqualTo(ProdutosQaModasProperties.CAMISETA.COM_PRECO.getTitulo());
        produtoPageObject.imagem.click();
        produtoPageObject.botaoFechar.click();
        produtoPageObject.botaoVoltar.click();
        validarProdutosComPrecoNaHome();
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        homePage.buscarAnuncioDeProduto("VESTIDO LONGO").click();
        assertThat(produtoPageObject.descricao.getText()).isEqualTo(ProdutosQaModasProperties.VESTIDO_LONGO.COM_PRECO.getDescricao());
        assertThat(produtoPageObject.titulo.getText()).isEqualTo(ProdutosQaModasProperties.VESTIDO_LONGO.COM_PRECO.getTitulo());
        produtoPageObject.vestidoLongo.corRosa.isDisplayed();
        produtoPageObject.vestidoLongo.corVermelha.isDisplayed();
        produtoPageObject.vestidoLongo.corRosa.click();
        assertThat(produtoPageObject.vestidoLongo.tituloCorZoom.getText()).isEqualTo("ROSA");
        produtoPageObject.vestidoLongo.tituloCorZoom.click();
        produtoPageObject.vestidoLongo.corVermelha.click();
        assertThat(produtoPageObject.vestidoLongo.tituloCorZoom.getText()).isEqualTo("VERMELHO");
        produtoPageObject.vestidoLongo.tituloCorZoom.click();
    }

    private void validarProdutosSemPrecoNaHome() {
        assertThat(homePage.getAnunciosSemPecoProdutosDto().toArray())
                .containsAnyOf(ProdutosQaModasProperties.PACK_JEANS.SEM_PRECO,
                        ProdutosQaModasProperties.JAQUETA.SEM_PRECO,
                        ProdutosQaModasProperties.VESTIDO_LONGO.SEM_PRECO,
                        ProdutosQaModasProperties.BLUSA.SEM_PRECO)
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }


    private void validarProdutosComPrecoNaHome() {
        assertThat(homePage.getAnunciosComPrecoProdutosDto().toArray())
                .containsAnyOf(ProdutosQaModasProperties.PACK_JEANS.COM_PRECO,
                        ProdutosQaModasProperties.JAQUETA.COM_PRECO,
                        ProdutosQaModasProperties.VESTIDO_LONGO.COM_PRECO,
                        ProdutosQaModasProperties.BLUSA.COM_PRECO)
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

}
