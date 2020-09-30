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
import mobi.vesti.utils.AcoesCustomizadas;
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
        assertThat(infoPageObject.linkInstagram.getAttribute("href")).contains(InfoProperties.PAGE_LINK_INSTAGRAM);
        assertThat(infoPageObject.linkVest.getAttribute("href")).contains(InfoProperties.PAGE_LINK_VESTI);
        assertThat(infoPageObject.linkWhats.getAttribute("href")).contains(InfoProperties.PAGE_LINK_WHATS);
        validarNovaAba(infoPageObject.linkWhats,InfoProperties.PAGE_LINK_WHATS);
        validarNovaAba(infoPageObject.linkInstagram,InfoProperties.URL_INSTAGRAM);
        validarNovaAba(infoPageObject.linkVest,InfoProperties.URL_VESTI);
        // Valida a não exibição do dados de vendedor
        assertThat(AcoesCustomizadas.elementoExiste(infoPageObject.linkWhatsVendedor)).isFalse();
        assertThat(AcoesCustomizadas.elementoExiste(infoPageObject.nomeVendedor)).isFalse();
    }

    /**
     * Validar as informaçoes existentes no menu hamburguer
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarInformacoesUteisLogado() {
        // Faz login na plataforma
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPageObject.logar();

        // Abre o menu hamburguer
        menuPageObject.botaoHamburguer.click();

        // Vai para a tela de informações uteis
        menuPageObject.botaoInformacoesUteis.click();

        // Valida os textos dos linkgs
        assertThat(infoPageObject.linkWhats.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR);
        assertThat(infoPageObject.linkVest.getText()).isEqualTo(InfoProperties.SITE_VESTI);
        assertThat(infoPageObject.linkInstagram.getText()).isEqualTo(InfoProperties.INSTAGRAM);
        assertThat(infoPageObject.infosUteis.titulo.getText()).isEqualTo(InfoProperties.TITULO_INFOS);
        assertThat(infoPageObject.infosUteis.descricaoMinimoPecas.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_PECAS);
        assertThat(infoPageObject.infosUteis.descricaoTroca.getText()).isEqualTo(InfoProperties.DESCRICAO_TROCA);
        assertThat(infoPageObject.infosUteis.descricaoMinimoValor.getText()).isEqualTo(InfoProperties.DESCRICAO_MINIMO_VALOR);
        assertThat(infoPageObject.infosUteis.telefoneEmpresa.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR);

        // Valida que todos itens contem links para os lugares corretos
        assertThat(infoPageObject.infosUteis.nomeEmpresa.getText()).isEqualTo(InfoProperties.NOME_EMPRESA);
        assertThat(infoPageObject.linkInstagram.getAttribute("href")).isEqualTo(InfoProperties.PAGE_LINK_INSTAGRAM);
        assertThat(infoPageObject.linkVest.getAttribute("href")).contains(InfoProperties.PAGE_LINK_VESTI);
        assertThat(infoPageObject.linkWhats.getAttribute("href")).contains(InfoProperties.PAGE_LINK_WHATS);

        // Clica e valida as abas que estão sendo abertas
        validarNovaAba(infoPageObject.linkWhats,InfoProperties.PAGE_LINK_WHATS);
        validarNovaAba(infoPageObject.linkInstagram,InfoProperties.URL_INSTAGRAM);
        validarNovaAba(infoPageObject.linkVest,InfoProperties.URL_VESTI);

        // Valida exibição das informações do vendedor
        assertThat(infoPageObject.nomeVendedor.getText()).isEqualTo(InfoProperties.NOME_VENDEDOR);
        assertThat(infoPageObject.linkWhatsVendedor.getText()).isEqualTo(InfoProperties.TELEFONE_CELULAR_VENDEDOR);
        String href = infoPageObject.linkWhatsVendedor.getAttribute("href");
        System.out.println(href);
        assertThat(href).contains(InfoProperties.PAGE_LINK_WHATS);

        // Clica e valida as abas das informações do vendedor
        validarNovaAba(infoPageObject.linkWhatsVendedor,InfoProperties.PAGE_LINK_WHATS);

        // Faz logout do sisitema
        menuPageObject.botaoVoltar.click();
        menuPageObject.botaoSair.click();
        Thread.sleep(5000);

        // Valida que os produtos que estão sendo exibido não contem preço
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
