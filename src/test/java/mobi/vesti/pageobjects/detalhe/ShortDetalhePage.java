package mobi.vesti.pageobjects.detalhe;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ShortDetalhePage {

    public Tamanho36 tamanho36;
    public Tamanho38 tamanho38;
    public Tamanho40 tamanho40;
    public Tamanho42 tamanho42;
    public Tamanho46 tamanho46;

    public ShortDetalhePage(WebDriver driver) {
        tamanho36 = new Tamanho36(driver);
        tamanho38 = new Tamanho38(driver);
        tamanho40 = new Tamanho40(driver);
        tamanho42 = new Tamanho42(driver);
        tamanho46 = new Tamanho46(driver);
    }

    @Getter
    public class Tamanho36 {
        @FindBy(xpath = "//table/tbody/tr[2]/td[2]/button")
        public WebElement verde;
        @FindBy(xpath = "//table/tbody/tr[3]/td[2]/button")
        public WebElement vermelho;

        public Tamanho36(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class Tamanho38 {
        @FindBy(xpath = "//table/tbody/tr[2]/td[3]/button")
        public WebElement verde;
        @FindBy(xpath = "//table/tbody/tr[3]/td[3]/button")
        public WebElement vermelho;

        public Tamanho38(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class Tamanho40 {
        @FindBy(xpath = "//table/tbody/tr[2]/td[4]/button")
        public WebElement verde;
        @FindBy(xpath = "//table/tbody/tr[3]/td[4]/button")
        public WebElement vermelho;

        public Tamanho40(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class Tamanho42 {
        @FindBy(xpath = "//table/tbody/tr[2]/td[5]/button")
        public WebElement verde;
        @FindBy(xpath = "//table/tbody/tr[3]/td[5]/button")
        public WebElement vermelho;

        public Tamanho42(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class Tamanho46 {
        @FindBy(xpath = "//table/tbody/tr[2]/td[6]/button")
        public WebElement verde;
        @FindBy(xpath = "//table/tbody/tr[3]/td[6]/button")
        public WebElement vermelho;

        public Tamanho46(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

}