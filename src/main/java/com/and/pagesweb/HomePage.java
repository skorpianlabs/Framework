package com.and.and.pagesweb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static com.and.and.constant.CommonConstant.ENDED;
import static com.and.and.constant.CommonConstant.STARTED;

import static org.junit.jupiter.api.Assertions.*;

public class HomePage extends BasePage {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//span[@class='header-title' and text()='Home']")
    private WebElement validateTextElement;

    @FindBy(id = "searchInputInsideNavigationMenu")
    private WebElement navigationLink;

    @FindBy(xpath = "//span[@class='subtitle ng-star-inserted' and text()='Aviation Maintenance / Mobile Maintenance']")
    private WebElement navigationToTurns;

    @FindBy(xpath = "//span[contains(@class, 'subtitle') and text()='Aviation Maintenance']")
    private WebElement navigationToLobby;

    public void validateText() {
        logger.info(STARTED + getCurrentMethodName());
        String textValue = validateTextElement.getText();
        assertEquals("Home", textValue);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickNavigationLink() throws InterruptedException {
        logger.info(STARTED + getCurrentMethodName());
        navigationLink.click();
        System.out.println("Clicked navigation link");
        Thread.sleep(2000);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void navigationSearchKey(String searchKey) {
        logger.info(STARTED + getCurrentMethodName());
        navigationLink.clear();
        navigationLink.sendKeys(searchKey);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickAviationMaintenanceMenu() throws InterruptedException {
        logger.info(STARTED + getCurrentMethodName());
        navigationToTurns.click();
        System.out.println("Clicked Mobile Maintenance");
        Thread.sleep(5000);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickAviationMaintenanceTitleForRF() {
        logger.info(STARTED + getCurrentMethodName());
        navigationToLobby.click();
        logger.info(ENDED + getCurrentMethodName());
    }
}
