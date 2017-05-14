package helpers;

import org.openqa.selenium.WebDriver;

public abstract class BaseHelper {

    protected WebDriver driver;

    public BaseHelper(WebDriver driver) {
        this.driver = driver;
    }
}
