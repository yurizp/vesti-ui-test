package mobi.vesti.properties;

import mobi.vesti.dto.LoginDto;

public class LoginProperties {

    public static final LoginDto LOGIN_VALIDO = LoginDto.builder()
            .cnpj("72905050000129")
            .email("cliente-de-teste-1@tst.com")
            .razaoSocial("cliente-de-teste-1")
            .senha("123123")
            .build();

}