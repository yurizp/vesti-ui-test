package mobi.vesti.properties;

import mobi.vesti.dto.ProdutosDto;

import java.util.Arrays;
import java.util.List;

public class ProdutosProperties {
    public static final ProdutosDto PACK_JEANS_SEM_PRECO = new ProdutosDto("PACK JEANS", "Ver Preço");
    public static final ProdutosDto CALCA_JEANS_PACK_SEM_PRECO = new ProdutosDto("CALÇA JEANS PACK", "Ver Preço");
    public static final ProdutosDto JAQUETA_SEM_PRECO = new ProdutosDto("JAQUETA", "Ver Preço");
    public static final ProdutosDto CALCA_JEANS_MILANDA_SEM_PRECO = new ProdutosDto("CALÇA JEANS MILADA", "Ver Preço");
    public static final ProdutosDto VESTIDO_LONGO_SEM_PRECO = new ProdutosDto("VESTIDO LONGO", "Ver Preço");
    public static final ProdutosDto POLO_SEM_PRECO = new ProdutosDto("POLO", "Ver Preço");
    public static final ProdutosDto CAMISETA_ESTAMPADA_SEM_PRECO = new ProdutosDto("CAMISETA ESTAMPADA", "Ver Preço");
    public static final ProdutosDto BLUSA_SEM_PRECO = new ProdutosDto("BLUSA", "Ver Preço");
    public static final ProdutosDto CAMISETA_SEM_PRECO = new ProdutosDto("CAMISETA", "Ver Preço");
    public static final List<ProdutosDto> PRODUTOS_HOME_SEM_PRECO = Arrays.asList(PACK_JEANS_SEM_PRECO,
            CALCA_JEANS_PACK_SEM_PRECO,
            JAQUETA_SEM_PRECO,
            VESTIDO_LONGO_SEM_PRECO,
            POLO_SEM_PRECO,
            CAMISETA_ESTAMPADA_SEM_PRECO,
            BLUSA_SEM_PRECO,
            CAMISETA_SEM_PRECO,
            CALCA_JEANS_MILANDA_SEM_PRECO);

    public static final ProdutosDto PACK_JEANS_COM_PRECO = new ProdutosDto("PACK JEANS", "R$ 50,00");
    public static final ProdutosDto CALCA_JEANS_PACK_COM_PRECO = new ProdutosDto("CALÇA JEANS PACK", "R$ 65,50");
    public static final ProdutosDto JAQUETA_COM_PRECO = new ProdutosDto("JAQUETA", "R$ 299,90");
    public static final ProdutosDto CALCA_JEANS_MILANDA_COM_PRECO = new ProdutosDto("CALÇA JEANS MILADA", "R$ 79,90");
    public static final ProdutosDto VESTIDO_LONGO_COM_PRECO = new ProdutosDto("VESTIDO LONGO", "R$ 199,99");
    public static final ProdutosDto POLO_COM_PRECO = new ProdutosDto("POLO", "R$ 29,90");
    public static final ProdutosDto CAMISETA_ESTAMPADA_COM_PRECO = new ProdutosDto("CAMISETA ESTAMPADA", "R$ 70,00");
    public static final ProdutosDto BLUSA_COM_PRECO = new ProdutosDto("BLUSA", "R$ 159,90 R$ 99,99");
    public static final ProdutosDto CAMISETA_COM_PRECO = new ProdutosDto("CAMISETA", "R$ 69,90");
    public static final List<ProdutosDto> PRODUTOS_HOME_COM_PRECO = Arrays.asList(PACK_JEANS_COM_PRECO,
            CALCA_JEANS_PACK_COM_PRECO,
            JAQUETA_COM_PRECO,
            VESTIDO_LONGO_COM_PRECO,
            POLO_COM_PRECO,
            CAMISETA_ESTAMPADA_COM_PRECO,
            BLUSA_COM_PRECO,
            CAMISETA_COM_PRECO,
            CALCA_JEANS_MILANDA_COM_PRECO);
}
