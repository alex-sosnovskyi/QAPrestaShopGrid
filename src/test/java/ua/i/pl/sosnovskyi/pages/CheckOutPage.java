package ua.i.pl.sosnovskyi.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import ua.i.pl.sosnovskyi.utils.DataGenerator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CheckOutPage {
    private EventFiringWebDriver driver;
    private By nameInputSelector = By.name("firstname");
    private By lastNameInputSelector = By.name("lastname");
    private By emailInputSelector = By.name("email");
    private static int step = 0;
    private By continueBtnGuestSelector = By.cssSelector("#checkout-guest-form button.continue");
    private By continueBtnAdressSelector = By.cssSelector("#delivery-address button.continue");
    private By continueBtnDeliverySelector = By.cssSelector("#checkout-delivery-step button.continue");
    private List<By> selectors = new ArrayList<By>(Arrays.asList(continueBtnGuestSelector,
            continueBtnAdressSelector,
            continueBtnDeliverySelector));
    private By mainAdressSelector = By.name("address1");
    private By postcodeSelector = By.name("postcode");
    private By citySelector = By.name("city");
    private By paymentCheckSelector = By.id("payment-option-1");
    private By paymentTransferSelector = By.id("payment-option-2");
    private By licenseSelector = By.id("conditions_to_approve[terms-and-conditions]");
    private By submitBtnSelector = By.cssSelector("div.ps-shown-by-js>button");


    public CheckOutPage(EventFiringWebDriver driver) {
        this.driver = driver;
    }

    public void nameFill() {
        driver.findElement(nameInputSelector).sendKeys(DataGenerator.getWord());
    }

    public void lastNameFill() {
        driver.findElement(lastNameInputSelector).sendKeys(DataGenerator.getWord());
    }

    public void emailFill() {
        driver.findElement(emailInputSelector).sendKeys(DataGenerator.getEmail());
    }

    public void mainAddressFill() {
        driver.findElement(mainAdressSelector).sendKeys(DataGenerator.getWord());
    }

    public void postcodeFill() {
        driver.findElement(postcodeSelector).sendKeys(DataGenerator.getCode());
    }

    public void sityFill() {
        driver.findElement(citySelector).sendKeys(DataGenerator.getWord());
    }

    public void paymentClick() {
        WebElement element = driver.findElement(paymentCheckSelector);//.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }

    public void licenseClick() {
        WebElement element = driver.findElement(licenseSelector);//.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }

    public void submitBtnClick() {
        WebElement element = driver.findElement(submitBtnSelector);//.click();
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }

    public void continueBtnClick() {
        WebElement element = driver.findElement(selectors.get(step));
        step = step + 1;
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].click()", element);
    }


}
