package mobi.vesti.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import mobi.vesti.utils.ObjectUtils;

import java.util.List;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EstoqueRequestVetClient {

    @Builder.Default
    @JsonProperty("scheme_url")
    private String schemaUrl = "qamodas";

    @JsonProperty("itens")
    private List<ItensRequestVestClient> itens;

    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
