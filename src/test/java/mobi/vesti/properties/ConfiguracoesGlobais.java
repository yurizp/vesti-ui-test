package mobi.vesti.properties;

public class ConfiguracoesGlobais {
    public static final String BASE_URL = "https://happweb.vesti.mobi/catalogo/qamodas";

    public static String criarUri(String URI){
        return String.format("%s/s%",BASE_URL, URI);
    }
}
