package mobi.vesti.webdriver.prototype;

import lombok.extern.slf4j.Slf4j;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class TestingBotPrototype {

    private static final String PREFIXO = "[TESTING BOT] - ";
    private static final String KEY = System.getenv("TESTING_BOOT_KEY");
    private static final String SECRET = System.getenv("TESTING_BOOT_SECRET");

    /***
     * Conecta a Testing Bot e retorna a URL da API do Selenium.
     * @return
     * @throws MalformedURLException
     */
    public URL URL() throws MalformedURLException {
        log.info("{}Buscando as credenciais da configuração.", PREFIXO);
        log.info("{}Chave de acesso Key: '{}'.", PREFIXO, KEY.substring(0, KEY.length() / 2));
        log.info("{}Chave de acesso Secret: '{}' .", PREFIXO, SECRET.substring(0, SECRET.length() / 2));
        String URL = "http://" + KEY + ":" + SECRET + "@hub.testingbot.com/wd/hub";
        return new URL(URL);
    }
}
