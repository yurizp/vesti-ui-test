package mobi.vesti.pageobjects.detalhe;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MacacaoDetalhePage {

    public TamanhoP tamanhoP;
    public TamanhoM tamanhoM;
    @FindBy(xpath = "//cart-panel//cart-product//h2/i")
    public WebElement botaoExcluirPeca;

    public MacacaoDetalhePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        tamanhoP = new TamanhoP(driver);
        tamanhoM = new TamanhoM(driver);
    }

    @Getter
    public class TamanhoP {

        @FindBy(xpath = "//item-grid//tr[2]/td[2]/button")
        public WebElement preto;

        public TamanhoP(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoM {

        @FindBy(xpath = "//item-grid//tr[2]/td[3]/button")
        public WebElement preto;

        public TamanhoM(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }
}
