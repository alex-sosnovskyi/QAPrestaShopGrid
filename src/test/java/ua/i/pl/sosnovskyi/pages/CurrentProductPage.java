package ua.i.pl.sosnovskyi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CurrentProductPage {
    private EventFiringWebDriver driver;
    private By priceSelector = By.cssSelector(".current-price>span");
    private By quantitySelector = By.cssSelector("#product-details div.product-quantities>span");
    private By nameSelector = By.className("h1");
    private final String name;
    private By toCartSelector = By.className("add-to-cart");
    private By goToCartSelector = By.cssSelector(".cart-content>a");
    private By detailsSelector = By.cssSelector("ul.nav-tabs li:last-child>a");

    public CurrentProductPage(EventFiringWebDriver driver) {
        this.driver = driver;
        name = driver.findElement(nameSelector).getText();

    }

    public String getName() {
        return name;
    }

    public float getPrice() {
        WebElement element = driver.findElement(priceSelector);
        float result = Float.valueOf(element.getAttribute("content"));
        return result;
    }

    public int getQuantity() throws InterruptedException {
//        detailsClick();
//        WebDriverWait wait=new WebDriverWait(driver, 5);
//        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("product-details")),
//                "aria-expanded", "true"));
        WebElement element = driver.findElement(quantitySelector);
        int result = 0;
        String resultStr = element.getText();
//            System.out.println("resultStr "+resultStr);
           if(resultStr.equals("")){
                detailsClick();
                WebDriverWait wait=new WebDriverWait(driver, 5);
                wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("product-details")),
                        "aria-expanded", "true"));
                element = driver.findElement(quantitySelector);
                resultStr = element.getText();
//                System.out.println("resultStr "+resultStr);
            }
            String regExp = "[\\d]{1,}";
            Pattern pattern = Pattern.compile(regExp);
            Matcher matcher = pattern.matcher(resultStr);
            while (matcher.find()) {
                result = Integer.parseInt(matcher.group());
            }
//            System.out.println("result "+result);
        return result;
    }

    public void sddToCart() {
        WebElement element = driver.findElement(toCartSelector); //.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }

    public void goToCart() {
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.attributeContains(driver.findElement(By.id("blockcart-modal")),
                "display", "block"));
        WebElement element = driver.findElement(goToCartSelector); //.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }
    public void detailsClick(){
        WebElement element = driver.findElement(detailsSelector);
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }
}
