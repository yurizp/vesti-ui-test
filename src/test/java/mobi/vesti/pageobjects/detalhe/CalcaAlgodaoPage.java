package mobi.vesti.pageobjects.detalhe;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CalcaAlgodaoPage {

    public TamanhoP tamanhoP;
    public TamanhoM tamanhoM;
    public TamanhoG tamanhoG;
    public TamanhoGG tamanhoGG;

    @FindBy(xpath = "//item-grid//tr[3]/td[1]/color-button")
    public WebElement corVermelha;

    @FindBy(xpath = "//item-grid//tr[2]/td[1]/color-button")
    public WebElement corRosa;

    @FindBy(xpath = "//catalogue/color-zoom-box/div[1]/div/h3")
    public WebElement tituloCorZoom;

    public CalcaAlgodaoPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        tamanhoP = new TamanhoP(driver);
        tamanhoM = new TamanhoM(driver);
        tamanhoG = new TamanhoG(driver);
        tamanhoGG = new TamanhoGG(driver);
    }

    @Getter
    public class TamanhoP {

        @FindBy(xpath = "//item-grid//tr[2]/td[2]/button")
        private WebElement cinza;

        @FindBy(xpath = "//item-grid//tr[3]/td[2]/button")
        private WebElement preto;

        @FindBy(xpath = "//item-grid//tr[4]/td[2]/button")
        private WebElement azul;

        public TamanhoP(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoM {

        @FindBy(xpath = "//item-grid//tr[2]/td[3]/button")
        private WebElement cinza;

        @FindBy(xpath = "//item-grid//tr[3]/td[3]/button")
        private WebElement preto;

        @FindBy(xpath = "//item-grid//tr[4]/td[3]/button")
        private WebElement azul;

        public TamanhoM(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoG {

        @FindBy(xpath = "//item-grid//tr[2]/td[4]/button")
        public WebElement cinza;

        @FindBy(xpath = "//item-grid//tr[3]/td[4]/button")
        public WebElement preto;

        @FindBy(xpath = "//item-grid//tr[4]/td[4]/button")
        public WebElement azul;

        public TamanhoG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoGG {

        @FindBy(xpath = "//item-grid//tr[2]/td[5]/button")
        public WebElement cinza;

        @FindBy(xpath = "//item-grid//tr[3]/td[5]/button")
        public WebElement preto;

        @FindBy(xpath = "//item-grid//tr[4]/td[5]/button")
        public WebElement azul;

        public TamanhoGG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }
}