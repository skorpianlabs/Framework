package com.and.and.pagesweb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.and.and.constant.CommonConstant.ENDED;
import static com.and.and.constant.CommonConstant.STARTED;

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
        logger.info(STARTED + getCurrentMethodName());
        aurenaLink.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void giveUserName(String userName) {
        logger.info(STARTED + getCurrentMethodName());
        userNameField.sendKeys(userName);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void givePassword(String password) {
        logger.info(STARTED + getCurrentMethodName());
        passwordField.sendKeys(password);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickLogin() {
        logger.info(STARTED + getCurrentMethodName());
        loginButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }
}
