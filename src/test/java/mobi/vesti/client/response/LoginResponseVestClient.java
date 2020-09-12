package mobi.vesti.client.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import mobi.vesti.utils.ObjectUtils;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponseVestClient {

    @JsonProperty("user")
    private UserResponseVestClient user;

    @JsonProperty("token")
    private String token;


    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
