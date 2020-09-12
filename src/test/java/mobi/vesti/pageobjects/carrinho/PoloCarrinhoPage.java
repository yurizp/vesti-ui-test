package mobi.vesti.pageobjects.carrinho;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class PoloCarrinhoPage {

    public TamanhoP tamanhoP;
    public TamanhoM tamanhoM;
    public TamanhoG tamanhoG;
    public TamanhoGG tamanhoGG;

    public PoloCarrinhoPage(WebDriver driver) {
        tamanhoP = new TamanhoP(driver);
        tamanhoM = new TamanhoM(driver);
        tamanhoG = new TamanhoG(driver);
        tamanhoGG = new TamanhoGG(driver);
    }

    @Getter
    public class TamanhoP {
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[2]/button")
        public WebElement rosa;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[2]/button")
        public WebElement verde;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[2]/button")
        public WebElement azul;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[5]/td[2]/button")
        public WebElement cinza;

        public TamanhoP(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoM {
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[3]/button")
        public WebElement rosa;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[3]/button")
        public WebElement verde;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[4]/td[3]/button")
        public WebElement azul;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[5]/td[3]/button")
        public WebElement cinza;

        public TamanhoM(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoG {
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[4]/button")
        public WebElement rosa;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[4]/button")
        public WebElement verde;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[4]/td[4]/button")
        public WebElement azul;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[5]/td[4]/button")
        public WebElement cinza;

        public TamanhoG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoGG {
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[2]/td[5]/button")
        public WebElement rosa;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[3]/td[5]/button")
        public WebElement verde;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[4]/td[5]/button")
        public WebElement azul;
        @FindBy(xpath = "//item-grid/div/div[2]/table/tbody/tr[5]/td[5]/button")
        public WebElement cinza;

        public TamanhoGG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }
}
