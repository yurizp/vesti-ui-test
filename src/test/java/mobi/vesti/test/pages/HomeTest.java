package mobi.vesti.test.pages;

import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.RetentarUmaVez;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class HomeTest extends TestContext {

    public HomePageObject homePage;

    @BeforeClass
    public void before() {
        homePage = PageFactory.initElements(driver, HomePageObject.class);
    }

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validaSeAnunciosEstaoSemPreco() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        List<ProdutosDto> produtosHome = homePage.getAnunciosSemPecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosProperties.PRODUTOS_HOME_SEM_PRECO.toArray())
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

}
