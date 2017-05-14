package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindAll;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.List;

/**
 * Created by Marina on 13.05.2017.
 */
public class RegionSearchPage extends ModalPage {

    public static final String UI_REGIONS_ITEM = "ui-regions__item";

    public RegionSearchPage(WebDriver webDriver) {
        super(webDriver);
    }

    @FindBy(xpath = ".//div[contains(@class,'ui-regions__input')]//input")
    public WebElement regionInput;

    @FindBy(xpath = ".//div[contains(@class,'ui-input__search-icon')]")
    public WebElement searchIcon;

    @FindBys(@FindBy(className = UI_REGIONS_ITEM))
    public List<WebElement> regionsList;


    public void findAndClickRegion(String regionName){
        regionInput.sendKeys(regionName);
        Assert.assertEquals(regionsList.size(), 1, "Found more then one item");
        Assert.assertEquals(regionsList.get(0).getText(), regionName, "Found name isn't correct");
        regionsList.get(0).findElement(By.xpath("./span")).click();
    }

    @Override
    public RegionSearchPage ensurePageLoaded(){
        waitElementLoad.until(ExpectedConditions.presenceOfElementLocated(By.className("ui-regions")));
        return this;
    }


}
