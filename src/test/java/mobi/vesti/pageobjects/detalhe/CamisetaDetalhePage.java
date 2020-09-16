package mobi.vesti.pageobjects.detalhe;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CamisetaDetalhePage {

    public TamanhoP tamanhoP;
    public TamanhoM tamanhoM;
    public TamanhoG tamanhoG;
    public TamanhoGG tamanhoGG;

    public CamisetaDetalhePage(WebDriver driver) {
        tamanhoP = new TamanhoP(driver);
        tamanhoM = new TamanhoM(driver);
        tamanhoG = new TamanhoG(driver);
        tamanhoGG = new TamanhoGG(driver);
    }

    @Getter
    public class TamanhoP {

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[2]/button")
        private WebElement branco;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[2]/button")
        private WebElement amarelo;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[4]/td[2]/button")
        private WebElement azul;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[5]/td[2]/button")
        private WebElement preto;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[6]/td[2]/button")
        private WebElement vinho;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[7]/td[2]/button")
        private WebElement vermelho;

        public TamanhoP(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoM {

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[3]/button")
        private WebElement branco;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[3]/button")
        private WebElement amarelo;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[4]/td[3]/button")
        private WebElement azul;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[5]/td[3]/button")
        private WebElement preto;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[6]/td[3]/button")
        private WebElement vinho;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[7]/td[3]/button")
        private WebElement vermelho;

        public TamanhoM(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoG {

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[4]/button")
        private WebElement branco;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[4]/button")
        private WebElement amarelo;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[4]/td[4]/button")
        private WebElement azul;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[5]/td[4]/button")
        private WebElement preto;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[6]/td[4]/button")
        private WebElement vinho;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[7]/td[4]/button")
        private WebElement vermelho;

        public TamanhoG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoGG {

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[5]/button")
        private WebElement branco;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[5]/button")
        private WebElement amarelo;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[4]/td[5]/button")
        private WebElement azul;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[5]/td[5]/button")
        private WebElement preto;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[6]/td[5]/button")
        private WebElement vinho;

        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[7]/td[5]/button")
        private WebElement vermelho;

        public TamanhoGG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

}
