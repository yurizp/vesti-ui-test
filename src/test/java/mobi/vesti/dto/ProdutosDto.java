package mobi.vesti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import mobi.vesti.utils.ObjectUtils;

import java.util.Objects;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProdutosDto {

    public String titulo;
    public String preco;
    public String descricao;

    public ProdutosDto(String titulo, String preco) {
        this.titulo = titulo;
        this.preco = preco;
    }

    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProdutosDto)) return false;
        ProdutosDto that = (ProdutosDto) o;
        return Objects.equals(getTitulo(), that.getTitulo()) &&
                Objects.equals(getPreco(), that.getPreco());
    }

}