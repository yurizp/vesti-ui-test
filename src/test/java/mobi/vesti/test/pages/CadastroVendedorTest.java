package mobi.vesti.test.pages;

import mobi.vesti.dto.VendedorDto;
import mobi.vesti.factory.ContatoFactory;
import mobi.vesti.factory.DocumentoFactory;
import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import mobi.vesti.utils.Mascara;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.text.ParseException;

import static mobi.vesti.properties.CadastroVendedorProperties.MENSAGEM_CPF_CNPJ_INVALIDOS;
import static mobi.vesti.properties.CadastroVendedorProperties.MENSAGEM_DE_SENHAS_DIFERENTES;
import static mobi.vesti.properties.CadastroVendedorProperties.MENSAGEM_EMAIL_INVALIDO;
import static mobi.vesti.properties.CadastroVendedorProperties.POP_VOCE_DEVE_PREENCHER_CAMPOS_CORRETAMENTE;
import static mobi.vesti.utils.AcoesCustomizadas.sendKeys;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;

public class CadastroVendedorTest extends TestContext {

    public HomePageObject homePage;
    public CadastroVendedorPageObject cadastroVendedorPage;

    @BeforeClass()
    public void before() {
        cadastroVendedorPage = PageFactory.initElements(driver, CadastroVendedorPageObject.class);
        homePage = PageFactory.initElements(driver, HomePageObject.class);
    }

    @Test(retryAnalyzer = mobi.vesti.utils.RetryAnalyzer.class)
    public void testarFluxoDeCadastroComSucessoDeNovoVendedor()
            throws InterruptedException, ParseException {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        homePage.validarQuePrecosNaoSaoExibidos();
        String cnpj = DocumentoFactory.cnpj();
        VendedorDto vendedorDto =
                VendedorDto.builder()
                        .cnpjCpf(cnpj)
                        .confirmacaoSenha("@MinhaSenha123@")
                        .email(ContatoFactory.email())
                        .cnpjCpfOuEmail(cnpj)
                        .senha("@MinhaSenha123@")
                        .telefone(ContatoFactory.celular(10))
                        .nome(ContatoFactory.nomeSobrenome())
                        .build();
        homePage.clicarEmAnuncioDeProdutoSemPreco();
        cadastroVendedorPage.preencherFormularioCnpj(vendedorDto);
        cadastroVendedorPage.finalizarCadastro();
        homePage.validarQuePrecosNaoSaoExibidos();
    }

    @Test(retryAnalyzer = mobi.vesti.utils.RetryAnalyzer.class)
    public void testarValidacaoDeCamposNoCadastroDeVendedor() throws Exception {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        homePage.validarQuePrecosNaoSaoExibidos();
        homePage.clicarEmAnuncioDeProdutoSemPreco();
        cadastroVendedorPage.getCnpjCpfOuEmail().click();
        sendKeys("123asdasdsdada", cadastroVendedorPage.getCnpjCpfOuEmail());
        cadastroVendedorPage.getBotaoContinuar().click();
        assertEquals(StringUtils.trimToNull(cadastroVendedorPage.getMensagemCpfOuCnpjInvalidos().getText()), MENSAGEM_CPF_CNPJ_INVALIDOS);
        cadastroVendedorPage.getCnpjCpfOuEmail().clear();
        sendKeys("18733438000144", cadastroVendedorPage.getCnpjCpfOuEmail());
        assertEquals(cadastroVendedorPage.getCnpjCpfOuEmailText(), Mascara.cnpj("18733438000144"));
        AcoesCustomizadas.focarNoElemento(cadastroVendedorPage.getBotaoContinuar());
        cadastroVendedorPage.getBotaoVoltar().click();
        homePage.clicarEmAnuncioDeProdutoSemPreco();
        sendKeys("18733438000144", cadastroVendedorPage.getCnpjCpfOuEmail());
        cadastroVendedorPage.getBotaoContinuar().click();
        testarValidacaoDeEmail();
        assertFalse(cadastroVendedorPage.getCnpjCpf().isEnabled(), "O campo de CPF ou CNPJ esta editavel esta habilitado como editavel.");
        testarValidacaoDeTelefone();
        testarValidacaoDasSenhas();
    }

    private void testarValidacaoDasSenhas() {
        cadastroVendedorPage.getSenha().sendKeys("123");
        cadastroVendedorPage.getConfirmacaoSenha().sendKeys("1234");
        cadastroVendedorPage.getBotaoCadastrar().click();
        assertEquals(cadastroVendedorPage.getMensagemCampoInvalidoText(), MENSAGEM_DE_SENHAS_DIFERENTES);
    }

    private void testarValidacaoDeTelefone() throws Exception {
        sendKeys("24124bghvg1", cadastroVendedorPage.getTelefone());
        cadastroVendedorPage.getBotaoCadastrar().click();
        assertEquals(cadastroVendedorPage.getPopMensagemAlertaPreencherCorretamenteText(), POP_VOCE_DEVE_PREENCHER_CAMPOS_CORRETAMENTE);
        cadastroVendedorPage.getTelefone().clear();
        sendKeys("5198343334", cadastroVendedorPage.getTelefone());
        assertEquals(cadastroVendedorPage.getTelefoneText(), Mascara.telefone("5198343334"));
    }

    private void testarValidacaoDeEmail() throws Exception {
        sendKeys("@test.com", cadastroVendedorPage.getEmail());
        cadastroVendedorPage.getBotaoCadastrar().click();
        assertEquals(cadastroVendedorPage.getMensagemCampoInvalidoText(), MENSAGEM_EMAIL_INVALIDO);
        assertEquals(cadastroVendedorPage.getPopMensagemAlertaPreencherCorretamenteText(), POP_VOCE_DEVE_PREENCHER_CAMPOS_CORRETAMENTE);
        cadastroVendedorPage.getEmail().clear();
        //TODO: AJUSTAR EMAIL COM CARACTERES ESPECIAIS
        sendKeys(ContatoFactory.email(), cadastroVendedorPage.getEmail());
    }
}
