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
public class LoginVestClientRequest {

    @JsonProperty("password")
    private String senha;
    @JsonProperty("email")
    private String email;

    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
