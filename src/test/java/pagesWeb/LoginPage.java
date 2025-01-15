package pagesWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;


public class LoginPage {

   private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }


    private static By aurenalink = By.id("aurenalink");

    public static  String userName_xpath = "//*[@id=\"username\"]";

    public static  String password_xpath = "//*[@id=\"password\"]";

    public static String loginBtn_xpath = "//*[@id=\"id-ifs-login-btn\"]";

    public  void clickOnAurenaLink() {
        WebElement linkElement = driver.findElement(aurenalink);
        linkElement.click();
    }

    public  void give_userName(String UserName) throws InterruptedException {

        driver.findElement(By.xpath(userName_xpath)).sendKeys(UserName);

    }
    public  void give_password(String password) throws InterruptedException {


        driver.findElement(By.xpath(password_xpath)).sendKeys(password);

    }
    public  void clickLogin() throws InterruptedException {


        driver.findElement(By.xpath(loginBtn_xpath)).click();


    }
}
