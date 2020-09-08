package mobi.vesti.pageobjects;

import lombok.Getter;
import mobi.vesti.dto.LoginDto;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.utils.AcoesCustomizadas;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import static mobi.vesti.test.TestContext.driver;

@Getter
public class LoginPageObject {

    @FindBy(xpath = "//*[@ng-reflect-name=\"cnpjCpfOrEmail\"]//input")
    private WebElement campoCpfCnpj;

    @FindBy(xpath = "//*[@ng-reflect-name=\"password\"]//input")
    private WebElement campoSenha;

    @FindBy(xpath = "//*[@ng-reflect-name=\"password\"]//..//button")
    private WebElement botaoContinuar;

    public LoginPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public void preencherLogin(LoginDto loginDto) throws InterruptedException {
        campoSenha.sendKeys(loginDto.getSenha());
        Assert.assertFalse(getCampoCpfCnpj().isEnabled());
    }

    public void logar() throws InterruptedException {
        CadastroVendedorPageObject cadastroVendedorPage = PageFactory.initElements(driver, CadastroVendedorPageObject.class);
        driver.navigate().to(ConfiguracoesGlobais.LOGIN);
        AcoesCustomizadas.sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj(), cadastroVendedorPage.getCnpjCpfOuEmail());
        cadastroVendedorPage.getBotaoContinuar().click();
        preencherLogin(LoginProperties.LOGIN_VALIDO);
        getBotaoContinuar().click();
    }
}