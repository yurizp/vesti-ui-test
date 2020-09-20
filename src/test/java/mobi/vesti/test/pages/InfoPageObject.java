package mobi.vesti.test.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class InfoPageObject {

    @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[1]/div/div[2]/p[1]/a")
    public WebElement linkWhats;
    @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[1]/div/div[2]/p[2]/a")
    public WebElement linkVest;
    @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[1]/div/div[2]/p[3]/a")
    public WebElement linkInstagram;
    @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[2]/div")
    public WebElement bio;

    public InfosUteis infosUteis;

    public InfoPageObject(WebDriver driver) {
        PageFactory.initElements(driver, this);
        infosUteis = new InfosUteis(driver);
    }

    public class InfosUteis{

        @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[3]/div[1]/p")
        public WebElement titulo;
        @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[3]/div[1]/ul/li[1]")
        public WebElement descricaoMinimoPecas;
        @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[3]/div[1]/ul/li[2]")
        public WebElement descricaoTroca;
        @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[3]/div[1]/ul/li[3]")
        public WebElement descricaoMinimoValor;
        @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[3]/div[2]/div/p[1]")
        public WebElement nomeEmpresa;
        @FindBy(xpath = "/html/body/app/catalogue/detalhes-da-marca/div/div[3]/div[2]/div/p[2]/a")
        public WebElement telefoneEmpresa;

        public InfosUteis(WebDriver driver) {
            PageFactory.initElements(driver, this);
        }
    }
}
