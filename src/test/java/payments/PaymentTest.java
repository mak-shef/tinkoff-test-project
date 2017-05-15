package payments;

import helpers.*;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.PaymentsPage;
import pages.ZHKUPaymentPage;
import org.apache.log4j.*;
import ru.yandex.qatools.allure.annotations.Features;

/**
 * Created by Marina on 13.05.2017.
 */
@Features("Платежи")
public class PaymentTest {

    Logger logger = Logger.getLogger(PaymentTest.class);
    private AppManager appManager;
    private String communalName;
    private MainPageHelper mainPageHelper;
    private PaymentsHelper paymentsHelper;
    private CommunalPaymentsHelper communalPaymentsHelper;
    private ZHKUPaymentHelper zhkuPaymentHelper;


    @BeforeTest
    public void initDriver() throws Exception {
        //открываем браузер
        //переходим на стратовую страницу https://www.tinkoff.ru/
        appManager = new AppManager();
        //инициализация хелперов тестовых страниц
        mainPageHelper = new MainPageHelper(appManager.getWebDriver());
        paymentsHelper = new PaymentsHelper(appManager.getWebDriver());
        communalPaymentsHelper = new CommunalPaymentsHelper(appManager.getWebDriver());
        zhkuPaymentHelper = new ZHKUPaymentHelper(appManager.getWebDriver());
    }

    /**
     * Открытие страницы “ЖКУ-Москва“.
     */
    @BeforeClass
    public void prepareBeforeTest() {
        //переход на страницу “Платежи“ по клику на ссылку “Платежи“ в верхнем меню;
        mainPageHelper.gotoPaymentPageByLinkClick();
        //выбор пункта “Коммунальные платежи“ в списке категорий
        paymentsHelper.chooseCommunalPayments("Коммунальные платежи");
        //установка региона “г. Москва”

        communalPaymentsHelper.setRegionName("г. Москва", "Москве");
        Assert.assertEquals(communalPaymentsHelper.getRegionName(), "Москве", "Неверное наименование региона!");

        //клик по первому элементу из списка услуг, проверка наименования
        communalName = communalPaymentsHelper.clickCommunalItemsByIndex(1);
        Assert.assertEquals(communalName, "ЖКУ-Москва", "Неверное наименование поставщика услуг!");

        //переход на вкладку “Оплатить ЖКУ в Москве“.
        zhkuPaymentHelper.switchToPaymentPage();
    }

    @Test
    public void paymentsTest() throws Exception{
        //валидация полей
        logger.info("Валидация полей: сообщение о незаполненном поле");
        String expected = "Поле обязательное";
        ZHKUPaymentPage zhkuPaymentPage = zhkuPaymentHelper.getZhkuPaymentPage();
        zhkuPaymentPage.submitButton.click();

        logger.info("Поле 'Код плательщика'");
        WebElement code = zhkuPaymentPage.getPayerCode();
        Assert.assertEquals(zhkuPaymentPage.getErrorText(code),
                expected, "Неверный текст ошибки поля 'Код плательщика'");

        logger.info("Поле 'Период оплаты'");
        WebElement date = zhkuPaymentPage.getDatePeriod();
        Assert.assertEquals(zhkuPaymentPage.getErrorText(date),
                expected, "Неверный текст ошибки поля 'Период оплаты'");

        logger.info("Поле 'Сумма добровольного страхования жилья'");
        WebElement accountAmount = zhkuPaymentPage.getAccountAmount();
        Assert.assertEquals(zhkuPaymentPage.getErrorText(accountAmount),
                expected, "Неверный текст ошибки 'Сумма добровольного страхования жилья'");

        logger.info("Поле 'Сумма платежа'");
        WebElement ammount = zhkuPaymentPage.getAmount();
        Assert.assertEquals(zhkuPaymentPage.getErrorText(ammount),
                "", "Неверный текст ошибки 'Сумма платежа'");

        logger.info("Валидация поля код плательщика: неверное значение поля");
        zhkuPaymentHelper.getZhkuPaymentPage();
        zhkuPaymentPage.getPayerCode();
        code.sendKeys("не цифры*%");
        //перевод фокуса
        zhkuPaymentPage.zhkuPayLink.click();
        Assert.assertEquals(zhkuPaymentPage.getErrorText(code),
                "Поле неправильно заполнено", "Неверный текст ошибки");

        logger.info("Валидация поля код плательщика: больше 10 символов");
        zhkuPaymentHelper.getZhkuPaymentPage();
        zhkuPaymentPage.getPayerCode();
        code.sendKeys("12345678900");
        zhkuPaymentPage.zhkuPayLink.click();
        Assert.assertEquals(zhkuPaymentPage.getErrorText(code),
                "Поле неправильно заполнено", "Неверный текст ошибки");

        //контекстный поиск
        //выбор пункта “Коммунальные платежи“ в списке категорий
        paymentsHelper.gotoPaymentPageByLinkClick();
        //ввод поставщика услуг в строке быстрого поиска
        paymentsHelper.contextSearch(communalName);
        PaymentsPage paymentsPage = paymentsHelper.getPaymentsPage();
        Assert.assertTrue(paymentsPage.searchResultList.size() > 0,
                "Не найдено ни одной записи с наименованием  " + communalName);
        Assert.assertEquals(paymentsPage.searchResultList.get(0).getText(),
                communalName + "\nКоммунальные платежи", "Первая" +
                        "запись не верна");
        logger.info("Выбор первой записи с наименованием: "+communalName);
        paymentsPage.searchResultList.get(0).click();
        zhkuPaymentHelper.getZhkuMainPage().waitPageLoaded();

        //проверка зaгрузки страницы
        String currentUrl = appManager.getWebDriver().getCurrentUrl();
        Assert.assertTrue(currentUrl.endsWith(zhkuPaymentPage.PAGE_URL),
                "Загружен неверный URL: " + currentUrl);

        //изменение города
        //переход на страницу “Платежи“ по клику на ссылку “Платежи“ в верхнем меню;
        mainPageHelper.gotoPaymentPageByLinkClick();
        //выбор "Коммунальные платежи"
        paymentsHelper.chooseCommunalPayments("Коммунальные платежи");
        //установка региона "г. Санкт-Петербург"
        communalPaymentsHelper.setRegionName("г. Санкт-Петербург", "Санкт-Петербурге");
        Assert.assertEquals(communalPaymentsHelper.getRegionName(), "Санкт-Петербурге", "Неверное наименование региона!");
        //проверка,что поставщик не найден
        boolean isExists = communalPaymentsHelper.isElementExistsByName(communalName);
        Assert.assertFalse(isExists, "Найден неверный поставщик услуг " + communalName);
    }

    @AfterTest
    public void closeDriver() {
        appManager.closeDriver();

    }
}
