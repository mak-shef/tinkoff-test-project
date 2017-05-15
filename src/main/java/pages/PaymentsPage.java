package pages;

import exceptions.AutoTestException;
import org.omg.PortableInterceptor.ServerRequestInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

/**
 * Страница "Платежи"
 */
public class PaymentsPage extends MainPage {
    public PaymentsPage(WebDriver webDriver) {
        super(webDriver);
    }

    String PAGE_URL = "payments";

    @FindBy(xpath = ".//a[@href = '/payments/']")
    public WebElement paymentsLink;

    @FindBy(xpath = ".//input[@class='ui-search-input__input']")
    public WebElement searchInput;

    public List<WebElement> getSearchResultList() {
        List<WebElement> searchResultList = webDriver.findElements(
                By.xpath(".//div[@class= 'ui-search-flat']/span"));
        return searchResultList;

    }

    /**
     * Возвращает элемент по наименованию категории платежа
     *
     * @param paymentName
     * @return WebElement
     * @throws AutoTestException
     */
    public WebElement getPaymentByName(String paymentName) throws AutoTestException {
        List<WebElement> paymentsList = webDriver.findElements(By.linkText(paymentName));
        int size = paymentsList.size();
        if (size == 1) {
            return paymentsList.get(0);
        }
        if (size == 0) {
            throw new AutoTestException(String.format("Не найдено не одного элемента с наименованием %s", paymentName));
        }
        throw new AutoTestException(String.format("Найдено %s элементов с наименованием %s",
                String.valueOf(size), paymentName));
    }

    @Override
    public PaymentsPage ensurePageLoaded() {
        waitPageLoad.until(ExpectedConditions.visibilityOf(paymentsLink));
        waitPageLoad.until(ExpectedConditions.attributeContains(paymentsLink, "class", "link_active"));
        return this;
    }
}
