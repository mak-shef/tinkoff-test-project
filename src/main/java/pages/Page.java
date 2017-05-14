package pages;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Marina on 13.05.2017.
 */
public class Page {

    protected WebDriver webDriver;
    protected WebDriverWait waitPageLoad;
    protected WebDriverWait waitElementLoad;

    public Page(WebDriver webDriver) {
        this.webDriver = webDriver;
        waitPageLoad = new WebDriverWait(webDriver, 60);
        waitElementLoad = new WebDriverWait(webDriver, 30);
        PageFactory.initElements(webDriver, this);
    }

    public boolean waitPageLoaded() {
        try {
            ensurePageLoaded();
            return true;
        } catch (TimeoutException to) {
            return false;
        }
    }
    public Page ensurePageLoaded(){
        return this;
    }
}
