package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

/**
 * Главная странца
 */
public class MainPage extends Page {

    public static final String LOGO = ".//a[@href='/']//span[text()='Тинькофф']";
    @FindBy(xpath = ".//a[@href = '/payments/']")
    public WebElement paymentsLink;

    public MainPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public MainPage ensurePageLoaded(){
        waitPageLoad.until(ExpectedConditions.presenceOfElementLocated(By.xpath(LOGO)));
        return this;
    }

}
