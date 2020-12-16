package mobi.vesti.client;


import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import mobi.vesti.client.request.EstoqueRequestVetClient;
import mobi.vesti.client.request.FormaPagamentoDeleteRequestVestiClient;
import mobi.vesti.client.request.ItensRequestVestClient;
import mobi.vesti.client.request.LoginVestClientRequest;
import mobi.vesti.client.response.EnderecoResponseVestiClient;
import mobi.vesti.client.response.FormaPagamentoResponseVestiClient;
import mobi.vesti.client.response.LoginResponseVestClient;
import mobi.vesti.client.response.PedidoResponse;
import mobi.vesti.dto.LoginDto;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.utils.ObjectUtils;
import mobi.vesti.utils.ResourceUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
public class VestClient {

    private static final String BASE_URL_APPVENDAS = "https://hapi.meuvesti.com/api/appvendas";
    private static final String LOGIN_URI = "/login";
    private static final String ESTOQUE_URI = "/stocks/%s/product?v=1.0";
    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    private static LoginResponseVestClient loginResponseVestClient;

    private static OkHttpClient client = new OkHttpClient();

    public static LoginResponseVestClient autenticacao(LoginVestClientRequest login) throws Exception {
        RequestBody body = RequestBody.create(JSON, login.toString());
        Request request = new Request.Builder()
                .url(BASE_URL_APPVENDAS + LOGIN_URI)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();

        String response = client.newCall(request).execute().body().string();
        log.info("Chamada para API de autenticação, resultado: {}", response);
        loginResponseVestClient = ObjectUtils.OBJECT_MAPPER.readValue(response, LoginResponseVestClient.class);
        return loginResponseVestClient;
    }

    public static void adicionarEstoque(String productId, List<ItensRequestVestClient> itens, String ambiente) throws Exception {
        RequestBody body = RequestBody.create(JSON, EstoqueRequestVetClient.builder().schemaUrl(ambiente).itens(itens).build().toString());
        Request request = new Request.Builder()
                .url(BASE_URL_APPVENDAS + String.format(ESTOQUE_URI, productId))
                .put(body)
                .addHeader("authorization", "Bearer " + autenticacao(LoginProperties.LOGIN_API).getToken())
                .addHeader("content-type", "application/json")
                .build();
        String response = client.newCall(request).execute().body().string();
        log.info("Chamada para API de adicionar estoque, resultado: {}", response);
        assertThat("{\"result\":{\"success\":true,\"message\":\"Ok\",\"messages\":\"\"}}").isEqualToIgnoringNewLines(response);
    }

    public static void excluirEnderecos(String companyId, LoginVestClientRequest login) {
        EnderecoResponseVestiClient enderecos = buscarEnderecos(companyId, login);
        enderecos.getEnderecoResponses().forEach(endereco -> excluirEnderecos(endereco));
    }

    @SneakyThrows
    private static void excluirEnderecos(EnderecoResponseVestiClient.EnderecoResponse enderecos) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://hapi.meuvesti.com/api/appcompras/address/"+enderecos.getId())
                .delete()
                .addHeader("authorization", "Bearer " + autenticacao(LoginProperties.LOGIN_API).getToken())
                .build();

        client.newCall(request).execute().body().string();
    }

    @SneakyThrows
    private static EnderecoResponseVestiClient buscarEnderecos(String companyId, LoginVestClientRequest login) {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://hapi.meuvesti.com/api/appcompras/addresses/?v=1.0")
                .get()
                .addHeader("authorization", "Bearer " + autenticacao(login).getToken())
                .build();

        String response = client.newCall(request).execute().body().string();
        log.info("Chamada para API de autenticação, resultado: {}", response);
        EnderecoResponseVestiClient endereco = ObjectUtils.OBJECT_MAPPER.readValue(response, EnderecoResponseVestiClient.class);
        return endereco;
    }
}
