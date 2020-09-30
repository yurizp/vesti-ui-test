package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.pageobjects.PesquisaPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.RetentarUmaVez;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class PesquisaTest extends TestContext {

    private PesquisaPageObject pesquisa;
    private HomePageObject home;
    private LoginPageObject login;


    @BeforeClass
    public void before() {
        pesquisa = new PesquisaPageObject(driver);
        home = new HomePageObject(driver);
        login = new LoginPageObject(driver);
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarPesquisa() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(1000);
        validarProdutosExistentesNaHomeSemPreco();
        pesquisa.botaoLupa.click();
        pesquisa.botaoVoltar.click();
        validarProdutosExistentesNaHomeSemPreco();
        pesquisa.botaoLupa.click();
        pesquisa.caixaDePesquisa.sendKeys("aa");
        validarProdutosExistentesNaHomeSemPreco();
        pesquisa.botaoFecharClick();
        pesquisarProdutosSemPreco("olo", ProdutosQaModasProperties.POLO.SEM_PRECO);
        pesquisarProdutosSemPreco("eta", ProdutosQaModasProperties.JAQUETA.SEM_PRECO,
                ProdutosQaModasProperties.CAMISETA_ESTAMPADA.SEM_PRECO,
                ProdutosQaModasProperties.CAMISETA_MANGA_LON.SEM_PRECO,
                ProdutosQaModasProperties.CAMISETA.SEM_PRECO);
        pesquisarProdutosSemPreco("vestido", ProdutosQaModasProperties.VESTIDO_LONGO.SEM_PRECO);
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarPesquisaLogado() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        login.logarQaModas();
        Thread.sleep(3000);
        validarProdutosExistentesNaHomeComPreco();
        pesquisa.botaoLupa.click();
        pesquisa.botaoVoltar.click();
        validarProdutosExistentesNaHomeComPreco();
        pesquisa.botaoLupa.click();
        pesquisa.caixaDePesquisa.sendKeys("aa");
        validarProdutosExistentesNaHomeComPreco();
        pesquisa.botaoFecharClick();
        pesquisarProdutosComPreco("olo", ProdutosQaModasProperties.POLO.COM_PRECO);
        pesquisarProdutosComPreco("eta", ProdutosQaModasProperties.JAQUETA.COM_PRECO,
                ProdutosQaModasProperties.CAMISETA.COM_PRECO,
                ProdutosQaModasProperties.CAMISETA_MANGA_LON.COM_PRECO,
                ProdutosQaModasProperties.CAMISETA_ESTAMPADA.COM_PRECO);
        pesquisarProdutosComPreco("vestido", ProdutosQaModasProperties.VESTIDO_LONGO.COM_PRECO);
    }

    private void pesquisarProdutosSemPreco(String textoPesquisa, ProdutosDto... produtosDto) throws InterruptedException {
        pesquisa.caixaDePesquisa.sendKeys(textoPesquisa);
        Thread.sleep(2000);
        List<ProdutosDto> produtosHome = home.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome).containsExactlyInAnyOrder(produtosDto).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
        pesquisa.botaoFecharClick();
        Thread.sleep(2000);
    }

    private void pesquisarProdutosComPreco(String textoPesquisa, ProdutosDto... produtosDto) throws InterruptedException {
        pesquisa.caixaDePesquisa.sendKeys(textoPesquisa);
        Thread.sleep(2000);
        List<ProdutosDto> produtosHome = home.getAnunciosComPrecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(produtosDto).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
        pesquisa.botaoFecharClick();
        Thread.sleep(2000);
    }

    private void validarProdutosExistentesNaHomeSemPreco() {
        List<ProdutosDto> produtosHome = home.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosQaModasProperties.PRODUTOS_HOME_SEM_PRECO.toArray())
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

    private void validarProdutosExistentesNaHomeComPreco() {
        List<ProdutosDto> produtosHome = home.getAnunciosComPrecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosQaModasProperties.PRODUTOS_HOME_COM_PRECO.toArray())
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }
}
