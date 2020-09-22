package mobi.vesti.properties;

import com.google.common.collect.ImmutableList;
import mobi.vesti.client.request.ColorRequestVestClient;
import mobi.vesti.client.request.ItensRequestVestClient;
import mobi.vesti.dto.ProdutosDto;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProdutosProperties {
    public static final List<ProdutosDto> PRODUTOS_HOME_SEM_PRECO = ImmutableList.of(
            JAQUETA.SEM_PRECO,
            CALCA_JEANS_PACK.SEM_PRECO,
            VESTIDO_LONGO.SEM_PRECO,
            POLO.SEM_PRECO,
            CAMISETA_ESTAMPADA.SEM_PRECO,
            BLUSA.SEM_PRECO,
            CAMISETA.SEM_PRECO,
            CAMISETA_MANGA_LON.SEM_PRECO,
            CALCA_JEANS_MILANDA.SEM_PRECO,
            PACK_JEANS.SEM_PRECO,
            SHORTS.SEM_PRECO);

    public static final List<ProdutosDto> PRODUTOS_HOME_COM_PRECO = ImmutableList.of(
            CALCA_JEANS_PACK.COM_PRECO,
            PACK_JEANS.COM_PRECO,
            JAQUETA.COM_PRECO,
            VESTIDO_LONGO.COM_PRECO,
            POLO.COM_PRECO,
            CAMISETA_ESTAMPADA.COM_PRECO,
            BLUSA.COM_PRECO,
            CAMISETA.COM_PRECO,
            CAMISETA_MANGA_LON.COM_PRECO,
            CALCA_JEANS_MILANDA.COM_PRECO,
            SHORTS.COM_PRECO);

    public static class CAMISETA_ESTAMPADA {
        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto COM_PRECO = new ProdutosDto("CAMISETA ESTAMPADA", "R$ 70,00");
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("CAMISETA ESTAMPADA", "Ver Preço");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().cor(getPreto()).build()
        );
    }

    public static class POLO {
        public static final String ID = "95d8a821-b7fb-4ac0-b1bd-7e5c9df23b28";
        public static final String NOME = "POLO";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 29,90");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getRosa()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getVerde()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getAzulClaro()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getCinza()).build()
        );
    }

    public static class PACK_JEANS {
        public static final String ID = "db960a44-1fb4-4fdf-96af-995193104376";
        public static final String NOME = "PACK JEANS";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 50,00");
    }

    public static class JAQUETA {
        //        public static final String ID = "2961/b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("JAQUETA", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("JAQUETA", "R$ 299,90");
    }

    public static class CALCA_JEANS_MILANDA {
        public static final String NOME = "CALÇA JEANS MILADA";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 79,90");
    }

    public static class CALCA_JEANS_PACK {
        public static final String NOME = "CALÇA JEANS PACK";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 65,50");
    }

    public static class VESTIDO_LONGO {
        public static final String NOME = "VESTIDO LONGO";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 199,99", "100% tela");
    }

    public static class BLUSA {
        public static final String ID = "72f2e364-6708-4b65-bff6-ad3c1225c860";
        public static final String NOME = "BLUSA";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 159,90 R$ 99,99");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getBranco()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getPreto()).build());
    }

    public static class SHORTS {
        public static final String ID = "bc57d790-eb17-4d81-95df-a0159834c45e";
        public static final String NOME = "SHORTS";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 79,90");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "36", "38", "42", "46")).cor(getVerde()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "36", "38", "42", "46")).cor(getVermelho()).build());

        public static List<ItensRequestVestClient> getEstoque(String quantidadeDePecas) {
            return ImmutableList.of(
                    ItensRequestVestClient.builder().tamanho(getTamanhos(quantidadeDePecas, "36", "38", "42", "46")).cor(getVerde()).build(),
                    ItensRequestVestClient.builder().tamanho(getTamanhos(quantidadeDePecas, "36", "38", "42", "46")).cor(getVermelho()).build());
        }
    }

    public static class CAMISETA_MANGA_LON {
        public static final String ID = "541c79e2-d0c7-400d-b858-d9539ce22139";
        public static final String NOME = "CAMISETA MANGA LON";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 120,00 R$ 60,00");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getPreto()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getCinza()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getAzulClaro()).build());
    }

    public static class CAMISETA {
        public static final String ID = "b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("CAMISETA", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("CAMISETA", "R$ 69,90", "100% algodão");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getBranco()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getAmarelo()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getAzul()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getPreto()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getVinho()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhosPMG()).cor(getVermelho()).build()
        );
    }

    public static ImmutableList<Map<String, String>> getTamanhosPMG() {
        return getTamanhosPMG("4");
    }

    public static ImmutableList<Map<String, String>> getTamanhosPMG(String quantidade) {
        return getTamanhos(quantidade, "P", "M", "G");
    }

    public static ImmutableList<Map<String, String>> getTamanhos(String quantidade, String... tamanho) {
        List<Map<String, String>> tamanhos = Arrays.asList(tamanho)
                .stream()
                .map(t -> Collections.singletonMap(t, quantidade))
                .collect(Collectors.toList());
        return ImmutableList.copyOf(tamanhos);
    }

    private static ColorRequestVestClient getRosa() {
        return ColorRequestVestClient.builder()
                .codigoCor("#ffc4f9")
                .id("c09e3095-fb64-41b9-ad8c-5744b34d740d")
                .nome("Rosa")
                .build();
    }

    public static ColorRequestVestClient getAzulClaro() {
        return ColorRequestVestClient.builder()
                .codigoCor("#5bb6f7")
                .id("bea7adf8-d12a-4d28-aa8b-cfc930f6be6f")
                .nome("Azul Claro")
                .build();
    }

    public static ColorRequestVestClient getVerde() {
        return ColorRequestVestClient.builder()
                .codigoCor("#03f11b")
                .id("08149abf-5778-4f2d-ad89-2a479c5df4cc")
                .nome("Verde")
                .build();
    }

    public static ColorRequestVestClient getCinza() {
        return ColorRequestVestClient.builder()
                .codigoCor("#c6c6c6")
                .id("d3cb9222-7b3d-4d4b-9d74-3f76a75fe942")
                .nome("Cinza")
                .build();
    }

    public static ColorRequestVestClient getPreto() {
        return ColorRequestVestClient.builder()
                .codigoCor("#000000")
                .id("463f9661-968d-4748-9346-863229b1dea7")
                .nome("Preto")
                .build();
    }

    public static ColorRequestVestClient getBranco() {
        return ColorRequestVestClient.builder()
                .codigoCor("#ffffff")
                .id("d351fa15-cf87-43e8-94c0-55607d8c6ab6")
                .nome("Branco")
                .build();
    }

    public static ColorRequestVestClient getAmarelo() {
        return ColorRequestVestClient.builder()
                .codigoCor("#ffe600")
                .id("d5851612-90ee-4bf2-93aa-132cb57b0c9b")
                .nome("Amarelo")
                .build();
    }

    public static ColorRequestVestClient getAzul() {
        return ColorRequestVestClient.builder()
                .codigoCor("#260ad0")
                .id("cd0f8bf7-c405-4e00-9db1-a57e6e0af8e4")
                .nome("Azul")
                .build();
    }

    public static ColorRequestVestClient getVinho() {
        return ColorRequestVestClient.builder()
                .codigoCor("#d12001")
                .id("ea803052-93ba-46d1-9452-7043a437dfd5")
                .nome("Vinho")
                .build();
    }

    public static ColorRequestVestClient getVermelho() {
        return ColorRequestVestClient.builder()
                .codigoCor("#f02410")
                .id("d476d8f1-5b93-4e43-972a-29e3968884fb")
                .nome("Vermelho")
                .build();
    }
}
