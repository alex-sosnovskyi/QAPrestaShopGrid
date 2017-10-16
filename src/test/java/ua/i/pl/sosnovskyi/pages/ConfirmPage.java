package ua.i.pl.sosnovskyi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConfirmPage {
    private EventFiringWebDriver driver;
    private By messageSelector = By.cssSelector("h3.card-title");
    private By nameSelector = By.cssSelector("div.details");
    private By priceSelector = By.xpath("//div[@class=\"order-line row\"]//div[@class=\"col-xs-5 text-xs-right bold\"]");
    //    div.order-line div:last-child div.row div:first-child

    public ConfirmPage(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public boolean isConfirmMessage() {
        if (driver.findElement(messageSelector).getText() != null) {
            return true;
        }
        return false;
    }

    public String getProductName() {
        return driver.findElement(nameSelector).getText();
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
}
