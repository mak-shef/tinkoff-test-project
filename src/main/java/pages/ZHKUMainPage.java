package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

/**
 * Страница "ЖКУ-Москва"
 */
public class ZHKUMainPage extends MainPage {

    public ZHKUMainPage(WebDriver webDriver) {
        super(webDriver);
    }

    public final String PAGE_URL = "zhku-moskva/";

    @FindBy(xpath = ".//a[@href='/zhku-moskva/oplata/' and @title='Оплатить ЖКУ в Москве']")
    public WebElement zhkuPayLink;

    @Override
    public ZHKUMainPage ensurePageLoaded() {
        waitPageLoad.until(ExpectedConditions.visibilityOf(zhkuPayLink));
        return this;
    }
}
