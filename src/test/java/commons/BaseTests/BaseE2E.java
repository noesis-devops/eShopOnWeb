package commons.BaseTests;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.microsoft.playwright.*;
import e2e.page.LoginPage;
import e2e.page.ProductPage;
import io.qameta.allure.Attachment;
import io.qameta.allure.Epic;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.parallel.Execution;
import org.junit.jupiter.api.parallel.ExecutionMode;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static commons.JsonData.getEnvironmentData;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Execution(ExecutionMode.SAME_THREAD)
@Epic("E2E")
public class BaseE2E {

    private static final String environment = "LOCAL"; // Define o ambiente de execução, podendo ser LOCAL ou WEB
    private static Playwright playwright;
    private static List<Browser> browsers;
    protected Page page;
    protected LoginPage loginPage;
    protected ProductPage productPage;
    private static final Path STATE_FILE_PATH = Paths.get("playwright/.auth/state.json");


    /***
     * Inicializa o Playwright e autentica o usuário
     */
    @BeforeAll
    public static void initializePlaywrightAndAuthenticate() throws IOException {
        playwright = Playwright.create();
        browsers = initializeBrowsers();

        // for (Browser browser : browsers) {
        //     authenticate(browser);
        // }
    }

    /***
     * Identifica quais navegadores serão utilizados nos testes conforme arquivo browsers.json
     * Define as opções de lançamento para os navegadores (headless, slowMo)
     */
    private static List<Browser> initializeBrowsers() throws IOException {
        Path path = Paths.get("src/test/java/resources/browsers.json");
        try (Reader reader = Files.newBufferedReader(path)) {
            Map<String, List<String>> config = new Gson().fromJson(reader, new TypeToken<Map<String, List<String>>>() {
            }.getType());
            List<String> browserNames = config.get("browsers");

            return browserNames.stream().map(browserName -> {
                BrowserType.LaunchOptions options = new BrowserType.LaunchOptions()
                        .setHeadless(Boolean.parseBoolean(getEnvironmentData("headlessMode")))
                        .setSlowMo(0);
                return switch (browserName) {
                    case "chromium" -> playwright.chromium().launch(options);
                    case "firefox" -> playwright.firefox().launch(options);
                    case "webkit" -> playwright.webkit().launch(options);
                    default -> throw new IllegalArgumentException("Unsupported browser: " + browserName);
                };
            }).toList();
        }
    }


    /***
     * Autentica o usuário no sistema gravando o estado da sessão em um arquivo state.json
     * para que seja reaproveitado nos testes
     */
    private static void authenticate(Browser browser) {
        if (Files.notExists(STATE_FILE_PATH)) {
            BrowserContext authContext = browser.newContext();
            Page authPage = authContext.newPage();
            LoginPage loginPage = new LoginPage(authPage, authContext);
            loginPage.login();
            authContext.storageState(new BrowserContext.StorageStateOptions().setPath(STATE_FILE_PATH));
            authContext.close();
        }
    }

    /***
     * Inicializa o contexto do navegador para cada teste
     * Inicia o trace para captura de evidências
     * Instancia a página e as classes de PageObjects com objetivo
     */
    @BeforeEach
    public void setup() {
        for (Browser browser : browsers) {
            setupBrowserContext(browser);
        }
    }

    private BrowserContext context;

    private void setupBrowserContext(Browser browser) {
        // context = browser.newContext(
        //         new Browser.NewContextOptions().setStorageStatePath(STATE_FILE_PATH)
        // );

        context = browser.newContext(
                new Browser.NewContextOptions());

        context.tracing().start(new Tracing.StartOptions()
                .setScreenshots(true)
                .setSnapshots(true)
                .setSources(true)
        );

        page = context.newPage();
        productPage = new ProductPage(page);
        loginPage = new LoginPage(page, context);
    }

    @AfterEach
    public void teardown() {
        try {
            // Parar o trace para o contexto atual e salvar o arquivo
            context.tracing().stop(new Tracing.StopOptions()
                    .setPath(Paths.get("target/trace_" + context.browser().browserType().name() + "_" +
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".zip"))
            );

            screenshot();
        } finally {
            if (context != null) {
                context.close(); // Fechar o contexto atual
            }
        }
    }

    /***
     * Encerra todos os navegadores e o Playwright
     */
    @AfterAll
    public static void closePlaywright() {
        for (Browser browser : browsers) {
            if (browser != null) {
                browser.close();
            }
        }
        if (playwright != null) {
            playwright.close();
        }
    }

    /***
     * Captura um screenshot da página atual, salva em target/screenshots
     * e anexa ao relatório Allure
     */
    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] screenshot() {
        try {
            Path screenshotsFolder = Paths.get("target/screenshots");
            if (!Files.exists(screenshotsFolder)) {
                Files.createDirectories(screenshotsFolder);
            }
            Path path = screenshotsFolder.resolve(
                    "Evidence_" + page.title() + " - " + LocalDateTime.now().format(
                            DateTimeFormatter.ofPattern("yyyyMMddHHmmss")) + ".jpg");
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(path)
                    .setQuality(50));
            return Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /***
     * Retorna a URL base conforme o ambiente configurado
     * em environment.json
     * @return URL base
     */
    public static String getBaseURL() {
        return switch (environment.toUpperCase()) {
            case "LOCAL" -> getEnvironmentData("local_eshop");
            case "WEB" -> getEnvironmentData("remote_eshop");
            default -> throw new IllegalArgumentException("Invalid environment: " + environment);
        };
    }
}
