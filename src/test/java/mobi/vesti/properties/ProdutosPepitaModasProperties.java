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

import static mobi.vesti.properties.EstoqueProperties.getAzul;
import static mobi.vesti.properties.EstoqueProperties.getPreto;
import static mobi.vesti.properties.EstoqueProperties.getTamanhos;

public class ProdutosPepitaModasProperties {

    public static class BLUSA_FEMININA {
        public static final String ID = "05def72a-f742-4c5f-9c59-49d4a5a88837";
        public static final String NOME = "BLUSA FEMININA";
        public static final ProdutosDto COM_PRECO = new ProdutosDto(NOME, "R$ 70,00");
        public static final ProdutosDto SEM_PRECO = new ProdutosDto(NOME, "Ver Preço");
        public static final List<ItensRequestVestClient> ESTOQUE_REQUEST = ImmutableList.of(
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G")).cor(getAzul()).build(),
                ItensRequestVestClient.builder().tamanho(getTamanhos("10", "P", "M", "G")).cor(getPreto()).build()
        );

        public static List<ItensRequestVestClient> getEstoque(List<Map<String, String>> tamanhosQuantidades) {
            return ImmutableList.of(ItensRequestVestClient.builder().tamanho(tamanhosQuantidades).cor(getPreto()).build());
        }
    }
}