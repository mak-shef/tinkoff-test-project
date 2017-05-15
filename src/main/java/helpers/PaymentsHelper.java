package helpers;

import org.openqa.selenium.WebDriver;
import pages.PaymentsPage;
import ru.yandex.qatools.allure.annotations.Step;

/**
 * Хелпер для страницы "Платежи"
 */
public class PaymentsHelper extends MainPageHelper {
    public PaymentsHelper(WebDriver driver) {
        super(driver);
    }

    private PaymentsPage paymentsPage;

    public PaymentsPage getPaymentsPage() {
        if (paymentsPage == null) {
            paymentsPage = new PaymentsPage(driver);
        }
        return paymentsPage.ensurePageLoaded();
    }

    /**
     * Выбирает из списка "Коммунальные платежи"
     */
    @Step("Выбор пункта {0}")
    public void chooseCommunalPayments(String paymentName) {
        logger.info("Выбор пункта :" + paymentName);
        getPaymentsPage().getPaymentByName(paymentName).click();
    }

    /**
     * Ввод строки для поиска поставщика услуг
     *
     * @param searchString
     */
    @Step("Поиск наименования {0}")
    public void contextSearch(String searchString) {
        logger.info("Поиск наименования: " + searchString);
        getPaymentsPage().searchInput.sendKeys(searchString);
        getPaymentsPage().ensurePageLoaded();
    }

    /**
     * Выбор постащика из найденного списка по индексу. Индексация начинается с 1
     *
     * @param idx
     */
    @Step("Выбор поставщика по индексу {0}")
    public void choosePaymentsByIndex(int idx) {
        getPaymentsPage().getSearchResultList().get(idx - 1).click();
    }
}
