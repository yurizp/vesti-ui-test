package mobi.vesti.test.pages;

import lombok.SneakyThrows;
import mobi.vesti.client.VestClient;
import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.CarrinhoPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.CarrinhoProperties;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.properties.ProdutosProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import mobi.vesti.utils.RetentarUmaVez;
import org.apache.commons.lang3.StringUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarrinhoTest extends TestContext {

    private HomePageObject homePage;
    private CadastroVendedorPageObject cadastroVendedorPage;
    private CarrinhoPageObject carrinhoPage;
    private LoginPageObject loginPage;

    @BeforeClass
    public void before() {
        homePage = PageFactory.initElements(driver, HomePageObject.class);
        loginPage = new LoginPageObject(driver);
        carrinhoPage = new CarrinhoPageObject(driver);
        cadastroVendedorPage = PageFactory.initElements(driver, CadastroVendedorPageObject.class);
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarAdicionarERemoverProdutosDoCarrinho() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        //Adiciona os valores defaults ao estoque (4 peças de cada cor) via API
        VestClient.adicionarEstoque(ProdutosProperties.POLO.ID, ProdutosProperties.POLO.ESTOQUE_REQUEST);

        // Faz login clicando em um produto
        homePage.clicarEmAnuncioDeProdutoSemPreco("POLO");
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("POLO");
        carrinhoPage.polo.tamanhoP.verde.click();
        carrinhoPage.polo.tamanhoP.azul.click();
        carrinhoPage.polo.tamanhoG.rosa.click();
        carrinhoPage.polo.tamanhoG.rosa.click();
        carrinhoPage.carrinhoIcone.click();
        carrinhoPage.polo.tamanhoGG.cinza.click();
        Thread.sleep(2000);
        AcoesCustomizadas.clicarEManterPressionado(carrinhoPage.polo.tamanhoG.rosa);
        AcoesCustomizadas.focarNoElemento(carrinhoPage.polo.tamanhoG.azul);
        carrinhoPage.polo.tamanhoG.rosa.click();
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(2000);
        carrinhoPage.validaMensagemDePedidoEnviado();
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarRealizarPedidoClientePossuiCadastro() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        VestClient.adicionarEstoque(ProdutosProperties.CAMISETA.ID, ProdutosProperties.CAMISETA.ESTOQUE_REQUEST);
        homePage.clicarEmAnuncioDeProdutoSemPreco("CAMISETA");
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        carrinhoPage.camiseta.tamanhoP.getBranco().click();
        carrinhoPage.camiseta.tamanhoP.getAmarelo().click();
        carrinhoPage.camiseta.tamanhoM.getAzul().click();
        carrinhoPage.camiseta.tamanhoG.getPreto().click();
        carrinhoPage.camiseta.tamanhoGG.getVinho().click();
        carrinhoPage.carrinhoIcone.click();
        validarInformacoesDeContato();
        Thread.sleep(1000);
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();

    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarProdutoMaximoDisponivel() {
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        VestClient.adicionarEstoque(ProdutosProperties.CAMISETA.ID, ProdutosProperties.CAMISETA.ESTOQUE_REQUEST);
        homePage.clicarEmAnuncioDeProdutoSemPreco("CAMISETA");
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        validarMensagemDeLimiteAtingido(carrinhoPage.camiseta.tamanhoGG.getVinho(), 4);
        assertThat("3").isEqualTo(carrinhoPage.camiseta.tamanhoGG.getVinho().getText());
        carrinhoPage.carrinhoIcone.click();
        validarInformacoesDeContato();
        Thread.sleep(1000);
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();
        carrinhoPage.botaoVoltar.click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        carrinhoPage.camiseta.tamanhoGG.getVinho().click();
        assertThat(" ").isEqualTo(carrinhoPage.camiseta.tamanhoGG.getVinho().getText());
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarMensagensDeCarrinhoAtualizadoQuandoAdicionadoRemovidoItensDoCarrinho() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);

        // Abrir o item Calca Jeans Milanda
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.CALCA_JEANS_MILANDA.NOME);
        Thread.sleep(5000);
        assertThat(carrinhoPage.calcaJeansMilanda.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.calcaJeansMilanda.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.calcaJeansMilanda.quantidade.getText()).isEqualTo("0");

        //Adicionar um pack e validar a mensagem
        carrinhoPage.calcaJeansMilanda.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.mensagemCarrinhoAtualizado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_CARRINHO_ATUALIZADO);
        assertThat(carrinhoPage.calcaJeansMilanda.quantidade.getText()).isEqualTo("1");
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");

        //Remover o item adicionado anteriormente e validar a mensagem
        carrinhoPage.calcaJeansMilanda.botaoRemoverItem.click();
        assertThat(carrinhoPage.mensagemCarrinhoAtualizado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_PRODUTO_REMOVIDO);
        assertThat(carrinhoPage.calcaJeansMilanda.quantidade.getText()).isEqualTo("0");
        assertThat(AcoesCustomizadas.elementoExiste(carrinhoPage.carrinhoQuantidadeItens)).isFalse();

    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarCalculoDeValorTotalNoCarrinho() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);

        // Adicionar um Vestido tamanho GG vermelho ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.VESTIDO_LONGO.NOME);
        carrinhoPage.vestidoLongo.tamanhoGG.vermelho.click();
        assertThat(carrinhoPage.vestidoLongo.tamanhoGG.vermelho.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();
        Thread.sleep(1200);
        homePage.validarQuePrecosEstaoSendoExibidos();
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");

        // Adicionar uma Blusa tamanho G preta ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.BLUSA.NOME);
        carrinhoPage.blusa.tamanhoGG.preto.click();
        assertThat(carrinhoPage.blusa.tamanhoGG.preto.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();
        Thread.sleep(1200);
        homePage.validarQuePrecosEstaoSendoExibidos();
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("2");

        //Validar que possui 2 paças e o valor total é igual a R$ 299.98
        carrinhoPage.carrinhoIcone.click();
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("2 pc    R$ 299,98");
    }


    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRemocaoDeItensDoCarrinhoPeloIconeX() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);

        // Adicionar o item Vestido Longo GG vermelho ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.VESTIDO_LONGO.NOME);
        carrinhoPage.vestidoLongo.tamanhoGG.vermelho.click();
        assertThat(carrinhoPage.vestidoLongo.tamanhoGG.vermelho.getText()).isEqualTo("1");
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();
        Thread.sleep(1000);

        // Clicar para remover o item Vestido Longo GG vermelho do carrinho e validar a mensagem
        carrinhoPage.carrinhoIcone.click();
        validarInformacoesDeContato();
        carrinhoPage.vestidoLongo.iconeXRemoverItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.mensagemCarrinhoAtualizado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_PRODUTO_REMOVIDO);
        homePage.validarQuePrecosEstaoSendoExibidos();
    }


    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRemocaoDoCarrinhoPressionandoESegurandoAQuandidadeDoItem() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);

        //Adicionar Blusa Tamanho G branca ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.BLUSA.NOME);
        carrinhoPage.blusa.tamanhoG.branco.click();
        assertThat(carrinhoPage.blusa.tamanhoG.branco.getText()).isEqualTo("1");
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();

        //Remover Blusa do carrinho e validar quantidade de peças e mensagem
        carrinhoPage.carrinhoIcone.click();
        validarInformacoesDeContato();
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("1 pc    R$ 99,99");
        AcoesCustomizadas.clicarEManterPressionado(carrinhoPage.blusa.tamanhoG.branco);
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("0 pc    R$ 0,00");
        assertThat(carrinhoPage.mensagemCarrinhoAtualizado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_PRODUTO_REMOVIDO);

    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarMaximoDisponivel() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);

        //Adicionar Pack Jeans ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.PACK_JEANS.NOME);
        Thread.sleep(1000);
        assertThat(carrinhoPage.packJeans.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.packJeans.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.packJeans.quantidade.getText()).isEqualTo("0");

        //Adicionar um pack e validar a mensagem
        validarMensagemDeLimiteAtingido(carrinhoPage.packJeans.botaoAdicionarItem, 4);
    }

    private void validarInformacoesDeContato(){
        assertThat(carrinhoPage.nomeVendedor.getText()).isEqualTo(CarrinhoProperties.NOME_VENDEDOR);
        assertThat(carrinhoPage.telefoneVendedor.getText()).isEqualTo(CarrinhoProperties.TELEFONE_VENDEDOR);
        assertThat(carrinhoPage.iniciaisVendedor.getText()).isEqualTo(CarrinhoProperties.INICIAIS_VENDEDOR);
    }

    private void validarMensagemDeLimiteAtingido(WebElement element, int numeroEstoque) {
        int numeroCliques = 0;
        while (numeroCliques < 10) {
            element.click();
            numeroCliques++;
            String text = carrinhoPage.mensagemMaximoDisponivel.getText();
            if (StringUtils.isNotBlank(text)) {
                assertThat(CarrinhoProperties.MENSAGEM_MAXIMO_DISPONIVEL).isEqualTo(text);
                assertThat(numeroCliques).isEqualTo(numeroCliques);
                return;
            }
        }
        throw new RuntimeException("Não foi possivel validar a mensagem de limite maximo.");
    }
}
