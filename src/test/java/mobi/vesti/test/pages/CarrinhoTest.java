package mobi.vesti.test.pages;

import com.google.common.collect.ImmutableList;
import lombok.SneakyThrows;
import mobi.vesti.client.VestClient;
import mobi.vesti.client.request.ItensRequestVestClient;
import mobi.vesti.pageobjects.CadastroVendedorPageObject;
import mobi.vesti.pageobjects.CarrinhoPageObject;
import mobi.vesti.pageobjects.HomePageObject;
import mobi.vesti.pageobjects.LoginPageObject;
import mobi.vesti.properties.AmbienteProperties;
import mobi.vesti.properties.CarrinhoProperties;
import mobi.vesti.properties.ConfiguracoesGlobais;
import mobi.vesti.properties.InfoProperties;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.properties.ProdutosPepitaModasProperties;
import mobi.vesti.properties.ProdutosQaModas2ModasProperties;
import mobi.vesti.properties.ProdutosQaModasProperties;
import mobi.vesti.test.TestContext;
import mobi.vesti.utils.AcoesCustomizadas;
import mobi.vesti.utils.Mascara;
import mobi.vesti.utils.RetentarUmaVez;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.api.Assertions;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static mobi.vesti.properties.EstoqueProperties.getVerde;
import static mobi.vesti.properties.EstoqueProperties.getVermelho;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CarrinhoTest extends TestContext {

    private HomePageObject homePage;
    private CadastroVendedorPageObject cadastroVendedorPage;
    private CarrinhoPageObject carrinhoPage;
    private LoginPageObject loginPage;

    /**
     * Nesse teste o cliente vai selecionar o produto Polo e irá selecionar os seguintes tamanhos e cores:
     * 1 x P-Verde
     * 1 x P-Azul
     * 2X G-Rosa
     * No carrinho deve adicionar Polo 1 x GG-Cinza e então deve zerar a cor G-Rosa e depois adicionar 1 x GG-Rosa e então finalizar a compra normalmente.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarAdicionarERemoverProdutosDoCarrinho() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        // Adiciona ao estoque 10 peças de cada cor
        VestClient.adicionarEstoque(ProdutosQaModasProperties.POLO.ID, ProdutosQaModasProperties.POLO.getEstoque("10"), AmbienteProperties.QAMODAS);

        // Faz login clicando em um produto
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosQaModasProperties.POLO.NOME);
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS.getDocumento());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);

        // Adiciona o item camiseta POLO Verde e Azul P, duas unidades de Rosa G
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.POLO.NOME);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.polo.tamanhoP.verde, 6);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.polo.tamanhoP.azul, 7);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.polo.tamanhoG.rosa, 2);
        Thread.sleep(1000);
        assertThat("6").isEqualTo(carrinhoPage.polo.tamanhoP.verde.getText());
        assertThat("7").isEqualTo(carrinhoPage.polo.tamanhoP.azul.getText());
        assertThat("2").isEqualTo(carrinhoPage.polo.tamanhoG.rosa.getText());

        // Vai até a pagina do carrinho e adiciona uma camiseta Polo
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(1000);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.polo.tamanhoG.cinza, 4);
        Thread.sleep(1000);

        // Remove os produtos Polo rosa e adiciona uma Polo Rosa G
        AcoesCustomizadas.clicarEManterPressionado(carrinhoPage.polo.tamanhoG.rosa);
        Thread.sleep(3000);
        assertThat("6").isEqualTo(carrinhoPage.polo.tamanhoP.verde.getText());
        assertThat("7").isEqualTo(carrinhoPage.polo.tamanhoP.azul.getText());
        assertThat("0").isEqualTo(carrinhoPage.polo.tamanhoG.rosa.getText());
        assertThat("4").isEqualTo(carrinhoPage.polo.tamanhoG.cinza.getText());
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("17 pc    R$ 508,30");

        //Finaliza o pedido e valida a mensagem
        Thread.sleep(1000);
        AcoesCustomizadas.clicarViaJavaScript(carrinhoPage.botaoFinalizarPedido);
        carrinhoPage.validaMensagemDePedidoEnviado();
    }

    @BeforeClass
    public void before() {
        homePage = PageFactory.initElements(driver, HomePageObject.class);
        loginPage = new LoginPageObject(driver);
        carrinhoPage = new CarrinhoPageObject(driver);
        cadastroVendedorPage = PageFactory.initElements(driver, CadastroVendedorPageObject.class);
    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarRealizarPedidoClientePossuiCadastro() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        VestClient.adicionarEstoque(ProdutosQaModasProperties.CAMISETA.ID, ProdutosQaModasProperties.CAMISETA.ESTOQUE_REQUEST, AmbienteProperties.QAMODAS);
        homePage.clicarEmAnuncioDeProdutoSemPreco("CAMISETA");
        Thread.sleep(1000);
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS.getDocumento());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoComPreco("CAMISETA");
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.camiseta.tamanhoP.getBranco(), 5);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.camiseta.tamanhoP.getAmarelo(), 5);
        carrinhoPage.camiseta.tamanhoM.getAzul().click();
        carrinhoPage.camiseta.tamanhoG.getPreto().click();
        carrinhoPage.camiseta.tamanhoGG.getVinho().click();
        carrinhoPage.carrinhoIcone.click();
        validarInformacoesDeContato();
        Thread.sleep(1000);
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(1000);
        carrinhoPage.validaMensagemDePedidoEnviado();

    }

    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void testarProdutoMaximoDisponivel() {
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);

        // Altera o estoque de Camiseta Estampada
        List<Map<String, String>> tamanhosQuantidades = Arrays.asList(
                Collections.singletonMap("P", "10"),
                Collections.singletonMap("M", "4"),
                Collections.singletonMap("G", "4"),
                Collections.singletonMap("GG", "3")
        );
        VestClient.adicionarEstoque(ProdutosQaModasProperties.CAMISETA_ESTAMPADA.ID, ProdutosQaModasProperties.CAMISETA_ESTAMPADA.getEstoque(tamanhosQuantidades), AmbienteProperties.QAMODAS);

        // Loga clicando no produto Camiseta Estampada
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosQaModasProperties.CAMISETA_ESTAMPADA.NOME);
        Thread.sleep(2000);
        AcoesCustomizadas.sendKeys(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS.getDocumento(), cadastroVendedorPage.getCnpjCpfOuEmail());
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);

        // Valida a mensagem de maximo
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.CAMISETA_ESTAMPADA.NOME);
        validarMensagemDeLimiteAtingido(carrinhoPage.camisetaEstampada.tamanhoGG.preto, 3);
        Thread.sleep(7000);
        validarMensagemDeLimiteAtingido(carrinhoPage.camisetaEstampada.tamanhoG.preto, 4);
        Thread.sleep(7000);
        validarMensagemDeLimiteAtingido(carrinhoPage.camisetaEstampada.tamanhoM.preto, 4);
        Thread.sleep(7000);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.camisetaEstampada.tamanhoP.preto, 6);

        // Validar numero de itens selecionados
        assertThat(carrinhoPage.camisetaEstampada.tamanhoGG.preto.getText()).isEqualTo("2");
        assertThat(carrinhoPage.camisetaEstampada.tamanhoG.preto.getText()).isEqualTo("3");
        assertThat(carrinhoPage.camisetaEstampada.tamanhoM.preto.getText()).isEqualTo("3");
        assertThat(carrinhoPage.camisetaEstampada.tamanhoP.preto.getText()).isEqualTo("6");

        // Finalizar o orçamento
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(1000);
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();
        carrinhoPage.botaoVoltar.click();

        // Validar a indisponibilidade dos itens M, G e GG
        Thread.sleep(1000);
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.CAMISETA_ESTAMPADA.NOME);
        assertThat(carrinhoPage.camisetaEstampada.tamanhoGG.preto.getText()).isEqualTo(" ");
        assertThat(carrinhoPage.camisetaEstampada.tamanhoG.preto.getText()).isEqualTo(" ");
        assertThat(carrinhoPage.camisetaEstampada.tamanhoM.preto.getText()).isEqualTo(" ");
        assertThat(carrinhoPage.camisetaEstampada.tamanhoP.preto.getText()).isEqualTo("0");
    }

    /**
     * Testar mensagens de carrinho atualizado quando adicionado itens ao carrinho.
     * Quando adicionado ou removido itens do carrinho deve validar mensagens de atualização de itens.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarMensagensDeCarrinhoAtualizadoQuandoAdicionadoRemovidoItensDoCarrinho() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        loginPage.logarQaModas();
        Thread.sleep(2000);

        // Abrir o item Calca Jeans Milanda
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.CALCA_JEANS_MILANDA.NOME);
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
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void     validarCalculoDeValorTotalNoCarrinho() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        loginPage.logarQaModas();
        Thread.sleep(2000);

        // Adicionar um Vestido tamanho GG vermelho ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.VESTIDO_LONGO.NOME);
        carrinhoPage.vestidoLongo.tamanhoGG.vermelho.click();
        Thread.sleep(1000);
        assertThat(carrinhoPage.vestidoLongo.tamanhoGG.vermelho.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();
        Thread.sleep(1200);
        homePage.validarQuePrecosEstaoSendoExibidosQaModas();
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");

        // Adicionar uma Blusa tamanho G preta ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.BLUSA.NOME);
        carrinhoPage.blusa.tamanhoGG.preto.click();
        assertThat(carrinhoPage.blusa.tamanhoGG.preto.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();
        Thread.sleep(1200);
        homePage.validarQuePrecosEstaoSendoExibidosQaModas();
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("2");

        //Validar que possui 2 paças e o valor total é igual a R$ 299.98
        carrinhoPage.carrinhoIcone.click();
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("2 pc    R$ 299,98");
    }

    /**
     * Testar a remoção de item do carrinho pelo botão 'X' dentro do carrinho.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRemocaoDeItensDoCarrinhoPeloIconeX() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        loginPage.logarQaModas();
        Thread.sleep(2000);

        // Adicionar o item Vestido Longo GG vermelho ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.VESTIDO_LONGO.NOME);
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
        homePage.validarQuePrecosEstaoSendoExibidosQaModas();
    }

    /**
     * Testar a remoção de itens do carrinho pressionando e segurando a quantidade de itens do carrinho.
     * É esperado que quando a ação de manter pressionado for feita deve zerar a quantidade do tamanho e cor especifico.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarRemocaoDoCarrinhoPressionandoESegurandoAQuantidadeDoItem() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        loginPage.logarQaModas();
        Thread.sleep(2000);

        //Adiciona os valores defaults ao estoque (4 peças de cada cor) via API
        VestClient.adicionarEstoque(ProdutosQaModasProperties.BLUSA.ID, ProdutosQaModasProperties.BLUSA.ESTOQUE_REQUEST, AmbienteProperties.QAMODAS);

        //Adicionar Blusa Tamanho G branca ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.BLUSA.NOME);
        Thread.sleep(1000);
        carrinhoPage.blusa.tamanhoG.branco.click();
        assertThat(carrinhoPage.blusa.tamanhoG.branco.getText()).isEqualTo("1");
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();

        //Remover Blusa do carrinho e validar quantidade de peças e mensagem
        carrinhoPage.carrinhoIcone.click();
        validarInformacoesDeContato();
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("1 pc    R$ 99,99");
        AcoesCustomizadas.clicarEManterPressionado(carrinhoPage.blusa.tamanhoG.branco);
        Thread.sleep(800);
        assertThat(carrinhoPage.mensagemCarrinhoAtualizado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_PRODUTO_REMOVIDO);
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("0 pc    R$ 0,00");

    }

    /**
     * Testar produtos esgotado durante um pedido.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarProdutoEsgotadoDuranteUmPedido() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        loginPage.logarQaModas();
        Thread.sleep(2000);

        //Adiciona ao estoque 20 peças de cada cor via API
        VestClient.adicionarEstoque(ProdutosQaModasProperties.SHORTS.ID, ProdutosQaModasProperties.SHORTS.ESTOQUE_REQUEST, AmbienteProperties.QAMODAS);

        //Adicionar ao carrinho 9 peças verdes dos tamanhos 36 e 38 e 4 peças verdes dos tamanhos 40 e 46
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.SHORTS.NOME);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.shorts.tamanho36.verde, 9);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.shorts.tamanho38.verde, 9);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.shorts.tamanho40.verde, 4);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.shorts.tamanho46.verde, 4);
        Thread.sleep(2000);
        assertThat("9").isEqualTo(carrinhoPage.shorts.tamanho36.verde.getText());
        assertThat("9").isEqualTo(carrinhoPage.shorts.tamanho38.verde.getText());
        assertThat("4").isEqualTo(carrinhoPage.shorts.tamanho40.verde.getText());
        assertThat("4").isEqualTo(carrinhoPage.shorts.tamanho46.verde.getText());

        // Alterar o estoque dos tamanhos 36 e 38 para 5 peças cada cor via API
        List<Map<String, String>> tamanho = Arrays.asList(
                Collections.singletonMap("36", "5"),
                Collections.singletonMap("38", "5"),
                Collections.singletonMap("42", "10"),
                Collections.singletonMap("46", "10"),
                Collections.singletonMap("40", "10"));
        ImmutableList<ItensRequestVestClient> estoque = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(tamanho).cor(getVerde()).build(),
                ItensRequestVestClient.builder().tamanho(tamanho).cor(getVermelho()).build());

        VestClient.adicionarEstoque(ProdutosQaModasProperties.SHORTS.ID, estoque, AmbienteProperties.QAMODAS);

        // Valida mensagem de produto esgotado
        Thread.sleep(1000);
        assertThat(CarrinhoProperties.COR_PRETA_RGB).isEqualTo(carrinhoPage.shorts.tamanho36.verde.getCssValue("color"));
        assertThat(CarrinhoProperties.COR_PRETA_RGB).isEqualTo(carrinhoPage.shorts.tamanho38.verde.getCssValue("color"));
        assertThat(CarrinhoProperties.COR_PRETA_RGB).isEqualTo(carrinhoPage.shorts.tamanho40.verde.getCssValue("color"));
        assertThat(CarrinhoProperties.COR_PRETA_RGB).isEqualTo(carrinhoPage.shorts.tamanho46.verde.getCssValue("color"));
        carrinhoPage.carrinhoIcone.click();
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(1000);
        assertThat(CarrinhoProperties.PECAS_ESGOTADAS_MENSAGEM).isEqualTo(carrinhoPage.popUpMensagem.mensagem.getText());
        carrinhoPage.popUpMensagem.botaoOk.click();

        // Valida a cor do texto e a quantidade de itens
        Thread.sleep(1000);
        assertThat(CarrinhoProperties.COR_VERMELHA_RGB).isEqualTo(carrinhoPage.shorts.tamanho36.verde.getCssValue("color"));
        assertThat(CarrinhoProperties.COR_VERMELHA_RGB).isEqualTo(carrinhoPage.shorts.tamanho38.verde.getCssValue("color"));
        assertThat(CarrinhoProperties.COR_PRETA_RGB).isEqualTo(carrinhoPage.shorts.tamanho40.verde.getCssValue("color"));
        assertThat(CarrinhoProperties.COR_PRETA_RGB).isEqualTo(carrinhoPage.shorts.tamanho46.verde.getCssValue("color"));
        assertThat("4").isEqualTo(carrinhoPage.shorts.tamanho36.verde.getText());
        assertThat("4").isEqualTo(carrinhoPage.shorts.tamanho38.verde.getText());
        assertThat("4").isEqualTo(carrinhoPage.shorts.tamanho40.verde.getText());
        assertThat("4").isEqualTo(carrinhoPage.shorts.tamanho46.verde.getText());

        // Finaliza a venda
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();
        carrinhoPage.botaoVoltar.click();
        Thread.sleep(2000);

        // Validar a indisponibilidade de estoque após a compra
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.SHORTS.NOME);
        carrinhoPage.shorts.tamanho38.verde.click();
        carrinhoPage.shorts.tamanho36.verde.click();
        assertThat(" ").isEqualTo(carrinhoPage.shorts.tamanho36.verde.getText());
        assertThat(" ").isEqualTo(carrinhoPage.shorts.tamanho38.verde.getText());
        assertThat("0").isEqualTo(carrinhoPage.shorts.tamanho40.verde.getText());
        assertThat("0").isEqualTo(carrinhoPage.shorts.tamanho42.verde.getText());
        assertThat("0").isEqualTo(carrinhoPage.shorts.tamanho46.verde.getText());
    }

    /**
     * Validar mensagem de "Máximo disponível" ao adicionar produtos do tipo Pack.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarMaximoDisponivel() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        loginPage.logarQaModas();
        Thread.sleep(2000);

        //Adicionar Pack Jeans ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.PACK_JEANS.NOME);
        Thread.sleep(2000);
        assertThat(carrinhoPage.packJeans.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.packJeans.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.packJeans.quantidade.getText()).isEqualTo("0");

        //Adicionar um pack e validar a mensagem
        validarMensagemDeLimiteAtingido(carrinhoPage.packJeans.botaoAdicionarItem, 3);
    }

    /**
     * Validar calculo de valor total para produtos do tipo Pack.
     * Deve ir recalculando o valor total do carrinho conforme vai sendo adicionado novos Packs.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarCalculoDeValorTotalParaItensDoTipoPack() {
        // Fazer login
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        loginPage.logarQaModas();
        Thread.sleep(2000);

        //Adiciona uma Calça Jeans Pack ao carrinho e volta para a home
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.CALCA_JEANS_PACK.NOME);

        Thread.sleep(1800);
        assertThat(carrinhoPage.calcaJeansPack.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.calcaJeansPack.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("0");
        carrinhoPage.calcaJeansPack.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("1");
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(800);

        // Validar o valor total dos itens no carrinho
        // assertThat(carrinhoPage.totalItens.getText()).isEqualTo("4 pc    R$ 262,00");

        // Abre o contato do Whatsapp
        validarNovaAba(carrinhoPage.telefoneVendedor, InfoProperties.PAGE_LINK_WHATS);

        // Validar o valor total dos itens no carrinho
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("4 pc    R$ 262,00");

        // Adiciona mais uma Calça Jeans Pack ao carrinho
        assertThat(carrinhoPage.calcaJeansPack.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.calcaJeansPack.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("1");
        carrinhoPage.packJeans.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("2");
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("8 pc    R$ 524,00");

        // Adiciona mais duas Calça Jeans Pack ao carrinho
        assertThat(carrinhoPage.calcaJeansPack.botaoAdicionarItem.getText()).isEqualTo("+");
        assertThat(carrinhoPage.calcaJeansPack.botaoRemoverItem.getText()).isEqualTo("-");
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("2");
        carrinhoPage.packJeans.botaoAdicionarItem.click();
        carrinhoPage.packJeans.botaoAdicionarItem.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.calcaJeansPack.quantidade.getText()).isEqualTo("4");
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("16 pc    R$ 1.048,00");

        // Finalizar venda e validar mensagem
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(800);
        carrinhoPage.validaMensagemDePedidoEnviado();
    }

    /**
     * Validar a quantidade minima de compra.
     * Deve aceitar pedidos com no mínimo dois itens no carrinho.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarQuantidadeMinimaDeCompra() {
        // Faz login clicando em um produto
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosQaModasProperties.CAMISETA_MANGA_LON.NOME);
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS.getDocumento());
        cadastroVendedorPage.getBotaoContinuar();
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CPF_QAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);

        // Adiciona ao estoque 20 peças de cada cor via API
        VestClient.adicionarEstoque(ProdutosQaModasProperties.CAMISETA_MANGA_LON.ID, ProdutosQaModasProperties.CAMISETA_MANGA_LON.getEstoque("20"), AmbienteProperties.QAMODAS);

        // Adicionar uma Camiseta Manga Long ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.CAMISETA_MANGA_LON.NOME);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.camisetaMangaLong.tamanho46.cinza, 11);
        Thread.sleep(2000);
        assertThat(carrinhoPage.camisetaMangaLong.tamanho46.cinza.getText()).isEqualTo("11");
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");
        carrinhoPage.botaoContinuarComprando.click();

        // Validar mensagem de minimo
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(1000);
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(2000);
        assertThat(carrinhoPage.popUpMensagem.mensagem.getText()).isEqualTo(CarrinhoProperties.MENSAGEM_MINIMO_PECAS);
        carrinhoPage.popUpMensagem.botaoOk.click();

        // Adicionar uma camiseta manga lon
        carrinhoPage.camisetaMangaLong.tamanho46.cinza.click();
        assertThat(carrinhoPage.camisetaMangaLong.tamanho46.cinza.getText()).isEqualTo("12");

        // Finalizar o pedido e validar a mensagem
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();
    }

    /**
     * Validar o preço de um produto na promoção.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarPrecoProdutoPromocional() {
        // Faz login clicando em um produto
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosQaModasProperties.CAMISETA_MANGA_LON.NOME);
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO_CPF_QAMODAS.getDocumento());
        Thread.sleep(800);
        assertThat(loginPage.getCnpjCpfText()).isEqualTo(Mascara.cpf(LoginProperties.LOGIN_VALIDO_CPF_QAMODAS.getDocumento()));
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CPF_QAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2000);

        // Adiciona ao estoque 20 peças de cada cor via API
        VestClient.adicionarEstoque(ProdutosQaModasProperties.CAMISETA_MANGA_LON.ID, ProdutosQaModasProperties.CAMISETA_MANGA_LON.ESTOQUE_REQUEST, AmbienteProperties.QAMODAS);

        // Adicionar duas Camiseta Manga Long preta tamanho uma tamanho 38 e a outra 36
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.CAMISETA_MANGA_LON.NOME);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.camisetaMangaLong.tamanho38.preto, 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.camisetaMangaLong.tamanho36.preto, 10);
        Thread.sleep(1000);
        assertThat(carrinhoPage.camisetaMangaLong.tamanho38.preto.getText()).isEqualTo("2");
        assertThat(carrinhoPage.camisetaMangaLong.tamanho36.preto.getText()).isEqualTo("10");
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");

        // Validar preço no carrinho
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(2000);
        assertThat(carrinhoPage.camisetaMangaLong.tamanho38.preto.getText()).isEqualTo("2");
        assertThat(carrinhoPage.camisetaMangaLong.tamanho36.preto.getText()).isEqualTo("10");
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("12 pc    R$ 720,00");


        // Finalizar o pedido e validar a mensagem
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();
    }


    /**
     * Validar o botão limpar logando com CPF.
     * Utilizando o ambiente QAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarBotaoLimparLogandoComCpf() {
        // Faz login clicando em um produto
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_BASE_URL);
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosQaModasProperties.CAMISETA_MANGA_LON.NOME);
        cadastroVendedorPage.getCnpjCpfOuEmail().sendKeys(LoginProperties.LOGIN_VALIDO_CPF_QAMODAS.getDocumento());
        Thread.sleep(800);
        assertThat(loginPage.getCnpjCpfText()).isEqualTo(Mascara.cpf(LoginProperties.LOGIN_VALIDO_CPF_QAMODAS.getDocumento()));
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CPF_QAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2500);

        // Valida o titulo e o preço exibido na home
        homePage.validarTituloEPrecoDeProdutosQaModas();

        // Adiciona ao estoque 20 peças de cada cor via API
        VestClient.adicionarEstoque(ProdutosQaModasProperties.CAMISETA_MANGA_LON.ID, ProdutosQaModasProperties.CAMISETA_MANGA_LON.ESTOQUE_REQUEST, AmbienteProperties.QAMODAS);

        // Adicionar uma Camiseta Manga Long preta tamanho 42
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModasProperties.CAMISETA_MANGA_LON.NOME);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.camisetaMangaLong.tamanho42.preto, 1);
        Thread.sleep(1000);
        assertThat(carrinhoPage.camisetaMangaLong.tamanho42.preto.getText()).isEqualTo("1");
        Thread.sleep(1000);
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");

        // Clica em botao limpar e valida que foi removido
        carrinhoPage.botaoLimpar.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.mensagemCarrinhoAtualizado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_PRODUTO_REMOVIDO);
        assertThat(carrinhoPage.camisetaMangaLong.tamanho42.preto.getText()).isEqualTo("0");
        assertThat(AcoesCustomizadas.elementoExiste(carrinhoPage.carrinhoQuantidadeItens)).isFalse();

        // Voltar para o catalogo e validar produtos
        carrinhoPage.botaoVoltar.click();
        homePage.validarTituloEPrecoDeProdutosQaModas();
    }

    /**
     * Validar o valor minimo configurado no ambiente para pedido
     * Utilizando o ambiente PEPITAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validaValorDePedidoMinimo() {
        // Faz login clicando em um produto
        driver.navigate().to(ConfiguracoesGlobais.PEPITAMODAS_BASE_URL);
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosPepitaModasProperties.BLUSA_FEMININA.NOME);
        AcoesCustomizadas.sendKeys(LoginProperties.LOGIN_VALIDO_CNPJ_PEPITAMODAS.getDocumento(), cadastroVendedorPage.getCnpjCpfOuEmail());
        Thread.sleep(800);
        assertThat(loginPage.getCnpjCpfText()).isEqualTo(Mascara.cnpj(LoginProperties.LOGIN_VALIDO_CNPJ_PEPITAMODAS.getDocumento()));
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CNPJ_PEPITAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2500);

        // Valida quantidade de itens exibidos na home
        homePage.validarQuantidadeDeProdutosSendoExibidosPepitaModas();

        // Adiciona ao estoque 10 peças de cada cor via API
        VestClient.adicionarEstoque(ProdutosPepitaModasProperties.BLUSA_FEMININA.ID, ProdutosPepitaModasProperties.BLUSA_FEMININA.ESTOQUE_REQUEST, AmbienteProperties.PEPITAMODAS);

        // Adicionar uma Camiseta Manga Long preta tamanho 42
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosPepitaModasProperties.BLUSA_FEMININA.NOME);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.blusaFeminina.tamanhoP.getAzul(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.blusaFeminina.tamanhoM.getAzul(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.blusaFeminina.tamanhoG.getAzul(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.blusaFeminina.tamanhoP.getPreto(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.blusaFeminina.tamanhoM.getPreto(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.blusaFeminina.tamanhoG.getPreto(), 1);
        Thread.sleep(2000);
        assertThat(carrinhoPage.blusaFeminina.tamanhoP.getAzul().getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoM.getAzul().getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoG.getAzul().getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoP.getPreto().getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoM.getPreto().getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoG.getPreto().getText()).isEqualTo("1");
        Thread.sleep(1000);
        assertThat(carrinhoPage.carrinhoQuantidadeItens.getText()).isEqualTo("1");

        // Clica em botao limpar e valida que foi removido
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.blusaFeminina.tamanhoP.azul.getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoM.azul.getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoG.azul.getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoP.preto.getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoM.preto.getText()).isEqualTo("2");
        assertThat(carrinhoPage.blusaFeminina.tamanhoG.preto.getText()).isEqualTo("1");
        assertThat(carrinhoPage.totalItens.getText()).isEqualTo("11 pc    R$ 495,00");

        // Valida a mensagem de minimo R$
        carrinhoPage.botaoFinalizarPedido.click();
        assertThat(CarrinhoProperties.PEDIDO_MINIMO_MENSAGEM).isEqualTo(carrinhoPage.popUpMensagem.mensagem.getText());
        carrinhoPage.popUpMensagem.botaoOk.click();

        // Finalizar venda
        carrinhoPage.blusaFeminina.tamanhoG.preto.click();
        Thread.sleep(800);
        assertThat(carrinhoPage.blusaFeminina.tamanhoG.preto.getText()).isEqualTo("2");
        carrinhoPage.botaoFinalizarPedido.click();
        carrinhoPage.validaMensagemDePedidoEnviado();

    }

    /**
     * Validar produto esgotado com estoque binario
     * Utilizando o ambiente PEPITAMODAS.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarProdutoComEstoqueBinario() {
        // Deixa o macacao como disponivel em estoque
        VestClient.adicionarEstoque(ProdutosPepitaModasProperties.MACACAO.ID, ProdutosPepitaModasProperties.MACACAO.ESTOQUE_REQUEST, AmbienteProperties.PEPITAMODAS);

        // Faz login clicando em um produto
        driver.navigate().to(ConfiguracoesGlobais.PEPITAMODAS_BASE_URL);
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosPepitaModasProperties.MACACAO.NOME);
        AcoesCustomizadas.sendKeys(LoginProperties.LOGIN_VALIDO_CNPJ_PEPITAMODAS.getEmail(), cadastroVendedorPage.getCnpjCpfOuEmail());
        Thread.sleep(800);
        assertThat(loginPage.getCnpjCpfText()).isEqualTo(LoginProperties.LOGIN_VALIDO_CNPJ_PEPITAMODAS.getEmail());
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CNPJ_PEPITAMODAS);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2500);

        // Valida quantidade de itens exibidos na home
        homePage.validarQuantidadeDeProdutosSendoExibidosPepitaModas();

        // Adiciona Macacao ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosPepitaModasProperties.MACACAO.NOME);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.macacao.tamanhoP.preto, 3);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.macacao.tamanhoM.preto, 3);
        Thread.sleep(2000);
        assertThat(carrinhoPage.macacao.tamanhoP.preto.getText()).isEqualTo("3");
        assertThat(carrinhoPage.macacao.tamanhoM.preto.getText()).isEqualTo("3");

        // Muda o estoque do macacão para esgotado
        VestClient.adicionarEstoque(ProdutosPepitaModasProperties.MACACAO.ID, ProdutosPepitaModasProperties.MACACAO.SEM_ESTOQUE_REQUEST, AmbienteProperties.PEPITAMODAS);

        // Clica no carrinho
        AcoesCustomizadas.rolarPaginaParaCima();
        carrinhoPage.carrinhoIcone.click();
        Thread.sleep(2000);
        assertThat(CarrinhoProperties.COR_PRETA_RGB).isEqualTo(carrinhoPage.macacao.tamanhoP.preto.getCssValue("color"));
        assertThat(CarrinhoProperties.COR_PRETA_RGB).isEqualTo(carrinhoPage.macacao.tamanhoM.preto.getCssValue("color"));

        // Clicar em finalizar venda
        carrinhoPage.botaoFinalizarPedido.click();
        Thread.sleep(1000);
        assertThat(CarrinhoProperties.COR_VERMELHA_RGB).isEqualTo(carrinhoPage.macacao.tamanhoP.preto.getCssValue("color"));
        assertThat(CarrinhoProperties.COR_VERMELHA_RGB).isEqualTo(carrinhoPage.macacao.tamanhoM.preto.getCssValue("color"));
        assertThat(CarrinhoProperties.PECAS_ESGOTADAS_MENSAGEM).isEqualTo(carrinhoPage.popUpMensagem.mensagem.getText());
        carrinhoPage.popUpMensagem.botaoOk.click();
        carrinhoPage.macacao.botaoExcluirPeca.click();
        Thread.sleep(1000);

        // Voltar para home e clicar no produto macacão
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosPepitaModasProperties.MACACAO.NOME);
        Thread.sleep(1000);

        // Validar que esta exibindo o produto como esgotado
        assertThat(carrinhoPage.mensagemEsgotado.getText()).isEqualTo(CarrinhoProperties.MENSGAEM_PRODUTO_ESGOTADO);
        assertThat(AcoesCustomizadas.elementoExiste(carrinhoPage.macacao.tamanhoP.preto)).isFalse();
        assertThat(AcoesCustomizadas.elementoExiste(carrinhoPage.macacao.tamanhoM.preto)).isFalse();
    }

    /**
     * Validar frete
     * Utilizando o ambiente QAMODAS2.
     */
    @SneakyThrows
    @Test(retryAnalyzer = RetentarUmaVez.class)
    public void validarFrete() {
        // Deixa o macacao como disponivel em estoque
        VestClient.adicionarEstoque(ProdutosPepitaModasProperties.MACACAO.ID, ProdutosPepitaModasProperties.MACACAO.ESTOQUE_REQUEST, AmbienteProperties.QAMODAS2);
        VestClient.excluirEnderecos("bc57d790-eb17-4d81-95df-a0159834c45e", LoginProperties.LOGIN_API);

        // Faz login clicando em um produto
        driver.navigate().to(ConfiguracoesGlobais.QAMODAS_2);
        Thread.sleep(2000);
        homePage.clicarEmAnuncioDeProdutoSemPreco(ProdutosQaModas2ModasProperties.CALCA_ALGODAO.NOME);
        AcoesCustomizadas.sendKeys(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS2.getEmail(), cadastroVendedorPage.getCnpjCpfOuEmail());
        Thread.sleep(800);
        assertThat(loginPage.getCnpjCpfText()).isEqualTo(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS2.getEmail());
        cadastroVendedorPage.getBotaoContinuar().click();
        loginPage.preencherLogin(LoginProperties.LOGIN_VALIDO_CNPJ_QAMODAS2);
        loginPage.getBotaoContinuar().click();
        Thread.sleep(2500);

        // Muda o estoque do macacão para esgotado
        VestClient.adicionarEstoque(ProdutosQaModas2ModasProperties.CALCA_ALGODAO.ID,ProdutosQaModas2ModasProperties.CALCA_ALGODAO.ESTOQUE_REQUEST, AmbienteProperties.QAMODAS2);


        // Adiciona Macacao ao carrinho
        homePage.clicarEmAnuncioDeProdutoComPreco(ProdutosQaModas2ModasProperties.CALCA_ALGODAO.NOME);
        Thread.sleep(1000);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoP.getPreto(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoP.getCinza(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoP.getAzul(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoM.getCinza(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoM.getPreto(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoM.getAzul(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoG.getCinza(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoG.getPreto(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoG.getAzul(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoGG.getCinza(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoGG.getPreto(), 2);
        AcoesCustomizadas.cliarRepetidasVezes(carrinhoPage.calcaAlgodao.tamanhoGG.getAzul(), 2);
        Thread.sleep(2000);
        assertThat(carrinhoPage.calcaAlgodao.tamanhoGG.getAzul().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoGG.getCinza().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoGG.getPreto().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoG.getAzul().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoG.getCinza().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoG.getPreto().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoP.getAzul().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoP.getCinza().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoP.getPreto().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoM.getAzul().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoM.getCinza().getText()).isEqualTo("2");
        assertThat(carrinhoPage.calcaAlgodao.tamanhoM.getPreto().getText()).isEqualTo("2");

        // Ir para carrinho
        carrinhoPage.carrinhoIcone.click();

        // Seleciona endereço de entrega
        carrinhoPage.enderecoEntrega.click();
        AcoesCustomizadas.sendKeys("09440140 ",carrinhoPage.enderecoEntregaPage.cep);
        carrinhoPage.enderecoEntregaPage.numero.click();
        Thread.sleep(2000);
        carrinhoPage.enderecoEntregaPage.numero.sendKeys("123");
        carrinhoPage.enderecoEntregaPage.botaoSalvar.click();
        Thread.sleep(1000);

        // Seleciona o tipo de entrega
        carrinhoPage.opcoesEntrega.outrasFormas.click();
        carrinhoPage.opcoesEntrega.botaoContinuar.click();
        Thread.sleep(1000);
        assertThat(carrinhoPage.opcoesEntrega.mensagemObrigatorio.getText()).contains("Obrigatório");

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
    @SneakyThrows
    private void validarMensagemDeLimiteAtingido(WebElement element, int cliquesEsperados) {
        int numeroCliques = 0;
        while (numeroCliques < 10) {
            element.click();
            numeroCliques++;
            Thread.sleep(1000);
            String text = carrinhoPage.mensagemMaximoDisponivel.getText();
            if (StringUtils.isNotBlank(text)) {
                assertThat(CarrinhoProperties.MENSAGEM_MAXIMO_DISPONIVEL).isEqualTo(text);
                assertThat(numeroCliques).isEqualTo(cliquesEsperados);
                return;
            }
        }
        throw new RuntimeException("Não foi possivel validar a mensagem de limite maximo.");
    }


    @SneakyThrows
    private void validarNovaAba(WebElement element, String url) {
        element.click();
        Thread.sleep(1000);
        System.out.println(driver.getCurrentUrl());
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        driver.switchTo().window(tabs.get(1));
        Thread.sleep(1000);
        String currentUrl = driver.getCurrentUrl();
        System.out.println(currentUrl);
        Assertions.assertThat(currentUrl).contains(url);
        driver.close();
        driver.switchTo().window(tabs.get(0));
    }
}
