package ua.i.pl.sosnovskyi;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.testng.Assert;
import org.testng.Reporter;
import org.testng.annotations.*;
import ua.i.pl.sosnovskyi.pages.*;
import ua.i.pl.sosnovskyi.utils.EventHandler;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Ordering {

    WebDriver driver;
    EventFiringWebDriver eventDriver;
    Map<String, String> mobileEmulation = new HashMap<>();
    Map<String, Object> chromeOptions = new HashMap<String, Object>();

    @Parameters({"browser", "kind", "runType"})
    @BeforeTest
    public void setup(String browser, String kind, String runType) throws MalformedURLException {
        WebDriver baseDriver;

        switch (browser) {
            case "chrome": {
                String key = System.getProperty("webdriver.chrome.driver");
                String path = new File(Ordering.class.getResource("/chromedriver.exe").getFile()).getPath();
                if (key == null) {
                    System.setProperty("webdriver.chrome.driver", path);
                }

                switch (kind) {
                    case "desktop": {
                        baseDriver = new ChromeDriver();
                        break;
                    }
                    case "mobile": {
                        mobileEmulation.put("deviceName", "Nexus 5");
                        chromeOptions.put("mobileEmulation", mobileEmulation);
                        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
                        capabilities.setCapability(ChromeOptions.CAPABILITY, chromeOptions);
                        baseDriver = new ChromeDriver(capabilities);
                        break;
                    }
                    default: {
                        throw new RuntimeException("Wrong browser name");
                    }
                }
                break;

            }
            case "gecko": {
                String key = System.getProperty("webdriver.gecko.driver");
                String path = new File(Ordering.class.getResource("/geckodriver.exe").getFile()).getPath();
                if (key == null) {
                    System.setProperty("webdriver.gecko.driver", path);
                }
                baseDriver = new FirefoxDriver();
                break;
            }
            case "ie": {
                String key = System.getProperty("webdriver.ie.driver");
                String path = new File(Ordering.class.getResource("/IEDriverServer.exe").getFile()).getPath();
                if (key == null) {
                    System.setProperty("webdriver.ie.driver", path);

                }
                baseDriver = new InternetExplorerDriver();
                break;
            }
//            case"phantom":{
//                String key = System.getProperty("phantomjs.binary.path");
//                String path = new File(Ordering.class.getResource("/phantomjs.exe").getFile()).getPath();
//                if(key==null){
//                System.setProperty("phantomjs.binary.path", path);
//                }
//                baseDriver = new PhantomJSDriver();
//                break;
//            }
            default: {
                throw new RuntimeException("Wrong browser name");
            }
        }

        switch (runType) {
            case "grid": {
//                DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//                driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub"), capabilities);
                String kobitonServerUrl = "https://Mr.Alex:108e7261-0d73-4f78-914b-db790af693e2@api.kobiton.com/wd/hub";

                DesiredCapabilities capabilities = new DesiredCapabilities();
                capabilities.setCapability("sessionName", "Automation test session");
                capabilities.setCapability("sessionDescription", "");
                capabilities.setCapability("deviceOrientation", "portrait");
                capabilities.setCapability("captureScreenshots", true);
                capabilities.setCapability("browserName", "chrome");
                capabilities.setCapability("deviceGroup", "KOBITON");
                capabilities.setCapability("deviceName", "Galaxy A7(2017)");
                capabilities.setCapability("platformVersion", "6.0.1");
                capabilities.setCapability("platformName", "Android");
                driver=new RemoteWebDriver(new URL(kobitonServerUrl),capabilities);
                break;
            }
            case "single": {
                driver = baseDriver;
            }
        }
    }

    @BeforeClass
    public void configureDriver() {

//        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        eventDriver = new EventFiringWebDriver(this.driver);
        eventDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        eventDriver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
//        eventDriver.manage().window().maximize();
        eventDriver.register(new EventHandler());

    }

    @AfterTest
    public void quit() {
        eventDriver.quit();
    }

    @Test
    public void correctVersion() {
        IndexPage indexPage = new IndexPage(eventDriver);
        indexPage.openIndexPage();
        ua.i.pl.sosnovskyi.utils.View view = indexPage.getView();
        Reporter.log(view.toString());
//        SoftAssert softAssert = new SoftAssert();
//        softAssert.assertEquals(view, View.DESKTOP, "View is mobile");
//        softAssert.assertEquals(view, View.MOBILE, "View is desktop");
//        softAssert.assertAll();
    }

    @Test
    public void ordering() throws InterruptedException {
        IndexPage indexPage = new IndexPage(eventDriver);
        indexPage.openIndexPage();
        indexPage.allGoodsClick();
        MainPage mainPage = new MainPage(eventDriver);
        Assert.assertEquals(eventDriver.getTitle(), "Главная",
                "Page title is not Главная");
        mainPage.randomElementClick();
        CurrentProductPage currentProductPage = new CurrentProductPage(eventDriver);
        String currentProductUrl = eventDriver.getCurrentUrl();
        String productName = currentProductPage.getName();
        float productPrice = currentProductPage.getPrice();

        int quantityBefore = currentProductPage.getQuantity();
//        Thread.sleep(5000);
        currentProductPage.sddToCart();
        currentProductPage.goToCart();
        CartPage cartPage = new CartPage(eventDriver);
        Assert.assertEquals(cartPage.getName().equalsIgnoreCase(productName), true,
                "Name of the product isn't the same");
        Assert.assertEquals(cartPage.getQuantityOfProducts(), 1,
                "Products quantities is not equal to one");
        Assert.assertEquals(cartPage.getQuantity() == 1, true,
                "Quantity of product isn't equals to one");
        Assert.assertEquals(Float.compare(cartPage.getPrice(), productPrice), 0,
                "The price does not match to the declared");
        cartPage.checkOutClick();
        CheckOutPage checkOutPage = new CheckOutPage(eventDriver);
        checkOutPage.nameFill();
        checkOutPage.lastNameFill();
        checkOutPage.emailFill();
        checkOutPage.continueBtnClick();
        checkOutPage.mainAddressFill();
        checkOutPage.postcodeFill();
        checkOutPage.sityFill();
        checkOutPage.continueBtnClick();
        checkOutPage.continueBtnClick();
        checkOutPage.paymentClick();
        checkOutPage.licenseClick();
        checkOutPage.submitBtnClick();
        ConfirmPage confirmPage = new ConfirmPage(eventDriver);
        Assert.assertEquals(confirmPage.isConfirmMessage(), true, "Confirmation message isn't shown");
        System.err.println("productName " + productName);
        System.err.println("confirmPage.getProductName "+confirmPage.getProductName());
        String tmp=confirmPage.getProductName();
        Assert.assertEquals(tmp.toLowerCase().contains(productName.toLowerCase()), true,
                "Product name does not match to the product name which were displayed on the product page");

        Assert.assertEquals(Float.compare(confirmPage.getPrice(), productPrice), 0,
                "The price does not match to the price which were displayed on the product page");
        eventDriver.navigate().to(currentProductUrl);
        currentProductPage = new CurrentProductPage(eventDriver);
        int newQuantityy=currentProductPage.getQuantity();
        Assert.assertEquals( (quantityBefore-newQuantityy)==1, true, "Quantity error");
//        System.out.println("quantityBefore "+quantityBefore);
//        System.out.println("currentQuantity "+currentProductPage.getQuantity());
    }
}
