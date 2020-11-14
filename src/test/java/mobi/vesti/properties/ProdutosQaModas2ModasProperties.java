package mobi.vesti.properties;

import com.google.common.collect.ImmutableList;
import mobi.vesti.client.request.ColorRequestVestClient;
import mobi.vesti.client.request.ItensRequestVestClient;
import mobi.vesti.dto.ProdutosDto;

import java.util.List;

import static mobi.vesti.properties.EstoqueProperties.getTamanhos;

public class ProdutosQaModas2ModasProperties {


    public static class CALCA_ALGODAO {
        public static final String ID = "c8a90d80-a841-45fa-be28-28260ccaf673";
        public static final String NOME = "Calça algodão";
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 69,90");
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhos("1", "P", "M")).cor(getBranco()).build());
        public static final List<ItensRequestVestClient> SEM_ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhos("0", "P", "M")).cor(getBranco()).build());

    }


    public static ColorRequestVestClient getBranco() {
        return ColorRequestVestClient.builder()
                .codigoCor("#FFFFFF")
                .id("f40c5459-8a5f-4fd3-bbf9-4d4ff30551be")
                .nome("Branco")
                .build();
    }
}
