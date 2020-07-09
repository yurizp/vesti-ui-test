package tech.yurizp.PageTest;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tech.yurizp.Dto.AnunciosVendasDto;
import tech.yurizp.PageObjects.HomePage;
import tech.yurizp.TestContext;

import java.util.List;

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
    List<AnunciosVendasDto> anunciosVendasDtos = homePage.buscarTodosAnunciosComPreco();
    assertTrue(anunciosVendasDtos.isEmpty());
  }
}
