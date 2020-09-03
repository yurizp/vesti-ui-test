package mobi.vesti.pageobjects;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import mobi.vesti.dto.AnunciosVendasDto;
import mobi.vesti.properties.ConfiguracoesGlobais;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class HomePage extends AcoesCustomizadas {

    private URL url = new URL(ConfiguracoesGlobais.BASE_URL);

    @FindBy(how = How.XPATH, using = "//*[@id=\"grid-panel\"]//list-grid-item")
    private List<WebElement> anunciosProdutos;

    public HomePage() throws MalformedURLException {
    }

    public List<AnunciosVendasDto> getAnunciosProdutosDto() {
        return anunciosProdutos.stream()
                .map(
                        webElement ->
                                new AnunciosVendasDto(
                                        ((RemoteWebElement) webElement).findElementByTagName("h2").getText(),
                                        ((RemoteWebElement) webElement).findElementByTagName("button").getText()))
                .collect(Collectors.toList());
    }

    public List<AnunciosVendasDto> buscarTodosAnunciosComPreco() {
        return getAnunciosProdutosDto()
                .stream()
                .filter(anunciosVendasDto -> !"Ver Preço".equalsIgnoreCase(anunciosVendasDto.getPreco()))
                .collect(Collectors.toList());
    }

    public void clicarEmAnuncioDeProduto() {
        getAnunciosProdutos().stream()
                .map(webElement -> ((RemoteWebElement) webElement).findElementByTagName("button"))
                .findFirst()
                .get()
                .click();
    }
}
