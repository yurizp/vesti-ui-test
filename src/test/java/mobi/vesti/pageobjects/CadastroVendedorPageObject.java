package mobi.vesti.pageobjects;

import lombok.Getter;
import mobi.vesti.dto.VendedorDto;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import mobi.vesti.utils.Mascara;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

import java.text.ParseException;

import static mobi.vesti.properties.CadastroVendedorProperties.MENSAGEM_ANALISANDO_DADOS;
import static org.testng.Assert.assertFalse;

@Getter
public class CadastroVendedorPageObject {

    @FindBy(how = How.XPATH, using = "/html/body/app/catalogue/sign-in-panel/div/div/form/div/x-input/div/input")
    private WebElement cnpjCpfOuEmail;

    @FindBy(how = How.XPATH, using = "//form//button")
    private WebElement botaoContinuar;

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-name=\"cnpjcpf\"]//input")
    private WebElement cnpjCpf;

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-name=\"name\"]//input")
    private WebElement nome;

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-name=\"email\"]//input")
    private WebElement email;

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-name=\"phone\"]//input")
    private WebElement telefone;

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-name=\"password\"]//input")
    private WebElement senha;

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-name=\"password_confirmation\"]//input")
    private WebElement confirmacaoSenha;

    @FindBy(how = How.XPATH, using = "//*[@class=\"form-group\"]/../../button")
    private WebElement botaoCadastrar;

    @FindBy(how = How.XPATH, using = "//*[@class=\"message-box\"]/p")
    private WebElement mensagemCadastroComSucesso;

    @FindBy(how = How.XPATH, using = "//*[@class=\"vesti-panel\"]//button")
    private WebElement botaoVoltarAoCatalogo;

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-klass=\"form-group\"]//span")
    private WebElement mensagemCpfOuCnpjInvalidos;

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-name=\"password\"]//..//span")
    private WebElement mensagemCampoInvalido;

    @FindBy(how = How.XPATH, using = "//catalogue/alert/div")
    private WebElement popMensagemAlertaPreencherCorretamente;

    @FindBy(how = How.XPATH, using = "//*[@class=\"container-fluid\"]//button")
    public WebElement botaoVoltar;


    public CadastroVendedorPageObject(WebDriver driver) {
        PageFactory.initElements(TestContext.driver, this);
    }

    public String getCnpjCpfOuEmailText() {
        return cnpjCpfOuEmail.getAttribute("value");
    }

    public String getCnpjCpfText() {
        return cnpjCpf.getAttribute("value");
    }

    public String getNomeText() {
        return nome.getAttribute("value");
    }

    public String getEmailText() {
        return email.getAttribute("value");
    }

    public String getTelefoneText() {
        return telefone.getAttribute("value");
    }

    public String getSenhaText() {
        return senha.getAttribute("value");
    }

    public String getConfirmacaoSenhaText() {
        return confirmacaoSenha.getAttribute("value");
    }

    public String getBotaoCadastrarText() {
        return botaoCadastrar.getAttribute("value");
    }

    public String getMensagemCadastroText() {
        return mensagemCadastroComSucesso.getText();
    }

    public void preencherFormularioCnpj(VendedorDto vendedorDto)
            throws InterruptedException, ParseException {
        AcoesCustomizadas.sendKeys(vendedorDto.getCnpjCpf(), getCnpjCpfOuEmail());
        Assert.assertEquals(getCnpjCpfOuEmailText(), Mascara.cnpj(vendedorDto.getCnpjCpfOuEmail()));
        getBotaoContinuar().click();
        assertFalse(getCnpjCpf().isEnabled(), "O campo de CPF ou CNPJ esta editavel esta habilitado como editavel.");
        getNome().sendKeys(vendedorDto.getNome());
        AcoesCustomizadas.sendKeys(vendedorDto.getEmail(), getEmail());
        AcoesCustomizadas.sendKeys(vendedorDto.getTelefone(), getTelefone());
        getSenha().sendKeys(vendedorDto.getSenha());
        getConfirmacaoSenha().sendKeys(vendedorDto.getConfirmacaoSenha());
        Assert.assertEquals(this.getCnpjCpfText(), Mascara.cnpj((vendedorDto.getCnpjCpf())));
        Assert.assertEquals(this.getEmailText(), vendedorDto.getEmail());
        Assert.assertEquals(this.getTelefoneText(), Mascara.telefone(vendedorDto.getTelefone()));
        Assert.assertEquals(this.getSenhaText(), vendedorDto.getSenha());
        Assert.assertEquals(this.getNomeText(), vendedorDto.getNome());
        Assert.assertEquals(this.getConfirmacaoSenhaText(), vendedorDto.getConfirmacaoSenha());
    }

    public void finalizarCadastro() {
        getBotaoCadastrar().click();
        String actual = getMensagemCadastroText();
        Assert.assertEquals(actual, MENSAGEM_ANALISANDO_DADOS);
        botaoVoltarAoCatalogo.click();
    }

    public String getPopMensagemAlertaPreencherCorretamenteText() {
        return StringUtils.trimToNull(getPopMensagemAlertaPreencherCorretamente().getAttribute("innerHTML"));
    }

    public String getMensagemCampoInvalidoText() {
        return getMensagemCampoInvalido().getText();
    }
}
