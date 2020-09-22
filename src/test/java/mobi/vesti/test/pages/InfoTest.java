package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.InfoPageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.pageobjects.MenuPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.InfoProperties;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.RetentarUmaVez;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class InfoTest extends TestContext {

    public InfoPageObject infoPageObject;
    public MenuPageObject menuPageObject;
    public LoginPageObject loginPageObject;
    public HomePageObject homePageObject;

    @BeforeClass
    public void before() {
        infoPageObject = new InfoPageObject(driver);
        menuPageObject = new MenuPageObject(driver);
        loginPageObject = new LoginPageObject(driver);
        homePageObject = new HomePageObject(driver);
    }

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarInformacoesUteisDeslogado() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        menuPageObject.botaoHamburguer.click();
        menuPageObject.botaoInformacoesUteis.click();
        assertThat(infoPageObject.linkWhats.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR);
        assertThat(infoPageObject.linkVest.getText()).isEqualTo(InfoProperties.SITE_VESTI);
        assertThat(infoPageObject.linkInstagram.getText()).isEqualTo(InfoProperties.INSTAGRAM);
        assertThat(infoPageObject.bio.getText()).isEqualTo(InfoProperties.BIO);
        assertThat(infoPageObject.infosUteis.titulo.getText()).isEqualTo(InfoProperties.TITULO_INFOS);
        assertThat(infoPageObject.infosUteis.descricaoMinimoPecas.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_PECAS);
        assertThat(infoPageObject.infosUteis.descricaoTroca.getText()).isEqualTo(InfoProperties.DESCRICAO_TROCA);
        assertThat(infoPageObject.infosUteis.descricaoMinimoValor.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_VALOR);
        assertThat(InfoProperties.PAGE_LINK_INSTAGRAM).isEqualTo(infoPageObject.linkInstagram.getAttribute("href"));
        assertThat(InfoProperties.PAGE_LINK_VESTI).isEqualTo(infoPageObject.linkVest.getAttribute("href"));
        assertThat(InfoProperties.PAGE_LINK_WHATS).isEqualTo(infoPageObject.linkWhats.getAttribute("href"));
        validarNovaAba(infoPageObject.linkWhats,InfoProperties.PAGE_LINK_WHATS);
        validarNovaAba(infoPageObject.linkInstagram,InfoProperties.URL_INSTAGRAM);
        validarNovaAba(infoPageObject.linkVest,InfoProperties.URL_VESTI);
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarInformacoesUteisLogado() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPageObject.logar();
        menuPageObject.botaoHamburguer.click();
        menuPageObject.botaoInformacoesUteis.click();
        assertThat(infoPageObject.linkWhats.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR);
        assertThat(infoPageObject.linkVest.getText()).isEqualTo(InfoProperties.SITE_VESTI);
        assertThat(infoPageObject.linkInstagram.getText()).isEqualTo(InfoProperties.INSTAGRAM);
        assertThat(infoPageObject.infosUteis.titulo.getText()).isEqualTo(InfoProperties.TITULO_INFOS);
        assertThat(infoPageObject.infosUteis.descricaoMinimoPecas.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_PECAS);
        assertThat(infoPageObject.infosUteis.descricaoTroca.getText()).isEqualTo(InfoProperties.DESCRICAO_TROCA);
        assertThat(infoPageObject.infosUteis.descricaoMinimoValor.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_VALOR);
        assertThat(infoPageObject.infosUteis.telefoneEmpresa.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR);
        assertThat(infoPageObject.infosUteis.nomeEmpresa.getText()).isEqualTo(InfoProperties.NOME_EMPRESA);
        assertThat(InfoProperties.PAGE_LINK_INSTAGRAM).isEqualTo(infoPageObject.linkInstagram.getAttribute("href"));
        assertThat(InfoProperties.PAGE_LINK_VESTI).isEqualTo(infoPageObject.linkVest.getAttribute("href"));
        assertThat(InfoProperties.PAGE_LINK_WHATS).isEqualTo(infoPageObject.linkWhats.getAttribute("href"));
        validarNovaAba(infoPageObject.linkWhats,InfoProperties.PAGE_LINK_WHATS);
        validarNovaAba(infoPageObject.linkInstagram,InfoProperties.URL_INSTAGRAM);
        validarNovaAba(infoPageObject.linkVest,InfoProperties.URL_VESTI);
        menuPageObject.botaoVoltar.click();
        menuPageObject.botaoSair.click();
        Thread.sleep(5000);
        assertThat(homePageObject.getAnunciosSemPecoProdutosDto()).containsExactlyInAnyOrderElementsOf(ProdutosProperties.PRODUTOS_HOME_SEM_PRECO).withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);

    }

    @SneakyThrows
    private void validarNovaAba(WebElement element, String url) {
        element.click();
        Thread.sleep(2000);
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertThat(driver.getCurrentUrl()).contains(url);
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }
}
