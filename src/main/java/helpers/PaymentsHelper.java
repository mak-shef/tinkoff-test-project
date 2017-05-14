package helpers;

import org.openqa.selenium.WebDriver;
import pages.PaymentsPage;

/**
 * Хелпер для страницы "Платежи"
 */
public class PaymentsHelper extends MainPageHelper {
    public PaymentsHelper(WebDriver driver) {
        super(driver);
    }

    private PaymentsPage paymentsPage;

    public PaymentsPage getPaymentsPage(){
        if(paymentsPage == null){
            paymentsPage = new PaymentsPage(driver);
        }
        return paymentsPage.ensurePageLoaded();
    }

    /**
     * Выбирает из списка "Коммунальные платежи"
     */
    public void chooseCommunalPayments(String paymentName){
        logger.info("Выбор пункта :"+paymentName);
        getPaymentsPage().getPaymentByName(paymentName).click();
    }

    public void contextSearch(String searchString){
        logger.info("Поиск наименования: "+searchString);
        getPaymentsPage().searchInput.sendKeys(searchString);
        getPaymentsPage().ensurePageLoaded();
    }


}
