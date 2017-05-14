package pages;

import exceptions.AutoTestException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

/**
 * Страница "Коммунальные платежи"
 */
public class CommunalPaymentsPage extends MainPage {
    public CommunalPaymentsPage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(xpath = ".//div[text()='Коммунальные платежи']")
    public WebElement communalRegionString;

    @FindAll(@FindBy(xpath = ".//ul[contains(@class, 'ui-menu_icons')]/li"))
    public List<WebElement> communalItems;

    @FindBy(xpath = ".//span[@class='ui-link payment-page__title_inner']")
    public WebElement regionInput;

    /**
     * Возвращает элемент по индексу
     * @param idx - порядковый номер поставщика услуг. Нумерация начинается с 1.
     * @return наименование выбранного поставщика в формате String
     * @throws AutoTestException
     */
    public WebElement getCommunalItemsByIndex(int idx)throws AutoTestException{
        waitPageLoad.until(ExpectedConditions.visibilityOfAllElements(communalItems));
        if((communalItems.size() < idx)){
            return null;
        }
        return communalItems.get(idx - 1);
    }

    /**
     * Находит поставщика услуг по наименованию
     * @param name - наименования поставщика услуг
     * @return WebElement
     */
    public WebElement getCommunalItemsByName(String name){
        waitPageLoad.until(ExpectedConditions.visibilityOfAllElements(communalItems));
        if(communalItems.size() >= 0) {
            for (WebElement communalItem : communalItems) {
                if (communalItem.getText().equals(name)) {
                    return communalItem;
                }
            }
        }
        return null;
    }

    @Override
    public CommunalPaymentsPage ensurePageLoaded() {
        waitPageLoad.until(ExpectedConditions.textToBePresentInElementLocated(By.xpath(".//div"), "Коммунальные платежи"));
        return this;
    }
}
