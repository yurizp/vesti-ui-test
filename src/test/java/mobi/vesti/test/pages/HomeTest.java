package mobi.vesti.test.pages;

import mobi.vesti.dto.AnunciosVendasDto;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.test.TestContext;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.util.RetryAnalyzerCount;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class HomeTest extends TestContext {

    public HomePageObject homePage;

    @BeforeClass
    public void before() {
        homePage = PageFactory.initElements(driver, HomePageObject.class);
    }

    @Test(groups = {"caminho-feliz", "home"})
    public void validaSeAnunciosEstaoSemPreco() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        List<AnunciosVendasDto> anunciosVendasDtos = homePage.getAnunciosSemPecoProdutosDto();
        assertEquals(anunciosVendasDtos.size(), 8, "[validaSeAnunciosEstaoSemPreco] - Existem anuncios com preço de venda.");
    }

}
