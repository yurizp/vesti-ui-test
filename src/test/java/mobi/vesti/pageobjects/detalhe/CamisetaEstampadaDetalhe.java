package mobi.vesti.pageobjects.detalhe;

import lombok.Getter;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class CamisetaEstampadaDetalhe {

    public TamanhoP tamanhoP;
    public TamanhoM tamanhoM;
    public TamanhoG tamanhoG;
    public TamanhoGG tamanhoGG;

    public CamisetaEstampadaDetalhe(WebDriver driver) {
        tamanhoP = new TamanhoP(driver);
        tamanhoM = new TamanhoM(driver);
        tamanhoG = new TamanhoG(driver);
        tamanhoGG = new TamanhoGG(driver);
    }

    @Getter
    public class TamanhoP {
        @FindBy(xpath = "//table/tbody/tr[2]/td[2]/button")
        public WebElement preto;

        public TamanhoP(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoM {
        @FindBy(xpath = "//table/tbody/tr[2]/td[3]/button")
        public WebElement preto;

        public TamanhoM(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoG {
        @FindBy(xpath = "//table/tbody/tr[2]/td[4]/button")
        public WebElement preto;

        public TamanhoG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }

    @Getter
    public class TamanhoGG {
        @FindBy(xpath = "//table/tbody/tr[2]/td[5]/button")
        public WebElement preto;

        public TamanhoGG(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }
}
