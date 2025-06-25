package e2e.page;

import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import commons.BaseTests.BaseE2E;
import io.qameta.allure.Step;

import static commons.JsonData.getEnvironmentData;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class LoginPage {

    private final Page page;

    private static final String INPUT_EMAIL = "//input[@id='Input_Email']";
    private static final String INPUT_PASSWORD = "//input[@id='Input_Password']";
    private static final String BTN_LOGIN = "//button[@type='submit']";
    private static final String LINK_LOGIN_PAGE = "//a[@href='/Identity/Account/Login']";
    private static final String ALERT_LOGIN_ERROR_MESSAGE = "//li[contains(.,'Invalid login attempt.')]";
    private static final String LOGGED_USER = "//div[@class='esh-identity-name']";
    private static final String URL_REGISTER = "//a[@href='/Identity/Account/Register?returnUrl=%2F']";
    private static final String INPUT_NEW_USER_PASSWORD_CONFIRMATION = "//input[contains(@id,'ConfirmPassword')]";
    private static final String BTN_REGISTER = "//button[@type='submit']";


    public LoginPage(Page page, BrowserContext context) {
        this.page = page;
    }

    @Step("Acessar homepage")
    public void navigate() {
        page.navigate(BaseE2E.getBaseURL());
    }

    private void loggout() {
        page.hover(LOGGED_USER);
        page.click("//*[@id=\"logoutForm\"]/section[2]/a[4]/div");
    }

    @Step("Preencher o campo e-mail")
    public void fillEmail(String email) {
        page.fill(INPUT_EMAIL, email);
    }

    @Step("Preencher o campo de senha")
    public void fillPassword(String password) {
        page.fill(INPUT_PASSWORD, password);
    }

    @Step("Clicar no botão de login")
    public void clickLoginButton() {
        page.click(BTN_LOGIN);
    }

    @Step("Validar mensagem de erro de login")
    public void validateLoginError(String message) {
        Locator locator = page.locator(ALERT_LOGIN_ERROR_MESSAGE);

        if (!locator.isVisible()) {
            page.waitForTimeout(400);
        }
        assertThat(locator.textContent()).isEqualTo(message);
    }

    @Step("Validar login com sucesso")
    public void validateLogin(String user) {
        Locator locator = page.locator(LOGGED_USER);
        assertThat(locator.textContent())
                .contains(user);
    }

    @Step("Realizar login")
    public void login() {
        navigate();
        clickLoginPage();
        fillEmail(getEnvironmentData("eShopUserAdmin"));
        fillPassword(getEnvironmentData("eShopPassword"));
        clickLoginButton();
        validateLogin(getEnvironmentData("eShopUserAdmin"));
    }

    @Step("Acessar página de login")
    public void clickLoginPage() {
        page.click(LINK_LOGIN_PAGE);
    }

    @Step("Acessar registro de novo usuário")
    public void clickRegisterPage() {
        page.click(URL_REGISTER);
    }

    @Step("Preencher o campo Confirm password")
    public void fillConfirmPassword(String s) {
        page.fill(INPUT_NEW_USER_PASSWORD_CONFIRMATION, s);
    }

    @Step("Registrar novo usuário")
    public void clickRegisterButton() {
        page.click(BTN_REGISTER);
    }
}
