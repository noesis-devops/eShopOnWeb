package e2e.tests;

import commons.BaseTests.BaseE2E;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.SeverityLevel.BLOCKER;
import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic("E2E")
@Feature("Product")
public class ProductTest extends BaseE2E {

    @Test
    @Severity(CRITICAL)
    @DisplayName("Test order product")
    void testOrderProduct() {
        loginPage.login();
        productPage.navigate();
        productPage.addProductToBasket();
        productPage.checkout();
        productPage.payment();
        productPage.assertOrder("Thanks for your Order!");
    }

    @Test
    @Severity(BLOCKER)
    @DisplayName("Test order history")
    void testGetOrderHistory() {
        loginPage.login();
        productPage.navigate();
        productPage.orders();
        productPage.assertOrderHistoryTitle("My Order History");
    }

    @Test
    @Severity(BLOCKER)
    @DisplayName("Test filter product")
    void testFilterProduct() {
        loginPage.login();
        productPage.navigate();
        productPage.filterBrand();
        productPage.filterType();
        productPage.applyFilter();
        productPage.assertFilteredProduct(".NET BLACK & WHITE mug");
    }
}
