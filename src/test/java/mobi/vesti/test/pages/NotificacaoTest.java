package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.GmailPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import mobi.vesti.utils.RetentarUmaVez;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NotificacaoTest extends TestContext {

    public GmailPageObject gmailPageObject;

    @BeforeClass
    public void before() {
        gmailPageObject = new GmailPageObject(driver);
    }

    /**
     * Deve validar que ao entrar na plataforma os produtos exibidos não estão com preço.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validaSeAnunciosEstaoSemPreco() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        gmailPageObject.logarNoGmail();
        gmailPageObject.marcarComoExcluido();
        System.out.println("12sda");
    }

}
