package helpers;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import ru.yandex.qatools.allure.annotations.Step;
import utils.PropertyManager;
import webdriver.WebDriverFactory;


/**
 * Основной хелпер для управления приложением:
 * - создает инстанс webDriver
 * - переходит на базовый url
 */
public class AppManager {

    WebDriver webDriver;
    Logger logger = Logger.getLogger(AppManager.class);
    private final String BASE_URL = PropertyManager.getInstance().getProperty("base.url");
    private final String BROWSER = PropertyManager.getInstance().getProperty("browser.name");
    private final String DRIVER_PATH = PropertyManager.getInstance().getProperty("webdriver.path");
    private final String REMOTE_URL = PropertyManager.getInstance().getProperty("grid2.hub");

    public AppManager() throws Exception {
        webDriver = WebDriverFactory.createWebDriver(BROWSER, DRIVER_PATH, REMOTE_URL);
        webDriver.manage().window().maximize();
        goToBaseUrl();
    }

    /**
     * Переход на базовый URL, указанный в проперти файле
     */
    @Step("Переход на базовый URL")
    public void goToBaseUrl() {
        logger.info("Переход на страницу: " + BASE_URL);
        webDriver.navigate().to(BASE_URL);
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }

    public void closeDriver() {
        webDriver.close();
    }
}
