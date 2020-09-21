package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.pageobjects.MenuPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.InfoProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.RetentarUmaVez;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

public class InfoTest extends TestContext {

    public InfoPageObject infoPageObject;
    public MenuPageObject menuPageObject;
    public LoginPageObject loginPageObject;

    @BeforeClass
    public void before() {
        infoPageObject = new InfoPageObject(driver);
        menuPageObject = new MenuPageObject(driver);
        loginPageObject = new LoginPageObject(driver);
    }

    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarInformacoesUteisDeslogado() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        menuPageObject.botaoHamburguer.click();
        menuPageObject.botaoInformacoesUteis.click();
        assertThat(infoPageObject.linkWhats.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR);
        assertThat(infoPageObject.linkVest.getText()).isEqualTo(InfoProperties.URL_VESTI);
        assertThat(infoPageObject.linkInstagram.getText()).isEqualTo(InfoProperties.INSTAGRAM);
        assertThat(infoPageObject.bio.getText()).isEqualTo(InfoProperties.BIO);
        assertThat(infoPageObject.infosUteis.titulo.getText()).isEqualTo(InfoProperties.TITULO_INFOS);
        assertThat(infoPageObject.infosUteis.descricaoMinimoPecas.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_PECAS);
        assertThat(infoPageObject.infosUteis.descricaoTroca.getText()).isEqualTo(InfoProperties.DESCRICAO_TROCA);
        assertThat(infoPageObject.infosUteis.descricaoMinimoValor.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_VALOR);
        infoPageObject.linkInstagram.click();
        validarNovaAba(InfoProperties.URL_INSTAGRAM);
        infoPageObject.linkVest.click();
        validarNovaAba(InfoProperties.LINK_VESTI);
        infoPageObject.linkWhats.click();
        validarNovaAba(InfoProperties.URL_WHATS);
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarInformacoesUteisLogado() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPageObject.logar();
        menuPageObject.botaoHamburguer.click();
        menuPageObject.botaoInformacoesUteis.click();
        assertThat(infoPageObject.linkWhats.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR);
        assertThat(infoPageObject.linkVest.getText()).isEqualTo(InfoProperties.URL_VESTI);
        assertThat(infoPageObject.linkInstagram.getText()).isEqualTo(InfoProperties.INSTAGRAM);
        assertThat(infoPageObject.infosUteis.titulo.getText()).isEqualTo(InfoProperties.TITULO_INFOS);
        assertThat(infoPageObject.infosUteis.descricaoMinimoPecas.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_PECAS);
        assertThat(infoPageObject.infosUteis.descricaoTroca.getText()).isEqualTo(InfoProperties.DESCRICAO_TROCA);
        assertThat(infoPageObject.infosUteis.descricaoMinimoValor.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_VALOR);
        assertThat(infoPageObject.infosUteis.telefoneEmpresa.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR);
        assertThat(infoPageObject.infosUteis.nomeEmpresa.getText()).isEqualTo(InfoProperties.NOME_EMPRESA);
        infoPageObject.linkInstagram.click();
        validarNovaAba(InfoProperties.URL_INSTAGRAM);
        infoPageObject.linkVest.click();
        validarNovaAba(InfoProperties.LINK_VESTI);
        infoPageObject.linkWhats.click();
        validarNovaAba(InfoProperties.URL_WHATS);
    }

    private void validarNovaAba(String url) {
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        assertThat(driver.getCurrentUrl()).isEqualTo(url);
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }

}