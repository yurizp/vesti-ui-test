package mobi.vesti.client.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import mobi.vesti.utils.ObjectUtils;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ColorRequestVestClient {

    @JsonProperty("id")
    private String id;
    @JsonProperty("code")
    private String codigoCor;
    @JsonProperty("name")
    private String nome;

    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
