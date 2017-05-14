package helpers;

import exceptions.AutoTestException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import pages.CommunalPaymentsPage;
import pages.RegionSearchPage;

/**
 * Хелпер страницы "Коммунальные платежи"
 */
public class CommunalPaymentsHelper extends MainPageHelper {

    private CommunalPaymentsPage communalPaymentsPage;

    public CommunalPaymentsHelper(WebDriver driver) {
        super(driver);
    }

    public CommunalPaymentsPage getCommunalPaymentsPage() {
        if (communalPaymentsPage == null) {
            communalPaymentsPage = new CommunalPaymentsPage(driver);
        }
        return communalPaymentsPage.ensurePageLoaded();
    }

    /**
     * Выполяет клик по поставщику усуг по индексу
     * @param idx - порядковый номер поставщика услуг. Нумерация начинается с 1.
     * @return наименование выбранного поставщика в формате String
     * @throws AutoTestException
     */
    public String clickCommunalItemsByIndex(int idx)throws AutoTestException{
        logger.info("Клик по записи с индесом: "+idx);
        WebElement item = getCommunalPaymentsPage().getCommunalItemsByIndex(idx);
        if(item == null) throw new AutoTestException(String.format("Элемент с индексом %s не найден!", idx));
        String itemName= item.getText();
        item.click();
        return itemName;
    }

    /**
     * Вовзращает наличие элемента поимени
     * @param name - наименование поставщика услуг
     * @return true -элемент найден,false - нет
     * @throws AutoTestException
     */
    public boolean isElementExistsByName(String name)throws AutoTestException{
        WebElement item = getCommunalPaymentsPage().getCommunalItemsByName(name);
        if(item == null) return false;
        return true;
    }

    /**
     * Возврщает наименование текущего региона
     *
     * @return
     */
    public String getRegionName() {
        return getCommunalPaymentsPage().regionInput.getText();
    }

    /**
     * Устанавливает регион
     *
     * @param setRegionName
     * @param finalRegionName
     */
    public void setRegionName(String setRegionName, String finalRegionName) {
        logger.info("Установка региона: "+setRegionName);
        if (!getRegionName().equals(finalRegionName)) {
            getCommunalPaymentsPage().regionInput.click();
            getRegionSearchPage().findAndClickRegion(setRegionName);
            getCommunalPaymentsPage().ensurePageLoaded();
        } else {
            logger.info("Регион уже установлен в значение: " + setRegionName);
        }
    }

    /**
     * Возвращает модальное окно поииска региона
     * @return RegionSearchPage
     */
    public RegionSearchPage getRegionSearchPage() {
        return new RegionSearchPage(driver).ensurePageLoaded();
    }
}
