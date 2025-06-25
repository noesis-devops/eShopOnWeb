package commons.BaseTests;

import api.requests.UsersPage;
import com.microsoft.playwright.APIRequest;
import com.microsoft.playwright.APIRequestContext;
import com.microsoft.playwright.Playwright;
import commons.Generator;
import commons.JsonData;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.util.HashMap;
import java.util.Map;


@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.CONCURRENT)
@Epic("API")
public class BaseAPI {

    private static final String environment = "WEB";
    protected Playwright playwright;
    protected APIRequestContext request;
    protected Generator generator;


    /***
     * Inicializa o Playwright e cria um contexto para as requisições
     */
    @BeforeAll
    void beforeAll() {
        playwright = Playwright.create();
        request = playwright.request().newContext(new APIRequest.NewContextOptions()
                .setBaseURL(getBaseURL()) //
                .setExtraHTTPHeaders(headers));
        UsersPage usersPage = new UsersPage();
        usersPage.createDefaultUser();
    }


    /***
     * Encerra o Playwright
     */
    @AfterAll
    void afterAll() {
        request.dispose();
        playwright.close();
    }

    /***
     * Define os cabeçalhos padrão para as requisições
     */
    static Map<String, String> headers =
            new HashMap<>() {
                {
                    put("Content-type", "application/json");
                }
            };

    /***
     * Retorna a URL base conforme o ambiente de execução
     * @return URL base
     */
    private static String getBaseURL() {
        return switch (environment.toUpperCase()) {
            case "LOCAL" -> JsonData.getEnvironmentData("local_url");
            case "WEB" -> JsonData.getEnvironmentData("remote_url");
            default -> throw new IllegalArgumentException("Invalid environment: " + environment);
        };
    }
}
