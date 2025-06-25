package e2e.tests;

import commons.BaseTests.BaseE2E;
import commons.Generator;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static commons.JsonData.getEnvironmentData;
import static io.qameta.allure.SeverityLevel.BLOCKER;


@Epic("E2E")
@Feature("Authentication")
class LoginTest extends BaseE2E {

    @Test
    @Tag("regression")
    @DisplayName("Test login with valid data")
    @Severity(BLOCKER)
    void testLogin() {
        loginPage.navigate();
        loginPage.clickLoginPage();
        loginPage.fillEmail(getEnvironmentData("eShopUserAdmin"));
        loginPage.fillPassword(getEnvironmentData("eShopPassword"));
        loginPage.clickLoginButton();
        loginPage.validateLogin(getEnvironmentData("eShopUserAdmin"));
    }

    @Test
    @Tag("regression")
    @DisplayName("Test Authentication with invalid data")
    @Severity(BLOCKER)
    void testLoginInvalidData() {
        loginPage.navigate();
        loginPage.clickLoginPage();
        loginPage.fillEmail("invalid@email.com");
        loginPage.fillPassword("0000");
        loginPage.clickLoginButton();
        loginPage.validateLoginError("Invalid login attempt.");
    }

    @Test
    @DisplayName("Test new user Registration")
    void testRegisterNewAccount() {
        loginPage.navigate();
        loginPage.clickLoginPage();
        loginPage.clickRegisterPage();
        loginPage.fillEmail(Generator.email());
        loginPage.fillPassword(getEnvironmentData("eShopPassword"));
        loginPage.fillConfirmPassword(getEnvironmentData("eShopPassword"));
        loginPage.clickRegisterButton();
        //loginPage.validateLogin();
    }
}



