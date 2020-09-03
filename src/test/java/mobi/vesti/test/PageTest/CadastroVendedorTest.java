package mobi.vesti.test.PageTest;

import mobi.vesti.Mascara;
import mobi.vesti.dto.AnunciosVendasDto;
import mobi.vesti.dto.VendedorDto;
import mobi.vesti.factory.ContatoFactory;
import mobi.vesti.factory.DocumentoFactory;
import mobi.vesti.pageobjects.AcoesCustomizadas;
import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.HomePage;
import mobi.vesti.test.TestContext;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.List;

import static mobi.vesti.pageobjects.AcoesCustomizadas.sendKeys;
import static mobi.vesti.properties.CadastroVendedorProperties.MENSAGEM_CPF_CNPJ_INVALIDOS;
import static mobi.vesti.properties.CadastroVendedorProperties.MENSAGEM_DE_SENHAS_DIFERENTES;
import static mobi.vesti.properties.CadastroVendedorProperties.MENSAGEM_EMAIL_INVALIDO;
import static mobi.vesti.properties.CadastroVendedorProperties.POP_VOCE_DEVE_PREENCHER_CAMPOS_CORRETAMENTE;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class CadastroVendedorTest extends TestContext {

    public HomePage homePage;
    public CadastroVendedorPageObject cadastroVendedorPage;

    @BeforeClass()
    public void before() {
        cadastroVendedorPage = PageFactory.initElements(driver, CadastroVendedorPageObject.class);
        homePage = PageFactory.initElements(driver, HomePage.class);
    }

    @Test
    public void testarFluxoDeCadastroComSucessoDeNovoVendedor()
            throws InterruptedException, ParseException {
        driver.navigate().to(homePage.getUrl());
        List<AnunciosVendasDto> anunciosVendasDtos = homePage.buscarTodosAnunciosComPreco();
        assertTrue(anunciosVendasDtos.isEmpty());
        homePage.clicarEmAnuncioDeProduto();
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
        cadastroVendedorPage.preencherFormularioCnpj(vendedorDto);
        cadastroVendedorPage.finalizarCadastro();
        anunciosVendasDtos = homePage.buscarTodosAnunciosComPreco();
        assertTrue(anunciosVendasDtos.isEmpty());
    }

    @Test
    public void testarValidacaoDeCamposNoCadastroDeVendedor() throws Exception {
        driver.navigate().to(homePage.getUrl());
        List<AnunciosVendasDto> anunciosVendasDtos = homePage.buscarTodosAnunciosComPreco();
        assertTrue(anunciosVendasDtos.isEmpty(), "Foram encontrados anuncios de produtos com preço.");
        homePage.clicarEmAnuncioDeProduto();
        cadastroVendedorPage.getCnpjCpfOuEmail().click();
        sendKeys("123asdasdsdada", cadastroVendedorPage.getCnpjCpfOuEmail());
        cadastroVendedorPage.getBotaoContinuar().click();
        assertEquals(StringUtils.trimToNull(cadastroVendedorPage.getMensagemCpfOuCnpjInvalidos().getText()), MENSAGEM_CPF_CNPJ_INVALIDOS);
        cadastroVendedorPage.getCnpjCpfOuEmail().clear();
        sendKeys("18733438000144", cadastroVendedorPage.getCnpjCpfOuEmail());
        assertEquals(cadastroVendedorPage.getCnpjCpfOuEmailText(), Mascara.cnpj("18733438000144"));
        AcoesCustomizadas.focarNoElemento(cadastroVendedorPage.getBotaoContinuar());
        cadastroVendedorPage.getBotaoVoltar().click();
        homePage.clicarEmAnuncioDeProduto();
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
