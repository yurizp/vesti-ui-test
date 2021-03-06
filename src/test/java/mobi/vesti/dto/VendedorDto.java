package mobi.vesti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import mobi.vesti.utils.ObjectUtils;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VendedorDto {

    private String cnpjCpfOuEmail;
    private String cnpjCpf;
    private String email;
    private String telefone;
    private String senha;
    private String confirmacaoSenha;
    public String nome;

    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

}
