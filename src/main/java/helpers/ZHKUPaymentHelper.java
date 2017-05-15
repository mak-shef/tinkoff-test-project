package helpers;

import org.openqa.selenium.WebDriver;
import pages.ZHKUMainPage;
import pages.ZHKUPaymentPage;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Хелпер страницы "ЖКУ-Москва", "ЖКУ-Москва"
 */
public class ZHKUPaymentHelper extends MainPageHelper {
    public ZHKUPaymentHelper(WebDriver driver) {
        super(driver);
    }

    private ZHKUMainPage zhkuMainPage;
    private ZHKUPaymentPage zhkuPaymentPage;

    public ZHKUMainPage getZhkuMainPage() {
        if (zhkuMainPage == null) {
            zhkuMainPage = new ZHKUMainPage(driver);
        }
        return zhkuMainPage.ensurePageLoaded();
    }

    public ZHKUPaymentPage getZhkuPaymentPage() {
        if (zhkuPaymentPage == null) {
            zhkuPaymentPage = new ZHKUPaymentPage(driver);
        }
        return zhkuPaymentPage.ensurePageLoaded();
    }

    /**
     * Переход на вкладку Оплатить ЖКУ в Москве
     */
    @Step("Переключиться на вкладку 'Оплатить ЖКУ в Москве'")
    public void switchToPaymentPage() {
        logger.info("Переключиться на вкладку 'Оплатить ЖКУ в Москве'");
        getZhkuMainPage().zhkuPayLink.click();
        getZhkuPaymentPage().ensurePageLoaded();

    }
}
