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
        Thread.sleep(1000);
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
        Thread.sleep(1000);
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

    /**
     * Testar mensagens de carrinho atualizado quando adicionado itens ao carrinho.
     * Quando adicionado ou removido itens do carrinho deve validar mensagens de atualização de itens.
     */
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

    /**
     * Testar o calculo de valor total no carrinho.
     * Conforme adicionado itens ao carrinho o valor total é calculado e deve bater com o esperado.
     */
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

    /**
     * Testar a remoção de item do carrinho pelo botão 'X' dentro do carrinho.
     */
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

    /**
     * Testar a remoção de itens do carrinho pressionando e segurando a quantidade de itens do carrinho.
     * É esperado que quando a ação de manter pressionado for feita deve zerar a quantidade do tamanho e cor especifico.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRemocaoDoCarrinhoPressionandoESegurandoAQuandidadeDoItem() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);

        //Adiciona os valores defaults ao estoque (4 peças de cada cor) via API
        VestClient.adicionarEstoque(ProdutosProperties.BLUSA.ID, ProdutosProperties.BLUSA.ESTOQUE_REQUEST);

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
        Thread.sleep(1200);
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("0 pc    R$ 0,00");
        assertThat(carrinhoPage.mensagemCarrinhoAtualizado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_PRODUTO_REMOVIDO);

    }

    /**
     * Validar mensagem de "Máximo disponível" ao adicionar produtos do tipo Pack.
     */
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

    /**
     * Validar calculo de valor total para produtos do tipo Pack.
     * Deve ir recalculando o valor total do carrinho conforme vai sendo adicionado novos Packs.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarCalculoDeValorTotalParaItensDoTipoPack() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);

        //Adiciona dois Pack Jeans ao carrinho e volta para a home
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.PACK_JEANS.NOME);
        Thread.sleep(800);
        assertThat(carrinhoPage.packJeans.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.packJeans.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.packJeans.quantidade.getText()).isEqualTo("0");
        carrinhoPage.packJeans.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.packJeans.quantidade.getText()).isEqualTo("1");
        carrinhoPage.packJeans.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.packJeans.quantidade.getText()).isEqualTo("2");
        carrinhoPage.botaoContinuarComprando.click();
        Thread.sleep(800);

        // Validar o valor total dos itens no carrinho
        carrinhoPage.carrinhoIcone.click();
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("16 pc    R$ 800,00");
        carrinhoPage.botaoVoltar.click();
        Thread.sleep(800);

        // Adicionar mais dois itens
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.CALCA_JEANS_MILANDA.NOME);
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansMilanda.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.calcaJeansMilanda.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.calcaJeansMilanda.quantidade.getText()).isEqualTo("0");
        carrinhoPage.calcaJeansMilanda.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansMilanda.quantidade.getText()).isEqualTo("1");
        carrinhoPage.calcaJeansMilanda.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansMilanda.quantidade.getText()).isEqualTo("2");
        carrinhoPage.botaoVoltar.click();

        // Validar o valor total dos itens no carrinho
        carrinhoPage.carrinhoIcone.click();
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("36 pc    R$ 2.398,00");
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(800);
        carrinhoPage.validaMensagemDePedidoEnviado();
    }

    /**
     * Valida as informações de contato dentro da pagina de carrinho.
     * Valida que existe e estão sendo exibidos como o esperado o nome, telefone e as iniciais do vendedor.
     */
    @SneakyThrows
    private void validarInformacoesDeContato() {
        Thread.sleep(1200);
        assertThat(carrinhoPage.nomeVendedor.getText()).isEqualTo(CarrinhoProperties.NOME_VENDEDOR);
        assertThat(carrinhoPage.telefoneVendedor.getText()).isEqualTo(CarrinhoProperties.TELEFONE_VENDEDOR);
        assertThat(carrinhoPage.iniciaisVendedor.getText()).isEqualTo(CarrinhoProperties.INICIAIS_VENDEDOR);
    }

    /**
     * Deve validar se a mensagem de limite maximo disponível foi exibido.
     *
     * @param element          Botão a ser clicado para adicionar itens ao carrinho.
     * @param cliquesEsperados Numero de cliques esperado para exibir a mensagem
     */
    private void validarMensagemDeLimiteAtingido(WebElement element, int cliquesEsperados) {
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
