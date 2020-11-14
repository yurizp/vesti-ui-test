package mobi.vesti.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EnderecoPageObject {

    @FindBy(xpath = "//*[@placeholder=\"CEP\"]//input")
    public WebElement cep;
    @FindBy(xpath = "//*[@placeholder=\"Endereço\"]//input")
    public WebElement endereco;
    @FindBy(xpath = "//*[@placeholder=\"nº\"]//input")
    public WebElement numero;
    @FindBy(xpath = "//*[@placeholder=\"Complemento (opcional)\"]//input")
    public WebElement complemento;
    @FindBy(xpath = "//*[@placeholder=\"Bairro\"]//input")
    public WebElement bairro;
    @FindBy(xpath = "//*[@placeholder=\"Referência (opcional)\"]//input")
    public WebElement referencia;
    @FindBy(xpath = "//*[@placeholder=\"Cidade\"]//input")
    public WebElement cidade;
    @FindBy(xpath = "//*[@placeholder=\"UF\"]//select")
    public WebElement estado;
    @FindBy(xpath = "//*[@class=\"x-checkbox-container\"]//span")
    public WebElement enderecoPrincipal;
    @FindBy(xpath = "//*[normalize-space(text())='Salvar']")
    public WebElement botaoSalvar;

    public EnderecoPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

}
