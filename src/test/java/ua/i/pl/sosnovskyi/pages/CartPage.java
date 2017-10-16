package ua.i.pl.sosnovskyi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CartPage {
    private EventFiringWebDriver driver;
    private By nameSelector = By.cssSelector("div.product-line-info>a");
    private By quantitySelector = By.cssSelector("#cart-subtotal-products>span.js-subtotal");
    private By priceSelector = By.cssSelector("#cart-subtotal-products>span.value");
    private By quantityProductsSelector = By.cssSelector("li.cart-item");
    private By checkOutSelector = By.cssSelector("div.checkout a");

    public CartPage(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public String getName() {
        WebElement element = driver.findElement(nameSelector);
        return element.getText();
    }

    public int getQuantity() {
        WebElement element = driver.findElement(quantitySelector);
        int result = 0;
        Pattern pattern = Pattern.compile("[\\d]{1,}");
        Matcher matcher = pattern.matcher(element.getText());
        while (matcher.find()) {
            result = Integer.parseInt(matcher.group());
        }
        return result;
    }

    public int getQuantityOfProducts() {
        List<WebElement> elements = driver.findElements(quantityProductsSelector);
        return elements.size();
    }

    public float getPrice() {
        WebElement element = driver.findElement(priceSelector);
        String text = element.getText();
        String resultTmp = "";
        Pattern pattern = Pattern.compile("[\\d]{1,},[\\d]{2}");
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            resultTmp = matcher.group();
        }
        String replace = resultTmp.replace(",", ".");
        return Float.parseFloat(replace);
    }

    public void checkOutClick() {
        WebElement element = driver.findElement(checkOutSelector);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }
}
