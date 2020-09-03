package mobi.vesti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

}
