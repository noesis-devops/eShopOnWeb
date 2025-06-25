package e2e.page;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import commons.BaseTests.BaseE2E;
import io.qameta.allure.Step;

import static org.assertj.core.api.Assertions.assertThat;


public class ProductPage {

    private static final String FILTER_BRAND = "//select[contains(@id,'CatalogModel_BrandFilterApplied')]";
    private static final String FILTER_TYPE = "(//select[@class='esh-catalog-filter'])[2]";
    private static final String SUBMIT_FILTER = "//input[@class='esh-catalog-send']";
    private final Page page;

    private static final String BTN_CHECKOUT = "//a[@href='/Basket/Checkout']";
    private static final String BTN_PAY_NOW = "//input[contains(@type,'submit')]";
    private static final String MSG_ORDER = "//h1[contains(.,'Thanks for your Order!')]";
    private static final String BTN_ADD_CART = "(//input[@class='esh-catalog-button'])[1]";
    private static final String CURRENT_USER = "//div[@class='esh-identity-name']";
    private static final String MY_ORDERS = "(//div[contains(.,'My orders')])[4]";
    private static final String ORDER_TITLE = "//h1[contains(.,'My Order History')]";
    private static final String FILTERED_PRODUCT = "//span[contains(.,'.NET Black & White Mug')]";


    public ProductPage(Page page) {
        this.page = page;
    }

    @Step("Add product to basket")
    public void addProductToBasket() {
        page.click(BTN_ADD_CART);
    }

    @Step("Checkout")
    public void checkout() {
        page.click(BTN_CHECKOUT);
    }

    @Step("Payment")
    public void payment() {
        page.click(BTN_PAY_NOW);
    }

    @Step("Assert order")
    public void assertOrder(String message) {
        assertThat(page.locator(MSG_ORDER).innerText()).isEqualTo(message);
    }

    @Step("Show orders list")
    public void orders() {
        page.hover(CURRENT_USER);
        page.click(MY_ORDERS);
    }

    @Step("Assert order history")
    public void assertOrderHistoryTitle(String title) {
        assertThat(page.locator(ORDER_TITLE).innerText()).isEqualTo(title);
    }

    public void filterBrand() {
        Locator brand = page.locator(FILTER_BRAND);
        brand.selectOption(".NET");
    }

    @Step("Filter product type")
    public void filterType() {
        Locator type = page.locator(FILTER_TYPE);
        type.selectOption("Mug");
    }

    @Step("Apply filter")
    public void applyFilter() {
        page.click(SUBMIT_FILTER);
    }

    @Step("Assert filtered product")
    public void assertFilteredProduct(String product) {
        page.locator(FILTERED_PRODUCT).isVisible();
        assertThat(page.locator(FILTERED_PRODUCT).innerText()).isEqualTo(product);
    }

    @Step("Acessar homepage")
    public void navigate() {
        page.navigate(BaseE2E.getBaseURL());
    }
}
