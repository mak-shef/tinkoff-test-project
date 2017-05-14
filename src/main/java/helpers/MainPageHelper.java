package helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import pages.MainPage;

/**
 * Хедлпер главной страицы
 */
public class MainPageHelper extends BaseHelper{

    public MainPageHelper(WebDriver driver) {
        super(driver);
    }

    private MainPage mainPage;
    public Logger logger = Logger.getLogger(MainPageHelper.class);

    public MainPage getMainPage() {
        if(mainPage == null) {
            mainPage = new MainPage(driver);
        }
        return mainPage.ensurePageLoaded();
    }

    /**
     * Открывает стараницу "Платежи" по ссылке в верхнем меню
     */
    public void gotoPaymentPageByLinkClick(){
        logger.info("Открытие стараницы \"Платежи\" по ссылке в верхнем меню");
        getMainPage().paymentsLink.click();
    }

}
