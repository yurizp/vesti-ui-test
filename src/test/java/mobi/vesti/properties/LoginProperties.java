package mobi.vesti.properties;

import mobi.vesti.client.request.LoginVestClientRequest;
import mobi.vesti.dto.LoginDto;

public class LoginProperties {

    public static final LoginDto LOGIN_VALIDO_CNPJ_QAMODAS = LoginDto.builder()
            .documento("72905050000129")
            .email("cliente-de-teste-1@tst.com")
            .razaoSocial("cliente-de-teste-1")
            .senha("123123")
            .build();

    public static final LoginDto LOGIN_VALIDO_EMAIL_QAMODAS = LoginDto.builder()
            .documento("84682934074")
            .email(" testecpf@tst.com")
            .razaoSocial("cliente-de-teste-1")
            .senha("123123")
            .build();

    public static final LoginDto LOGIN_VALIDO_CPF_QAMODAS = LoginDto.builder()
            .documento("84682934074")
            .email("testecpf@tst.com")
            .razaoSocial("cliente-de-teste-1")
            .senha("123123")
            .build();

    public static final LoginVestClientRequest LOGIN_API = LoginVestClientRequest.builder()
            .email("joaosilva@interativasoftware.com.br")
            .senha("123")
            .build();

    public static final LoginDto LOGIN_VALIDO_CNPJ_PEPITAMODAS = LoginDto.builder()
            .documento("34310487000120")
            .senha("123123")
            .email("cliente-de-teste-3@tst.com")
            .build();
    public static final LoginDto LOGIN_VALIDO_CNPJ_QAMODAS2 = LoginDto.builder()
            .email("testecartao3@tst.com")
            .documento("03956716000155")
            .senha("123123")
            .build();
}
