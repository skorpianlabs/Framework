package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage extends BasePage {

    private WebDriver driver;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "aurenalink")
    private WebElement aurenaLink;

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement userNameField;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"id-ifs-login-btn\"]")
    private WebElement loginButton;

    public void clickOnAurenaLink() {
        aurenaLink.click();
    }

    public void giveUserName(String userName) {
        userNameField.sendKeys(userName);
    }

    public void givePassword(String password) {
        passwordField.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }
}
