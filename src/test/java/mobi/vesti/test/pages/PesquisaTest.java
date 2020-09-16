package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.pageobjects.PesquisaPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosProperties;
import mobi.vesti.test.TestContext;
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
    @Test
    public void testarPesquisa() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        Thread.sleep(1000);
        validarProdutosExistentesNaHomeSemPreco();
        pesquisa.botaoLupa.click();
        pesquisa.botaoVoltar.click();
        validarProdutosExistentesNaHomeSemPreco();
        pesquisa.botaoLupa.click();
        pesquisa.caixaDePesquisa.sendKeys("aa");
        validarProdutosExistentesNaHomeSemPreco();
        pesquisa.botaoFecharClick();
        pesquisarProdutosSemPreco("olo", ProdutosProperties.POLO.SEM_PRECO);
        pesquisarProdutosSemPreco("eta", ProdutosProperties.JAQUETA.SEM_PRECO, ProdutosProperties.CAMISETA_ESTAMPADA.SEM_PRECO, ProdutosProperties.CAMISETA.SEM_PRECO);
        pesquisarProdutosSemPreco("vestido", ProdutosProperties.VESTIDO_LONGO.SEM_PRECO);
    }

    @SneakyThrows
    @Test
    public void testarPesquisaLogado() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        login.logar();
        Thread.sleep(3000);
        validarProdutosExistentesNaHomeComPreco();
        pesquisa.botaoLupa.click();
        pesquisa.botaoVoltar.click();
        validarProdutosExistentesNaHomeComPreco();
        pesquisa.botaoLupa.click();
        pesquisa.caixaDePesquisa.sendKeys("aa");
        validarProdutosExistentesNaHomeComPreco();
        pesquisa.botaoFecharClick();
        pesquisarProdutosComPreco("olo", ProdutosProperties.POLO.COM_PRECO);
        pesquisarProdutosComPreco("eta", ProdutosProperties.JAQUETA.COM_PRECO, ProdutosProperties.CAMISETA_ESTAMPADA.COM_PRECO, ProdutosProperties.CAMISETA.COM_PRECO);
        pesquisarProdutosComPreco("vestido", ProdutosProperties.VESTIDO_LONGO.COM_PRECO);
    }

    private void pesquisarProdutosSemPreco(String textoPesquisa, ProdutosDto... produtosDto) throws InterruptedException {
        pesquisa.caixaDePesquisa.sendKeys(textoPesquisa);
        Thread.sleep(1000);
        List<ProdutosDto> produtosHome = home.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(produtosDto).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
        pesquisa.botaoFecharClick();
        Thread.sleep(1000);
    }

    private void pesquisarProdutosComPreco(String textoPesquisa, ProdutosDto... produtosDto) throws InterruptedException {
        pesquisa.caixaDePesquisa.sendKeys(textoPesquisa);
        Thread.sleep(1000);
        List<ProdutosDto> produtosHome = home.getAnunciosComPrecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(produtosDto).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
        pesquisa.botaoFecharClick();
        Thread.sleep(1000);
    }

    private void validarProdutosExistentesNaHomeSemPreco() {
        List<ProdutosDto> produtosHome = home.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosProperties.PRODUTOS_HOME_SEM_PRECO.toArray())
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

    private void validarProdutosExistentesNaHomeComPreco() {
        List<ProdutosDto> produtosHome = home.getAnunciosComPrecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosProperties.PRODUTOS_HOME_COM_PRECO.toArray())
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }
}
