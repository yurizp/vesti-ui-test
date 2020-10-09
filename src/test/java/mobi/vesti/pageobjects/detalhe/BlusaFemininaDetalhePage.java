package mobi.vesti.pageobjects.detalhe;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class BlusaFemininaDetalhePage {

    public TamanhoP tamanhoP;
    public TamanhoM tamanhoM;
    public TamanhoG tamanhoG;

    public BlusaFemininaDetalhePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        tamanhoP = new TamanhoP(driver);
        tamanhoM = new TamanhoM(driver);
        tamanhoG = new TamanhoG(driver);
    }

    @Getter
    public class TamanhoP {

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[2]")
        public WebElement azul;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[2]")
        public WebElement preto;

        public TamanhoP(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoM {

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[3]")
        public WebElement azul;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[3]")
        public WebElement preto;

        public TamanhoM(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoG {

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[4]")
        public WebElement azul;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[4]")
        public WebElement preto;

        public TamanhoG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

}
