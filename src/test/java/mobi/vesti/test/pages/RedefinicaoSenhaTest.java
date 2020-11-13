package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.EsqueceuSenhaPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import mobi.vesti.utils.Mascara;
import mobi.vesti.utils.RetentarUmaVez;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class RedefinicaoSenhaTest extends TestContext {

    public HomePageObject homePage;
    public LoginPageObject loginPage;
    public EsqueceuSenhaPageObject esqueceuSenhaPageObject;
    public CadastroVendedorPageObject cadastroVendedorPage;

    @BeforeClass
    public void before() {
        homePage = new HomePageObject(driver);
        cadastroVendedorPage = new CadastroVendedorPageObject(driver);
        esqueceuSenhaPageObject = new EsqueceuSenhaPageObject(driver);
        loginPage = new LoginPageObject(driver);
    }

    /**
     * Redefinição de senha utilizando o E-mail
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRedefinicaoDeSenhaUsandoCpfECnpj() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);

        // Faz clica em um produto
        homePage.clicarEmAnuncioDeProdutoSemPreco();

        // Preenche os dados da tela de login utilizando o email
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys("14091416000177");
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.botaoEsqueciMinhaSenha.click();
        Thread.sleep(2000);

        // Validar mensagem de senha enviada
        assertThat(esqueceuSenhaPageObject.titulo.getText()).contains("VERIFIQUE SEU E-MAIL");
        assertThat(esqueceuSenhaPageObject.mensagem.getText()).contains("Enviamos um link de troca de senha para: t*************@gmail.com.");

        // Validar voltar pelo botao OK
        esqueceuSenhaPageObject.botaoOk.click();
        assertThat(cadastroVendedorPage.getCnpjCpfOuEmailText()).isEqualTo(Mascara.cnpj("14091416000177"));

        // Validar mensagem de senha enviada
        loginPage.botaoEsqueciMinhaSenha.click();
        Thread.sleep(2000);
        assertThat(esqueceuSenhaPageObject.titulo.getText()).contains("VERIFIQUE SEU E-MAIL");
        assertThat(esqueceuSenhaPageObject.mensagem.getText()).contains("Enviamos um link de troca de senha para: t*************@gmail.com.");

        // Validar voltar pelo botao voltar
        esqueceuSenhaPageObject.botaoVoltar.click();
        assertThat(cadastroVendedorPage.getCnpjCpfOuEmailText()).isEqualTo(Mascara.cnpj("14091416000177"));
    }

    /**
     * Redefinição de senha utilizando o E-mail
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRedefinicaoDeSenhaPeloEmail() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);

        // Faz clica em um produto
        homePage.clicarEmAnuncioDeProdutoSemPreco();

        // Preenche os dados da tela de login utilizando o email
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys("testeclienteqa@gmail.com");
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.botaoEsqueciMinhaSenha.click();
        Thread.sleep(2000);

        // Validar mensagem de senha enviada
        assertThat(esqueceuSenhaPageObject.titulo.getText()).contains("VERIFIQUE SEU E-MAIL");
        assertThat(esqueceuSenhaPageObject.mensagem.getText()).contains("Enviamos um link de troca de senha para: t*************@gmail.com.");

        // Validar voltar pelo botao OK
        esqueceuSenhaPageObject.botaoOk.click();
        assertThat(cadastroVendedorPage.getCnpjCpfOuEmailText()).isEqualTo("testeclienteqa@gmail.com");

        // Validar mensagem de senha enviada
        loginPage.botaoEsqueciMinhaSenha.click();
        Thread.sleep(2000);
        assertThat(esqueceuSenhaPageObject.titulo.getText()).contains("VERIFIQUE SEU E-MAIL");
        assertThat(esqueceuSenhaPageObject.mensagem.getText()).contains("Enviamos um link de troca de senha para: t*************@gmail.com.");

        // Validar voltar pelo botao voltar
        esqueceuSenhaPageObject.botaoVoltar.click();
        assertThat(cadastroVendedorPage.getCnpjCpfOuEmailText()).isEqualTo("testeclienteqa@gmail.com");
    }

    /**
     * Validar formatos aceitos e redefinição de senha.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRedefinicaoDeSenhaPeloEmailEFormatos() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);

        // Faz clica em um produto
        homePage.clicarEmAnuncioDeProdutoSemPreco();

        // Preenche os dados da tela de login utilizando o email
        cadastroVendedorPage.getCnpjCpfOuEmail().clear();
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys("testeclienteqa@gmail.com");
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.botaoEsqueciMinhaSenha.click();
        Thread.sleep(2000);

        // Validar mensagem de senha enviada
        assertThat(esqueceuSenhaPageObject.titulo.getText()).contains("VERIFIQUE SEU E-MAIL");
        assertThat(esqueceuSenhaPageObject.mensagem.getText()).contains("Enviamos um link de troca de senha para: t*************@gmail.com.");
        esqueceuSenhaPageObject.linkNaoTemMaisAcessoAoEmail.click();

        // Tentar entrar com um email invalido
        AcoesCustomizadas.sendKeys("testeclienteqa@gmail", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(500);
        assertThat(cadastroVendedorPage.popupMensagem.getText()).contains("Você deve preencher o campo corretamente!");
        esqueceuSenhaPageObject.alterarEmail.clear();
        AcoesCustomizadas.sendKeys("testeclienteqa@gmail", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(500);
        assertThat(cadastroVendedorPage.popupMensagem.getText()).contains("Você deve preencher o campo corretamente!");
        esqueceuSenhaPageObject.alterarEmail.clear();
        AcoesCustomizadas.sendKeys("testeclienteqa@.com", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(500);
        assertThat(cadastroVendedorPage.popupMensagem.getText()).contains("Você deve preencher o campo corretamente!");
        esqueceuSenhaPageObject.alterarEmail.clear();
        AcoesCustomizadas.sendKeys("testeclienteqagmail.com", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(500);
        assertThat(cadastroVendedorPage.popupMensagem.getText()).contains("Você deve preencher o campo corretamente!");

        // Validar voltar pelo botao OK
        esqueceuSenhaPageObject.alterarEmail.clear();
        AcoesCustomizadas.sendKeys("testeclienteqa@gmail.com", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(1000);
        assertThat(esqueceuSenhaPageObject.alerta.mensage.getText()).contains("Seus dados foram enviados com sucesso, em breve entraremos em contato");
        esqueceuSenhaPageObject.alerta.botaoOK.click();
        assertThat(cadastroVendedorPage.getCnpjCpfOuEmailText()).isEqualTo("testeclienteqa@gmail.com");

        // Validar mensagem de senha enviada
        loginPage.botaoEsqueciMinhaSenha.click();
        Thread.sleep(2000);
        assertThat(esqueceuSenhaPageObject.titulo.getText()).contains("VERIFIQUE SEU E-MAIL");
        assertThat(esqueceuSenhaPageObject.mensagem.getText()).contains("Enviamos um link de troca de senha para: t*************@gmail.com.");

        // Validar voltar pelo botao voltar
        esqueceuSenhaPageObject.botaoVoltar.click();
        assertThat(cadastroVendedorPage.getCnpjCpfOuEmailText()).isEqualTo("testeclienteqa@gmail.com");
    }


    /**
     * Validar formatos aceitos e redefinição de senha.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRedefinicaoDeSenhaPeloCpfEFormatos() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);

        // Faz clica em um produto
        homePage.clicarEmAnuncioDeProdutoSemPreco();

        // Preenche os dados da tela de login utilizando o email
        cadastroVendedorPage.getCnpjCpfOuEmail().clear();
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys("14091416000177");
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.botaoEsqueciMinhaSenha.click();
        Thread.sleep(2000);

        // Validar mensagem de senha enviada
        assertThat(esqueceuSenhaPageObject.titulo.getText()).contains("VERIFIQUE SEU E-MAIL");
        assertThat(esqueceuSenhaPageObject.mensagem.getText()).contains("Enviamos um link de troca de senha para: t*************@gmail.com.");
        esqueceuSenhaPageObject.linkNaoTemMaisAcessoAoEmail.click();

        // Tentar entrar com um email invalido
        AcoesCustomizadas.sendKeys("testeclienteqa@gmail", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(500);
        assertThat(cadastroVendedorPage.popupMensagem.getText()).contains("Você deve preencher o campo corretamente!");
        esqueceuSenhaPageObject.alterarEmail.clear();
        AcoesCustomizadas.sendKeys("testeclienteqa@gmail", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(500);
        assertThat(cadastroVendedorPage.popupMensagem.getText()).contains("Você deve preencher o campo corretamente!");
        esqueceuSenhaPageObject.alterarEmail.clear();
        AcoesCustomizadas.sendKeys("testeclienteqa@.com", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(500);
        assertThat(cadastroVendedorPage.popupMensagem.getText()).contains("Você deve preencher o campo corretamente!");
        esqueceuSenhaPageObject.alterarEmail.clear();
        AcoesCustomizadas.sendKeys("testeclienteqagmail.com", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(500);
        assertThat(cadastroVendedorPage.popupMensagem.getText()).contains("Você deve preencher o campo corretamente!");

        // Validar voltar pelo botao OK
        esqueceuSenhaPageObject.alterarEmail.clear();
        AcoesCustomizadas.sendKeys("testeclienteqa@gmail.com", esqueceuSenhaPageObject.alterarEmail);
        esqueceuSenhaPageObject.botaoOk.click();
        Thread.sleep(1000);
        assertThat(esqueceuSenhaPageObject.alerta.mensage.getText()).contains("Seus dados foram enviados com sucesso, em breve entraremos em contato");
        esqueceuSenhaPageObject.alerta.botaoOK.click();
        assertThat(cadastroVendedorPage.getCnpjCpfOuEmailText()).isEqualTo(Mascara.cnpj("14091416000177"));

        // Validar mensagem de senha enviada
        loginPage.botaoEsqueciMinhaSenha.click();
        Thread.sleep(2000);
        assertThat(esqueceuSenhaPageObject.titulo.getText()).contains("VERIFIQUE SEU E-MAIL");
        assertThat(esqueceuSenhaPageObject.mensagem.getText()).contains("Enviamos um link de troca de senha para: t*************@gmail.com.");

        // Validar voltar pelo botao voltar
        esqueceuSenhaPageObject.botaoVoltar.click();
        assertThat(cadastroVendedorPage.getCnpjCpfOuEmailText()).isEqualTo(Mascara.cnpj("14091416000177"));
    }


}