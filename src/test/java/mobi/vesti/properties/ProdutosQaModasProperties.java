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

import static mobi.vesti.properties.EstoqueProperties.getAmarelo;
import static mobi.vesti.properties.EstoqueProperties.getAzul;
import static mobi.vesti.properties.EstoqueProperties.getAzulClaro;
import static mobi.vesti.properties.EstoqueProperties.getBranco;
import static mobi.vesti.properties.EstoqueProperties.getCinza;
import static mobi.vesti.properties.EstoqueProperties.getPreto;
import static mobi.vesti.properties.EstoqueProperties.getRosa;
import static mobi.vesti.properties.EstoqueProperties.getTamanhos;
import static mobi.vesti.properties.EstoqueProperties.getTamanhosPMG;
import static mobi.vesti.properties.EstoqueProperties.getVerde;
import static mobi.vesti.properties.EstoqueProperties.getVermelho;
import static mobi.vesti.properties.EstoqueProperties.getVinho;

public class ProdutosQaModasProperties {
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
        public static final String ID = "785b1535-248a-480f-9552-759537e58fed";
        public static final String NOME = "CAMISETA ESTAMPADA";
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 70,00");
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G", "GG")).cor(getPreto()).build()
        );

        public static List<ItensRequestVestClient> getEstoque(List<Map<String, String>> tamanhosQuantidades) {
            return ImmutableList.of(ItensRequestVestClient.builder().tamanho(tamanhosQuantidades).cor(getPreto()).build());
        }
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

        public static List<ItensRequestVestClient> getEstoque(String quantidadeDePecas) {
            return ImmutableList.of(
                    ItensRequestVestClient.builder().tamanho(getTamanhosPMG(quantidadeDePecas)).cor(getRosa()).build(),
                    ItensRequestVestClient.builder().tamanho(getTamanhosPMG(quantidadeDePecas)).cor(getVerde()).build(),
                    ItensRequestVestClient.builder().tamanho(getTamanhosPMG(quantidadeDePecas)).cor(getAzulClaro()).build(),
                    ItensRequestVestClient.builder().tamanho(getTamanhosPMG(quantidadeDePecas)).cor(getCinza()).build());
        }
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
                ItensRequestVestClient.builder().tamanho(getTamanhos("20", "36", "38", "42", "46", "40")).cor(getVerde()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhos("20", "36", "38", "42", "46", "40")).cor(getVermelho()).build());

        public static List<ItensRequestVestClient> getEstoque(String quantidadeDePecas) {
            return ImmutableList.of(
                    ItensRequestVestClient.builder().tamanho(getTamanhos(quantidadeDePecas, "36", "38", "42", "46", "40")).cor(getVerde()).build(),
                    ItensRequestVestClient.builder().tamanho(getTamanhos(quantidadeDePecas, "36", "38", "42", "46", "40")).cor(getVermelho()).build());
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

        public static List<ItensRequestVestClient> getEstoque(String quantidade) {
            return ImmutableList.of(
                    ItensRequestVestClient.builder().tamanho(getTamanhosPMG(quantidade)).cor(getPreto()).build(),
                    ItensRequestVestClient.builder().tamanho(getTamanhosPMG(quantidade)).cor(getCinza()).build(),
                    ItensRequestVestClient.builder().tamanho(getTamanhosPMG(quantidade)).cor(getAzulClaro()).build());
        }
    }

    public static class CAMISETA {
        public static final String ID = "b4a7ef4d-d61c-4275-8144-260962f6480c";
        public static final ProdutosDto SEM_PRECO = new ProdutosDto("CAMISETA", "Ver Preço");
        public static final ProdutosDto COM_PRECO = new ProdutosDto("CAMISETA", "R$ 69,90", "100% algodão");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G", "GG")).cor(getBranco()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G", "GG")).cor(getAmarelo()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G", "GG")).cor(getAzul()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G", "GG")).cor(getPreto()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G", "GG")).cor(getVinho()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G", "GG")).cor(getVermelho()).build()
        );
    }

}
