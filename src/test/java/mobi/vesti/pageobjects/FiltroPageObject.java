package mobi.vesti.pageobjects;

import mobi.vesti.dto.CategoriaDto;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.w3c.dom.html.HTMLInputElement;

import java.util.List;
import java.util.stream.Collectors;

public class FiltroPageObject {

    @FindBy(xpath = "//*[@id=\"grid-panel\"]//filter-button")
    private List<WebElement> categorias;

    public TelaDeFiltros adicionarFiltros;

    public FiltroPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        adicionarFiltros = new TelaDeFiltros(driver);
    }

    public List<CategoriaDto> categoriasDto() {
        return categorias.stream()
                .map(
                        webElement ->
                                new CategoriaDto(
                                        ((RemoteWebElement) webElement).findElementByTagName("button").getText(),
                                        webElement
                                ))
                .collect(Collectors.toList());
    }

    public WebElement categorias(String categoriaNome) {
        return categoriasDto().stream().filter(element -> StringUtils.equalsIgnoreCase(categoriaNome, element.nome))
                .findFirst()
                .map(categoriaDto -> categoriaDto.categoriaElement)
                .orElseThrow(() -> new RuntimeException(String.format("Não foi encontrado a categoria %s", categoriaNome)));
    }

    public boolean existeCategoria(String categoriaNome) {
        try {
            categorias(categoriaNome);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public class TelaDeFiltros {

        @FindBy(xpath = "//*[@class=\"icon icon-menu\"]")
        public WebElement botaoMenuHamburguer;
        @FindBy(xpath = "//filter-panel//filter-button")
        private List<WebElement> categorias;
        @FindBy(xpath = "//*[@class=\"icon icon-arrow-left\"]")
        public WebElement botaoVoltar;
        @FindBy(xpath = "//*[@class=\"icon icon-more\"]")
        public WebElement botaoAdicionar;

        public TelaDeFiltros(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

        public List<CategoriaDto> categoriasDto() {
            return this.categorias.stream()
                    .map(
                            webElement ->
                                    new CategoriaDto(
                                            ((RemoteWebElement) webElement).findElementByTagName("button").getText(),
                                            webElement
                                    ))
                    .collect(Collectors.toList());
        }

        public WebElement categorias(String categoriaNome) {
            return categoriasDto().stream().filter(element -> StringUtils.equalsIgnoreCase(categoriaNome, element.nome))
                    .findFirst()
                    .map(categoriaDto -> categoriaDto.categoriaElement)
                    .orElseThrow(() -> new RuntimeException(String.format("Não foi encontrado a categoria %s", categoriaNome)));
        }

        public boolean existeCategorias(String categoriaNome) {
            try {
                categorias(categoriaNome);
                return true;
            }catch (Exception e){
                return false;
            }
        }
    }
}
