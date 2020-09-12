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
import java.util.Map;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItensRequestVestClient {

    @JsonProperty("color")
    private ColorRequestVestClient cor;

    @JsonProperty("sizes")
    private List<Map<String, String>> tamanho;

    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }


}
