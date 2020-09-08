package mobi.vesti.dto;

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
public class LoginDto {

    private String cnpj;
    private String senha;
    private String email;
    private String razaoSocial;

    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
