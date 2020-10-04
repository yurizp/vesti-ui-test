package mobi.vesti.pageobjects;

import lombok.Getter;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.net.MalformedURLException;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

@Getter
public class HomePageObject {

    @FindBy(how = How.XPATH, using = "//*[@id=\"grid-panel\"]//list-grid-item")
    private List<WebElement> anunciosProdutos;
    public BannerAplicativo bannerAplicativo;

    public HomePageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        bannerAplicativo = new BannerAplicativo(driver);
    }

    public List<ProdutosDto> getAnunciosSemPecoProdutosDto() {
        return anunciosProdutos.stream()
                .map(
                        webElement ->
                                new ProdutosDto(
                                        ((RemoteWebElement) webElement).findElementByTagName("h2").getText(),
                                        ((RemoteWebElement) webElement).findElementByTagName("button").getText()
                                ))
                .collect(Collectors.toList());
    }

    public List<ProdutosDto> getAnunciosComPrecoProdutosDto() {
        return anunciosProdutos.stream()
                .map(
                        webElement ->
                                new ProdutosDto(
                                        ((RemoteWebElement) webElement).findElementByTagName("h2").getText(),
                                        ((RemoteWebElement) webElement).findElementByTagName("h3").getText()
                                ))
                .collect(Collectors.toList());
    }

    public void clicarEmAnuncioDeProdutoSemPreco() {
        getAnunciosProdutos().stream()
                .map(webElement -> ((RemoteWebElement) webElement).findElementByTagName("button"))
                .findFirst()
                .get()
                .click();
    }

    public void clicarEmAnuncioDeProdutoSemPreco(String anuncioNome) {
        getAnunciosProdutos().stream()
                .filter(element -> StringUtils.equalsIgnoreCase(anuncioNome, ((RemoteWebElement) element).findElementByTagName("h2").getText()))
                .map(webElement -> ((RemoteWebElement) webElement).findElementByTagName("button"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Não foi encontrado o anuncio %s", anuncioNome)))
                .click();
    }

    public WebElement buscarAnuncioDeProduto(String anuncioNome) {
        return getAnunciosProdutos().stream()
                .filter(element -> StringUtils.equalsIgnoreCase(anuncioNome, ((RemoteWebElement) element).findElementByTagName("h2").getText()))
                .map(webElement -> ((RemoteWebElement) webElement).findElementByClassName("grid-image-container"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Não foi encontrado o anuncio %s", anuncioNome)));
    }

    public void clicarEmAnuncioDeProdutoComPreco() {
        getAnunciosProdutos().stream()
                .map(webElement -> ((RemoteWebElement) webElement).findElementByTagName("button"))
                .findFirst()
                .get()
                .click();
    }

    public void clicarEmAnuncioDeProdutoComPreco(String anuncioNome) {
        getAnunciosProdutos()
                .stream()
                .filter(element -> StringUtils.equalsIgnoreCase(anuncioNome, ((RemoteWebElement) element).findElementByTagName("h2").getText()))
                .map(webElement -> ((RemoteWebElement) webElement).findElementByClassName("grid-image-container"))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("Não foi encontrado o anuncio %s", anuncioNome)))
                .click();
    }

    public void validarQuePrecosNaoSaoExibidosQaModas() {
        List<ProdutosDto> anunciosVendasDtos = this.getAnunciosSemPecoProdutosDto();
        assertEquals(anunciosVendasDtos.size(), 11);
    }

    /**
     * Valida que estão sendo exibidos o numero esperado de produtos com preço.
     */
    public void validarQuePrecosEstaoSendoExibidosQaModas() {
        List<ProdutosDto> anunciosVendasDtos = this.getAnunciosComPrecoProdutosDto();
        assertEquals(anunciosVendasDtos.size(), 11, "O numero de produtos na home do QaModas não bate com o esperado.");
    }

    /**
     * Valida que estão sendo exibidos o numero esperado de produtos com preço.
     */
    public void validarQuantidadeDeProdutosSendoExibidosPepitaModas() {
        List<ProdutosDto> anunciosVendasDtos = this.getAnunciosComPrecoProdutosDto();
        assertEquals(anunciosVendasDtos.size(), 23, "O numero de produtos na home do Pepita Modas não bate com o esperado.");
    }

    /**
     * Valida o titulo e o valor dos produtos que estão sendo exibidos na home.
     */
    public void validarTituloEPrecoDeProdutosQaModas() {
        List<ProdutosDto> produtosHome = getAnunciosComPrecoProdutosDto();
        assertThat(produtosHome.toArray()).containsExactlyInAnyOrder(ProdutosQaModasProperties.PRODUTOS_HOME_COM_PRECO.toArray())
                .withFailMessage(MensgensProperties.HOME_PRODUTOS_DIFERENTES);
    }

    public class BannerAplicativo {
        @FindBy(xpath = "//*[@class=\"navbar navbar-default navbar-fixed-top\"]//*[@class=\"banner-container\"]//img")
        public WebElement icone;
        @FindBy(xpath = "//*[@class=\"navbar navbar-default navbar-fixed-top\"]//*[@class=\"banner-container\"]//*[@class=\"title\"]")
        public WebElement titulo;
        @FindBy(xpath = "//*[@class=\"navbar navbar-default navbar-fixed-top\"]//*[@class=\"banner-container\"]//*[@class=\"subtitle\"]")
        public WebElement descricao;
        @FindBy(xpath = "//*[@class=\"navbar navbar-default navbar-fixed-top\"]//*[@class=\"banner-container\"]//button[2]")
        public WebElement botaoBaixar;
        @FindBy(xpath = "//*[@class=\"navbar navbar-default navbar-fixed-top\"]//*[@class=\"banner-container\"]//button[1]")
        public WebElement botaoFechar;
        public BannerAplicativo(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }
}
