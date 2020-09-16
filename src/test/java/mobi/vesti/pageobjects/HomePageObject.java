package mobi.vesti.pageobjects;

import lombok.Getter;
import mobi.vesti.dto.ProdutosDto;
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

import static org.testng.Assert.assertEquals;

@Getter
public class HomePageObject {

    @FindBy(how = How.XPATH, using = "//*[@id=\"grid-panel\"]//list-grid-item")
    private List<WebElement> anunciosProdutos;

    public HomePageObject() throws MalformedURLException {
    }

    public HomePageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
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

    public void validarQuePrecosNaoSaoExibidos() {
        List<ProdutosDto> anunciosVendasDtos = this.getAnunciosSemPecoProdutosDto();
        assertEquals(anunciosVendasDtos.size(), 9);
    }

    public void validarQuePrecosEstaoSendoExibidos() {
        List<ProdutosDto> anunciosVendasDtos = this.getAnunciosComPrecoProdutosDto();
        assertEquals(anunciosVendasDtos.size(), 9);
    }
}
