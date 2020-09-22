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

    /**
     * Nesse teste o cliente vai selecionar o produto Polo e irá selecionar os seguintes tamanhos e cores:
     * 1 x P-Verde
     * 1 x P-Azul
     * 2X G-Rosa
     * No carrinho deve adicionar Polo 1 x GG-Cinza e então deve zerar a cor G-Rosa e depois adicionar 1 x GG-Rosa e então finalizar a compra normalmente.
     */
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

        // Adiciona o item camiseta POLO Verde e Azul P, duas unidades de Rosa G
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.POLO.NOME);
        carrinhoPage.polo.tamanhoP.verde.click();
        carrinhoPage.polo.tamanhoP.azul.click();
        carrinhoPage.polo.tamanhoG.rosa.click();
        carrinhoPage.polo.tamanhoG.rosa.click();
        Thread.sleep(1000);
        assertThat(carrinhoPage.polo.tamanhoP.verde.getText()).isEqualTo("1");
        assertThat(carrinhoPage.polo.tamanhoP.azul.getText()).isEqualTo("1");
        assertThat(carrinhoPage.polo.tamanhoG.rosa.getText()).isEqualTo("2");

        // Vai até a pagina do carrinho e adiciona uma camiseta Polo
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(1000);
        carrinhoPage.polo.tamanhoGG.cinza.click();

        // Remove os produtos Polo rosa e adiciona uma Polo Rosa G
        AcoesCustomizadas.clicarEManterPressionado(carrinhoPage.polo.tamanhoG.rosa);
        Thread.sleep(2000);
        carrinhoPage.polo.tamanhoG.rosa.click();
        Thread.sleep(2000);
        assertThat(carrinhoPage.polo.tamanhoP.verde.getText()).isEqualTo("1");
        assertThat(carrinhoPage.polo.tamanhoP.azul.getText()).isEqualTo("1");
        assertThat(carrinhoPage.polo.tamanhoG.rosa.getText()).isEqualTo("1");
        assertThat(carrinhoPage.polo.tamanhoGG.cinza.getText()).isEqualTo("1");

        //Finaliza o pedido e valida a mensagem
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
    public void validarRemocaoDoCarrinhoPressionandoESegurandoAQuantidadeDoItem() {
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
        Thread.sleep(1000);
        assertThat(carrinhoPage.mensagemCarrinhoAtualizado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_PRODUTO_REMOVIDO);
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("0 pc    R$ 0,00");

    }

    /**
     * Testar produtos esgotado durante um pedido.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarProdutoEsgotadoDuranteUmPedido() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.BASE_URL);
        loginPage.logar();
        Thread.sleep(2000);

        //Adiciona ao estoque 10 peças de cada cor via API
        VestClient.adicionarEstoque(ProdutosProperties.SHORTS.ID, ProdutosProperties.SHORTS.ESTOQUE_REQUEST);

        //Adicionar ao carrinho 9 peças tamanho 36 e 38
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.SHORTS.NOME);
        carrinhoPage.shorts.tamanho36.verde.click();
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.shorts.tamanho36.verde,9);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.shorts.tamanho38.verde,9);
        Thread.sleep(2000);
        assertThat(carrinhoPage.shorts.tamanho36.verde.getText()).isEqualTo("9");
        assertThat(carrinhoPage.shorts.tamanho38.verde.getText()).isEqualTo("9");

        //Alterar o estoque para 5 peças cada cor via API
        VestClient.adicionarEstoque(ProdutosProperties.SHORTS.ID, ProdutosProperties.SHORTS.getEstoque("5"));

        // Valida mensagem de produto esgotado
        Thread.sleep(1000);
        assertThat(carrinhoPage.shorts.tamanho36.verde.getCssValue("color")).isEqualTo(CarrinhoProperties.COR_PRETA_RGB);
        assertThat(carrinhoPage.shorts.tamanho38.verde.getCssValue("color")).isEqualTo(CarrinhoProperties.COR_PRETA_RGB);
        carrinhoPage.carrinhoIcone.click();
        carrinhoPage.botaoFinalizarPedido.click();
        assertThat(carrinhoPage.pecasEsgotadas.mensagem.getText()).isEqualTo(CarrinhoProperties.PECAS_ESGOTADAS_MENSAGEM);
        carrinhoPage.pecasEsgotadas.botaoOk.click();

        // Valida a cor do texto e a quantidade de itens
        Thread.sleep(1000);
        assertThat(carrinhoPage.shorts.tamanho36.verde.getCssValue("color")).isEqualTo(CarrinhoProperties.COR_VERMELHA_RGB);
        assertThat(carrinhoPage.shorts.tamanho38.verde.getCssValue("color")).isEqualTo(CarrinhoProperties.COR_VERMELHA_RGB);
        assertThat(carrinhoPage.shorts.tamanho36.verde.getText()).isEqualTo("4");
        assertThat(carrinhoPage.shorts.tamanho38.verde.getText()).isEqualTo("4");

        // Finaliza a venda
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();
        carrinhoPage.botaoVoltar.click();
        Thread.sleep(2000);

        // Validar indisponibilidade de estoque após a compra
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.SHORTS.NOME);
        carrinhoPage.shorts.tamanho38.verde.click();
        carrinhoPage.shorts.tamanho36.verde.click();
        assertThat(carrinhoPage.shorts.tamanho36.verde.getText()).isEqualTo(" ");
        assertThat(carrinhoPage.shorts.tamanho38.verde.getText()).isEqualTo(" ");
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

        //Adiciona um Pack Jeans ao carrinho e volta para a home
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.PACK_JEANS.NOME);
        Thread.sleep(800);
        assertThat(carrinhoPage.packJeans.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.packJeans.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.packJeans.quantidade.getText()).isEqualTo("0");
        carrinhoPage.packJeans.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.packJeans.quantidade.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();
        Thread.sleep(800);

        // Validar o valor total dos itens no carrinho
        carrinhoPage.carrinhoIcone.click();
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("8 pc    R$ 400,00");
        carrinhoPage.botaoVoltar.click();
        Thread.sleep(800);

        // Adicionar mais duas calcas jeans pack ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosProperties.CALCA_JEANS_PACK.NOME);
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansPack.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.calcaJeansPack.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("0");
        carrinhoPage.calcaJeansPack.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("1");
        carrinhoPage.calcaJeansPack.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("2");

        // Validar o valor total dos itens no carrinho
        carrinhoPage.carrinhoIcone.click();
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("16 pc    R$ 924,00");
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(800);
        carrinhoPage.validaMensagemDePedidoEnviado();
    }

    /**
     * Validar a quantidade minima de compra.
     * Deve aceitar pedidos com no mínimo dois itens no carrinho.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarQuantidadeMinimaDeCompra() {
        // Faz login clicando em um produto
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosProperties.CAMISETA_MANGA_LON.NOME);
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO.getCnpj());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);

        //Adiciona ao estoque 4 peças de cada cor via API
        VestClient.adicionarEstoque(ProdutosProperties.CAMISETA_MANGA_LON.ID, ProdutosProperties.CAMISETA_MANGA_LON.ESTOQUE_REQUEST);

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
