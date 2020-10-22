package mobi.vesti.pageobjects;

import lombok.Getter;
import lombok.SneakyThrows;
import mobi.vesti.dto.ProdutosDto;
import mobi.vesti.properties.MensgensProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import mobi.vesti.test.TestContext;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

@Getter
public class GmailPageObject {

    private static final String URL = "https://accounts.google.com/signin/v2/identifier?continue=https%3A%2F%2Fmail.google.com%2Fmail%2F&service=mail&sacu=1&rip=1&flowName=GlifWebSignIn&flowEntry=ServiceLogin";

    @FindBy(how = How.XPATH, using = "//*[@id=\"identifierId\"]")
    private WebElement usuario;

    @FindBy(how = How.XPATH, using = "//*[@name=\"password\"]")
    private WebElement senha;

    @FindBy(xpath = "//*[normalize-space(text())='Próxima']/..")
    public WebElement botaoProximo;

    @FindBy(xpath = "//*[@value=\"Eliminar\"]")
    public WebElement botaoEliminar;

    @FindBy(xpath = "//*[@type=\"checkbox\"]")
    public List<WebElement> checkBoxes;

    public GmailPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    @SneakyThrows
    public void marcarComoExcluido(){
        for(WebElement element: checkBoxes){
            element.click();
        }
        botaoEliminar.click();
        Thread.sleep(1000);
    }

    @SneakyThrows
    public void logarNoGmail() {
        TestContext.driver.navigate().to(URL);
        usuario.sendKeys("testeclienteqa");
        botaoProximo.click();
        Thread.sleep(2000);
        senha.sendKeys("vesti4141");
        botaoProximo.click();
        Thread.sleep(2000);
    }
}