package mobi.vesti.test.PageTest;

import mobi.vesti.pageobjects.HomePage;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import mobi.vesti.dto.AnunciosVendasDto;
import mobi.vesti.test.TestContext;

import java.util.List;
import java.util.stream.Collectors;

import static org.testng.Assert.assertTrue;

public class HomeTest extends TestContext {

  public HomePage homePage;

  @BeforeClass
  public void before() {
    homePage = PageFactory.initElements(driver, HomePage.class);
  }

  @Test(groups = {"caminho-feliz", "home"})
    public void validaSeAnunciosEstaoSemPreco() {
    driver.navigate().to(homePage.getUrl());
    List<AnunciosVendasDto> collect = homePage.getAnunciosProdutosDto().stream().filter(anunciosVendasDto -> "Ver Preço".equalsIgnoreCase(anunciosVendasDto.getPreco())).collect(Collectors.toList());
    List<AnunciosVendasDto> anunciosVendasDtos = homePage.buscarTodosAnunciosComPreco();
    assertTrue(anunciosVendasDtos.isEmpty(), "[validaSeAnunciosEstaoSemPreco] - Existem anuncios com preço de venda.");
  }

}
