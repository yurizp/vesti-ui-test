package mobi.vesti.client;


import mobi.vesti.client.request.EstoqueRequestVetClient;
import mobi.vesti.client.request.ItensRequestVestClient;
import mobi.vesti.client.response.LoginResponseVestClient;
import mobi.vesti.properties.LoginProperties;
import mobi.vesti.utils.ObjectUtils;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

import java.util.List;

public class VestClient {

    private static final String BASE_URL = "https://hapi.meuvesti.com/api/appvendas";
    private static final String LOGIN_URI = "/login";
    private static final String ESTOQUE_URI = "/stocks/%s/product?v=1.0";

    private static OkHttpClient client = new OkHttpClient();
    private static MediaType mediaType = MediaType.parse("application/json");

    public static LoginResponseVestClient autenticacao() throws Exception {
        RequestBody body = RequestBody.create(mediaType, LoginProperties.LOGIN_API.toString());
        Request request = new Request.Builder()
                .url(BASE_URL + LOGIN_URI)
                .post(body)
                .addHeader("content-type", "application/json")
                .build();

        String response = client.newCall(request).execute().body().string();
        return ObjectUtils.OBJECT_MAPPER.readValue(response, LoginResponseVestClient.class);
    }

    public static void adicionarEstoque(String productId, List<ItensRequestVestClient> itens) throws Exception {
        RequestBody body = RequestBody.create(mediaType, EstoqueRequestVetClient.builder().itens(itens).build().toString());
        Request request = new Request.Builder()
                .url(BASE_URL + String.format(ESTOQUE_URI, productId))
                .put(body)
                .addHeader("authorization", "Bearer " + autenticacao().getToken())
                .addHeader("content-type", "application/json")
                .build();
        String response = client.newCall(request).execute().body().string();
        System.out.println(response);
    }

    public static void main(String[] args) throws Exception {
        String token = "Bearer " + autenticacao().getToken();
        System.out.println(token);
    }
}
