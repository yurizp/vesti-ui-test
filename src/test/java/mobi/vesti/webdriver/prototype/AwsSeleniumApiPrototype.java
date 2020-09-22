package mobi.vesti.webdriver.prototype;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.securitytoken.AWSSecurityTokenServiceClient;
import com.amazonaws.services.securitytoken.model.Credentials;
import com.amazonaws.services.securitytoken.model.GetSessionTokenRequest;
import lombok.extern.slf4j.Slf4j;
import software.amazon.awssdk.auth.credentials.AwsSessionCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.devicefarm.DeviceFarmClient;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlRequest;
import software.amazon.awssdk.services.devicefarm.model.CreateTestGridUrlResponse;

import java.net.MalformedURLException;
import java.net.URL;

@Slf4j
public class AwsSeleniumApiPrototype {

    private static final String PREFIXO = "[AWS FARM] - ";
    private static final String DEVICE_FARM_ARN = System.getenv("AWS_DEVICE_FARM_ARN");
    private static final String ACESS_KEY = System.getenv("AWS_ACESS_KEY");
    private static final String SECRET_KEY = System.getenv("AWS_SECRET_KEY");
    private static final int TOKEN_DURATION_SECONS = 36000;

    /***
     * Conecta a AWS Farm e retorna a URL da API do Selenium.
     * @return
     * @throws MalformedURLException
     */
    public URL URL() throws MalformedURLException {
        log.info("{}Iniciando a configuração.", PREFIXO);
        Credentials sessionCredentials = getSessionCredentials();
        log.info("{}Gerado o token de segurança.", PREFIXO);
        AwsSessionCredentials awsSessionCredentials = AwsSessionCredentials.create(sessionCredentials.getAccessKeyId(), sessionCredentials.getSecretAccessKey(), sessionCredentials.getSessionToken());
        log.info("{}Criada a sessão com o WebDriver remoto.", PREFIXO);
        DeviceFarmClient client = DeviceFarmClient.builder().region(Region.US_WEST_2)
                .credentialsProvider(StaticCredentialsProvider.create(awsSessionCredentials)).build();
        CreateTestGridUrlRequest request = CreateTestGridUrlRequest.builder().expiresInSeconds(TOKEN_DURATION_SECONS).projectArn(DEVICE_FARM_ARN).build();
        CreateTestGridUrlResponse response = client.createTestGridUrl(request);
        return new URL(response.url());
    }

    /***
     * Conecta a AWS paar gerar um token temporário.
     * @return
     */
    private Credentials getSessionCredentials() {
        log.info("{}Buscando as credenciais a configuração.", PREFIXO);
        log.info("{}Chave de acesso Acess Key: {} .", PREFIXO, ACESS_KEY.substring(0, ACESS_KEY.length() / 2));
        log.info("{}Chave de acesso Secret Key: {} .", PREFIXO, SECRET_KEY.substring(0, SECRET_KEY.length() / 2));
        log.info("{}Chave de acesso Device Farm: {} .", PREFIXO, DEVICE_FARM_ARN.substring(0, DEVICE_FARM_ARN.length() / 2));

        AWSSecurityTokenServiceClient stsClient = new AWSSecurityTokenServiceClient(new BasicAWSCredentials(ACESS_KEY, SECRET_KEY));
        GetSessionTokenRequest getSessionTokenRequest = new GetSessionTokenRequest();
        return stsClient.getSessionToken(getSessionTokenRequest).getCredentials();
    }
}
