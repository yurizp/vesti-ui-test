package mobi.vesti.pageobjects;

import lombok.Getter;
import mobi.vesti.dto.LoginDto;
import mobi.vesti.utils.AcoesCustomizadas;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

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
}
