package mobi.vesti;

import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.test.TestContext;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

public class Mensagens {

    @FindBy(how = How.XPATH, using = "\"//*[@ng-reflect-klass=\\\"form-group\\\"]//span\"")
    private WebElement element;

    public Mensagens() {
        PageFactory.initElements(TestContext.driver, this);;
    }

    public String texto(){
        return element.getText();
    }
}
