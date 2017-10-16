package ua.i.pl.sosnovskyi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ua.i.pl.sosnovskyi.utils.View;

public class IndexPage {
    private static final String DEFAULT_BASE_URL = "http://prestashop-automation.qatestlab.com.ua/";
    private EventFiringWebDriver driver;
    private By allGoodsSelector = By.className("all-product-link");
    private By viewSelector = By.className("mobile");

    public IndexPage(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public void allGoodsClick() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.elementToBeClickable(allGoodsSelector));
        WebElement element = driver.findElement(allGoodsSelector);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }

    public void openIndexPage() {
        driver.navigate().to(DEFAULT_BASE_URL);
    }

    public View getView() {
        WebElement element = driver.findElement(viewSelector);
        String display = element.getCssValue("display");
//        System.out.println(display);
        if(display.equals("none")){
            return View.DESKTOP;
        }
        return View.MOBILE;
    }
}
