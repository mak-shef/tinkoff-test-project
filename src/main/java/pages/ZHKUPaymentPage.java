package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

/**
 * Страница "ЖКУ-Москва\Оплата"
 */
public class ZHKUPaymentPage extends ZHKUMainPage {

    public ZHKUPaymentPage(WebDriver webDriver) {
        super(webDriver);
    }

    public WebElement getPayerCode() {
        return getVisibleElementByLocator(By.xpath(".//div[contains(@class, 'ui-form__row_text')]//input"));
    }

    public WebElement getDatePeriod() {
        return getVisibleElementByLocator(By.xpath(".//div[contains(@class, 'ui-form__row_date')]//input"));
    }

    public WebElement getAmount() {
        return getVisibleElementByLocator(By.xpath(".//div[contains(@class, 'ui-form__row_amount')]//input"));
    }

    public WebElement getAccountAmount() {
        return getVisibleElementByLocator(By.xpath(".//div[contains(@class, 'ui-form__row_account-amount')]//input"));
    }

    @FindBy(xpath = ".//div[contains(@class, 'ui-form__row_button-submit')]")
    public WebElement submitButton;

    private WebElement getVisibleElementByLocator(By by) {
        List<WebElement> elementList = webDriver.findElements(by);
        for (WebElement element : elementList) {
            if (element.isDisplayed())
                return element;
        }
        return null;
    }

    public String getErrorText(WebElement element) {
        List<WebElement> parents = element.findElements(By.xpath("./ancestor::div[@class='ui-form__field' ]"));
        Assert.assertEquals(parents.size(), 1, "Error find parent element for " + element.getText());
        List<WebElement> errors = parents.get(0).findElements(By.xpath(".//div[contains(@class, 'ui-form-field-error-message')]"));
        if (errors.size() > 0) return errors.get(0).getText();
        return "";
    }

    @Override
    public ZHKUPaymentPage ensurePageLoaded() {
        waitPageLoad.until(ExpectedConditions.attributeContains(zhkuPayLink.findElement(By.xpath("./..")),
                "class", "link_active"));
        return this;
    }

}
