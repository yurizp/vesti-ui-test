package tech.yurizp.PageObjects;

import lombok.Getter;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.testng.Assert;
import tech.yurizp.Dto.VendedorDto;
import tech.yurizp.Mascara;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

@Getter
public class CadastroVendedorPageObject extends AcoesCustomizadas {

    private URL url = new URL("https://happweb.vesti.mobi/catalogo/divamodas/cadastro");

    @FindBy(how = How.XPATH, using = "//*[@ng-reflect-name=\"cnpjCpfOrEmail\"]//input")
    private WebElement cnpjCpfOuEmail;

    @FindBy(how = How.XPATH, using = "//*[@class=\"form-group\"]/../../button")
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

    public CadastroVendedorPageObject() throws MalformedURLException {
    }

    public String getCnpjCpfOuEmailTextText() {
        return cnpjCpfOuEmail.getAttribute("value");
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
        sendKeys(vendedorDto.getCnpjCpf(), getCnpjCpfOuEmail());
        Assert.assertEquals(getCnpjCpfOuEmailText(), Mascara.cnpj(vendedorDto.getCnpjCpfOuEmail()));
        getBotaoContinuar().click();
        getNome().sendKeys(vendedorDto.getNome());
        getEmail().sendKeys(vendedorDto.getEmail());
        sendKeys(vendedorDto.getTelefone(), getTelefone());
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
        Assert.assertEquals(
                getMensagemCadastroText(),
                "Estamos analisando os seus dados para liberação do catálogo");
        botaoVoltarAoCatalogo.click();
    }

}
