package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.pageobjects.PaginaProdutoPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

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
    @Test(retryAnalyzer = mobi.vesti.utils.RetryAnalyzer.class)
    public void telaProdutoVerificarFotoEstampaClientePossuiCadastro() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        validarProdutosSemPrecoNaHome();
        loginPageObject.logar();
        Thread.sleep(3000);
        validarProdutosComPrecoNaHome();
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        assertThat(produtoPageObject.descricao.getText()).isEqualTo(ProdutosProperties.CAMISETA.COM_PRECO.getDescricao());
        assertThat(produtoPageObject.titulo.getText()).isEqualTo(ProdutosProperties.CAMISETA.COM_PRECO.getTitulo());
        produtoPageObject.imagem.click();
        produtoPageObject.botaoFechar.click();
        produtoPageObject.botaoVoltar.click();
        validarProdutosComPrecoNaHome();
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        homePage.buscarAnuncioDeProduto("VESTIDO LONGO").click();
        assertThat(produtoPageObject.descricao.getText()).isEqualTo(ProdutosProperties.VESTIDO_LONGO.COM_PRECO.getDescricao());
        assertThat(produtoPageObject.titulo.getText()).isEqualTo(ProdutosProperties.VESTIDO_LONGO.COM_PRECO.getTitulo());
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
                .containsAnyOf(ProdutosProperties.PACK_JEANS.SEM_PRECO,
                        ProdutosProperties.CALCA_JEANS_PACK.SEM_PRECO,
                        ProdutosProperties.JAQUETA.SEM_PRECO,
                        ProdutosProperties.VESTIDO_LONGO.SEM_PRECO,
                        ProdutosProperties.BLUSA.SEM_PRECO)
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }


    private void validarProdutosComPrecoNaHome() {
        assertThat(homePage.getAnunciosComPrecoProdutosDto().toArray())
                .containsAnyOf(ProdutosProperties.PACK_JEANS.COM_PRECO,
                        ProdutosProperties.CALCA_JEANS_PACK.COM_PRECO,
                        ProdutosProperties.JAQUETA.COM_PRECO,
                        ProdutosProperties.VESTIDO_LONGO.COM_PRECO,
                        ProdutosProperties.BLUSA.COM_PRECO)
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

}
