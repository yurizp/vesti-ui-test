package mobi.vesti.properties;

import com.google.common.collect.ImmutableList;
import mobi.vesti.client.request.ColorRequestVestClient;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class EstoqueProperties {

    public static ImmutableList<Map<String, String>> getTamanhosPMG() {
        return getTamanhosPMG("4");
    }

    public static ImmutableList<Map<String, String>> getTamanhosPMG(String quantidade) {
        return getTamanhos(quantidade, "P", "M", "G");
    }

    public static ImmutableList<Map<String, String>> getTamanhos(String quantidade, String... tamanho) {
        List<Map<String, String>> tamanhos = Arrays.asList(tamanho)
                .stream()
                .map(t -> Collections.singletonMap(t, quantidade))
                .collect(Collectors.toList());
        return ImmutableList.copyOf(tamanhos);
    }

    public static ColorRequestVestClient getRosa() {
        return ColorRequestVestClient.builder()
                .codigoCor("#ffc4f9")
                .id("c09e3095-fb64-41b9-ad8c-5744b34d740d")
                .nome("Rosa")
                .build();
    }

    public static ColorRequestVestClient getAzulClaro() {
        return ColorRequestVestClient.builder()
                .codigoCor("#5bb6f7")
                .id("bea7adf8-d12a-4d28-aa8b-cfc930f6be6f")
                .nome("Azul Claro")
                .build();
    }

    public static ColorRequestVestClient getVerde() {
        return ColorRequestVestClient.builder()
                .codigoCor("#03f11b")
                .id("08149abf-5778-4f2d-ad89-2a479c5df4cc")
                .nome("Verde")
                .build();
    }

    public static ColorRequestVestClient getCinza() {
        return ColorRequestVestClient.builder()
                .codigoCor("#c6c6c6")
                .id("d3cb9222-7b3d-4d4b-9d74-3f76a75fe942")
                .nome("Cinza")
                .build();
    }

    public static ColorRequestVestClient getPreto() {
        return ColorRequestVestClient.builder()
                .codigoCor("#000000")
                .id("463f9661-968d-4748-9346-863229b1dea7")
                .nome("Preto")
                .build();
    }

    public static ColorRequestVestClient getBranco() {
        return ColorRequestVestClient.builder()
                .codigoCor("#ffffff")
                .id("d351fa15-cf87-43e8-94c0-55607d8c6ab6")
                .nome("Branco")
                .build();
    }

    public static ColorRequestVestClient getAmarelo() {
        return ColorRequestVestClient.builder()
                .codigoCor("#ffe600")
                .id("d5851612-90ee-4bf2-93aa-132cb57b0c9b")
                .nome("Amarelo")
                .build();
    }

    public static ColorRequestVestClient getAzul() {
        return ColorRequestVestClient.builder()
                .codigoCor("#260ad0")
                .id("cd0f8bf7-c405-4e00-9db1-a57e6e0af8e4")
                .nome("Azul")
                .build();
    }

    public static ColorRequestVestClient getVinho() {
        return ColorRequestVestClient.builder()
                .codigoCor("#d12001")
                .id("ea803052-93ba-46d1-9452-7043a437dfd5")
                .nome("Vinho")
                .build();
    }

    public static ColorRequestVestClient getVermelho() {
        return ColorRequestVestClient.builder()
                .codigoCor("#f02410")
                .id("d476d8f1-5b93-4e43-972a-29e3968884fb")
                .nome("Vermelho")
                .build();
    }
}
