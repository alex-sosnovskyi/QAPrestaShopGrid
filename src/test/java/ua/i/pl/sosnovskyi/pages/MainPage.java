package ua.i.pl.sosnovskyi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainPage {
    private EventFiringWebDriver driver;
    private By productLinkSelector = By.cssSelector(".product-title>a");
    private List<String> productNames = new ArrayList<>();
    private List<WebElement> links;
    private WebElement newElement;

    public MainPage(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    private List<WebElement> getProducts() {

        links = driver.findElements(productLinkSelector);

        return new ArrayList<>(links);
    }

    private WebElement randomElement(List<WebElement> list) {
        int length = list.size();
        Random random = new Random(System.currentTimeMillis());
        int current = random.nextInt(length);
        return list.get(current);
    }
    public void randomElementClick(){
        List<WebElement> products = getProducts();
        WebElement element = randomElement(products);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
//        element.click();
    }
}
