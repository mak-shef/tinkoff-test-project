package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

/**
 * Модальное окно
 */
public class ModalPage extends Page {

    public ModalPage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(className = "ui-modals ui-modals_showed")
    public WebElement modalWindow;

}
