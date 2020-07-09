package tech.yurizp.PageTest;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import tech.yurizp.Dto.AnunciosVendasDto;
import tech.yurizp.Dto.VendedorDto;
import tech.yurizp.Factory.ContatoFactory;
import tech.yurizp.Factory.DocumentoFactory;
import tech.yurizp.PageObjects.CadastroVendedorPageObject;
import tech.yurizp.PageObjects.HomePage;
import tech.yurizp.TestContext;

import java.text.ParseException;
import java.util.List;

import static org.testng.Assert.assertTrue;

public class CadastroVendedorTest extends TestContext {

  public HomePage homePage;
  public CadastroVendedorPageObject cadastroVendedorPage;

  @BeforeClass()
  public void before() {
    cadastroVendedorPage = PageFactory.initElements(driver, CadastroVendedorPageObject.class);
    homePage = PageFactory.initElements(driver, HomePage.class);
  }

  @Test(groups = {"caminho-feliz", "cadastro-vendedor"})
  public void validarFluxoDeCadastroDeVendedorComSucesso()
      throws InterruptedException, ParseException {
    driver.navigate().to(homePage.getUrl());
    List<AnunciosVendasDto> anunciosVendasDtos = homePage.buscarTodosAnunciosComPreco();
    assertTrue(anunciosVendasDtos.isEmpty());
    homePage.clicarEmAnuncioDeProduto();
    String cnpj = DocumentoFactory.cnpj();
    VendedorDto vendedorDto =
        VendedorDto.builder()
            .cnpjCpf(cnpj)
            .confirmacaoSenha("@MinhaSenha123@")
            .email(ContatoFactory.email())
            .cnpjCpfOuEmail(cnpj)
            .senha("@MinhaSenha123@")
            .telefone(ContatoFactory.celular(10))
            .nome(ContatoFactory.nomeSobrenome())
            .build();
    cadastroVendedorPage.preencherFormularioCnpj(vendedorDto);
    cadastroVendedorPage.finalizarCadastro();
    anunciosVendasDtos = homePage.buscarTodosAnunciosComPreco();
    assertTrue(anunciosVendasDtos.isEmpty());
  }
}
