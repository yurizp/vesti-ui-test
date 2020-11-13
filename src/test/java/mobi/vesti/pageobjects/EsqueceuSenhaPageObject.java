package mobi.vesti.pageobjects;

import mobi.vesti.utils.AcoesCustomizadas;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class EsqueceuSenhaPageObject {

    @FindBy(xpath = "//*[@class=\"form-flex\"]/p[2]")
    public WebElement mensagem;
    @FindBy(xpath = "//*[@class=\"form-flex\"]/p[1]")
    public WebElement titulo;
                     //*[normalize-space(text())='OK']
    @FindBy(xpath = "//*[normalize-space(text())='Ok']")
    public WebElement botaoOk;
    @FindBy(xpath = "//*[@class=\"icon icon-arrow-left\"]")
    public WebElement botaoVoltar;
    @FindBy(xpath = "//*[@class=\"email-link\"]")
    public WebElement linkNaoTemMaisAcessoAoEmail;
    @FindBy(xpath = "//*[@placeholder=\"Email\"]")
    public WebElement alterarEmail;
    public Alerta alerta;

    public EsqueceuSenhaPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        alerta = new Alerta(driver);
    }

    public class Alerta {
        @FindBy(xpath = "//*[@class=\"prompt-message\"]")
        public WebElement mensage;
        @FindBy(xpath = "//*[normalize-space(text())='OK']")
        public WebElement botaoOK;

        public Alerta(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }

    }
}
