package mobi.vesti.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import mobi.vesti.utils.ObjectUtils;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.RemoteWebElement;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDto {

    public String nome;
    public WebElement categoriaElement;

    public void fechar() {
        ((RemoteWebElement) categoriaElement).findElementByTagName("i").click();
    }

    @SneakyThrows
    @Override
    public String toString() {
        return ObjectUtils.toString(this);
    }
}
