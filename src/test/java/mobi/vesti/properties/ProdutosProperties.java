package mobi.vesti.properties;

import mobi.vesti.client.request.ColorRequestVestClient;
import mobi.vesti.client.request.ItensRequestVestClient;
import mobi.vesti.dto.ProdutosDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class ProdutosProperties {
    public static final List<ProdutosDto> PRODUTOS_HOME_SEM_PRECO = Arrays.asList(PACK_JEANS.SEM_PRECO,
            CALCA_JEANS_PACK.SEM_PRECO,
            JAQUETA.SEM_PRECO,
            VESTIDO_LONGO.SEM_PRECO,
            POLO.SEM_PRECO,
            CAMISETA_ESTAMPADA.SEM_PRECO,
            BLUSA.SEM_PRECO,
            CAMISETA.SEM_PRECO,
            CALCA_JEANS_MILANDA.SEM_PRECO);

    public static final List<ProdutosDto> PRODUTOS_HOME_COM_PRECO = Arrays.asList(
            PACK_JEANS.COM_PRECO,
            CALCA_JEANS_PACK.COM_PRECO,
            JAQUETA.COM_PRECO,
            VESTIDO_LONGO.COM_PRECO,
            POLO.COM_PRECO,
            CAMISETA_ESTAMPADA.COM_PRECO,
            BLUSA.COM_PRECO,
            CAMISETA.COM_PRECO,
            CALCA_JEANS_MILANDA.COM_PRECO);

    public static class CAMISETA_ESTAMPADA {
        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto COM_PRECO = new ProdutosDto("CAMISETA ESTAMPADA", "R$ 70,00");
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("CAMISETA ESTAMPADA", "Ver Preço");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = Arrays.asList(
                ItensRequestVestClient.builder().cor(PRETO).build()
        );
    }

    public static class POLO {
        public static final String ID = "95d8a821-b7fb-4ac0-b1bd-7e5c9df23b28";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("POLO", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("POLO", "R$ 29,90");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = Arrays.asList(
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(ROSA).build(),
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(VERDE).build(),
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(AZUL_CLARO).build(),
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(CINZA).build()
        );
    }

    public static class PACK_JEANS {
        //        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("PACK JEANS", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("PACK JEANS", "R$ 50,00");
    }

    public static class CALCA_JEANS_PACK {
        //        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("CALÇA JEANS PACK", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("CALÇA JEANS PACK", "R$ 65,50");
    }

    public static class JAQUETA {
        //        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("JAQUETA", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("JAQUETA", "R$ 299,90");
    }

    public static class CALCA_JEANS_MILANDA {
        //        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("CALÇA JEANS MILADA", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("CALÇA JEANS MILADA", "R$ 79,90");
    }

    public static class VESTIDO_LONGO {
        //        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("VESTIDO LONGO", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("VESTIDO LONGO", "R$ 199,99","100% tela");
    }

    public static class BLUSA {
        //        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("BLUSA", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("BLUSA", "R$ 159,90 R$ 99,99");
    }

    public static class CAMISETA {
        public static final String ID = "b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("CAMISETA", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("CAMISETA", "R$ 69,90", "100% algodão");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = Arrays.asList(
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(BRANCO).build(),
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(AMARELO).build(),
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(AZUL).build(),
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(PRETO).build(),
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(VINHO).build(),
                ItensRequestVestClient.builder().tamanho(TAMANHOS).cor(VERMELHO).build()
        );
    }

    private static final List<Map<String, String>> TAMANHOS = Arrays.asList(
            Collections.singletonMap("P", "4"),
            Collections.singletonMap("M", "4"),
            Collections.singletonMap("G", "4"),
            Collections.singletonMap("GG", "4")
    );

    private final static ColorRequestVestClient ROSA = ColorRequestVestClient.builder()
            .codigoCor("#ffc4f9")
            .id("c09e3095-fb64-41b9-ad8c-5744b34d740d")
            .nome("Rosa")
            .build();

    private final static ColorRequestVestClient AZUL_CLARO = ColorRequestVestClient.builder()
            .codigoCor("#5bb6f7")
            .id("bea7adf8-d12a-4d28-aa8b-cfc930f6be6f")
            .nome("Azul Claro")
            .build();

    private final static ColorRequestVestClient VERDE = ColorRequestVestClient.builder()
            .codigoCor("#03f11b")
            .id("08149abf-5778-4f2d-ad89-2a479c5df4cc")
            .nome("Verde")
            .build();

    private final static ColorRequestVestClient CINZA = ColorRequestVestClient.builder()
            .codigoCor("#c6c6c6")
            .id("d3cb9222-7b3d-4d4b-9d74-3f76a75fe942")
            .nome("Cinza")
            .build();

    private final static ColorRequestVestClient PRETO = ColorRequestVestClient.builder()
            .codigoCor("#000000")
            .id("463f9661-968d-4748-9346-863229b1dea7")
            .nome("Preto")
            .build();

    private final static ColorRequestVestClient BRANCO = ColorRequestVestClient.builder()
            .codigoCor("#ffffff")
            .id("d351fa15-cf87-43e8-94c0-55607d8c6ab6")
            .nome("Branco")
            .build();

    private final static ColorRequestVestClient AMARELO = ColorRequestVestClient.builder()
            .codigoCor("#ffe600")
            .id("d5851612-90ee-4bf2-93aa-132cb57b0c9b")
            .nome("Amarelo")
            .build();

    private final static ColorRequestVestClient AZUL = ColorRequestVestClient.builder()
            .codigoCor("#260ad0")
            .id("cd0f8bf7-c405-4e00-9db1-a57e6e0af8e4")
            .nome("Azul")
            .build();

    private final static ColorRequestVestClient VINHO = ColorRequestVestClient.builder()
            .codigoCor("#d12001")
            .id("ea803052-93ba-46d1-9452-7043a437dfd5")
            .nome("Vinho")
            .build();

    private final static ColorRequestVestClient VERMELHO = ColorRequestVestClient.builder()
            .codigoCor("#f02410")
            .id("d476d8f1-5b93-4e43-972a-29e3968884fb")
            .nome("Vermelho")
            .build();
}
