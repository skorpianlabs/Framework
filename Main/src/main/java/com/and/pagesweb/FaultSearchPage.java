package com.and.pagesweb;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

public class FaultSearchPage extends BasePage {

    private WebDriver driver;

    public FaultSearchPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "FlmFaultSearch-FaultSearchList-toggleFilterPane-fndListFilterButton-fndButton-button")
    private WebElement filterPanelButton;

    @FindBy(id = "FlmFaultSearch-FaultSearchList-filterPane-fndFieldFilter-Description")
    private WebElement faultDescriptionMenu;

    @FindBy(xpath = "//input[@type='search' and @role='textbox']")
    private WebElement faultDescriptionSearchInputField;

    @FindBy(xpath = "//label[contains(text(),'Fault Description')]/child::i")
    private WebElement falutDescriptionFilterButton;

    @FindBy(id = "FlmFaultSearch-FaultSearchList-filterPane-fndMoreFieldsButton-moreButton")
    private WebElement moreFieldsButton;

    @FindBy(id = "FlmFaultSearch-FaultSearchList-fndRow-0-fndCommandCell")
    private WebElement threeDotButton;

    @FindBy(id = "FlmFaultSearch-FaultSearchList-fndRow-0-fndCommandCell-fndCommandDropdown-fndMenu-fndButton-FaultSearchList_NavigateFaultDetail-button")
    private WebElement detailsButton;

    @FindBy(id = "FlmFaultSearch-FaultSearchList-toggleFilterPane-fndListFilterButton-fndButton-button")
    private WebElement pageFilterIconButton;

    private WebElement desField;

    public void clickFaultDescriptionMenuItem(String field) {
        logger.info(STARTED + getCurrentMethodName());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        boolean searchField = false;
        try {
            //check the given field is already visible in the panel
            if (field.equals("Fault Description")) {
             desField = wait.until(ExpectedConditions.elementToBeClickable(By.id("FlmFaultSearch-FaultSearchList-filterPane-fndFieldFilter-Description")));
                searchField = desField.isDisplayed();
            }
        } catch (TimeoutException e) {
            // If the checkbox is not visible within the timeout, fallback to other methods
            searchField = false;
        }
        if (searchField) {
            clickFaultDescriptionMenu();
        } else {
            //If given field is not visible select from more button
            clickMoreFieldsButton();
            enterFaultDescriptionSearchText(field);
            if (field.equals("Fault Description"))
            {
                clickFaultDescriptionMenu();
            }

        }
        logger.info(ENDED + getCurrentMethodName());
    }

    // Method to click on the fault description menu
    public void clickFaultDescriptionMenu() {
        logger.info(STARTED + getCurrentMethodName());
        desField.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    // Method to enter text into the fault description search input field
    public void enterFaultDescriptionSearchText(String searchText) {
        logger.info(STARTED + getCurrentMethodName());
        faultDescriptionSearchInputField.clear(); // Clear any existing text
        faultDescriptionSearchInputField.sendKeys(searchText);
        logger.info(ENDED + getCurrentMethodName());
    }

    // Method to click the fault description filter button
    public void clickFaultDescriptionFilterButton() {
        logger.info(STARTED + getCurrentMethodName());
        falutDescriptionFilterButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickMoreFieldsButton() {
        logger.info(STARTED + getCurrentMethodName());
        moreFieldsButton.click();
        System.out.println("User clicked the 'More Fields' button");
        logger.info(ENDED + getCurrentMethodName());
    }
    public void clickThreeDotButton() {
        logger.info(STARTED + getCurrentMethodName());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(threeDotButton));
        threeDotButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickDetailsButton() {
        logger.info(STARTED + getCurrentMethodName());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        wait.until(ExpectedConditions.elementToBeClickable(detailsButton));
        detailsButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }
    public void clickFilterIcon() {
        logger.info(STARTED + getCurrentMethodName());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement filterIconButton = wait.until(ExpectedConditions.elementToBeClickable(By.id("FlmFaultSearch-FaultSearchList-toggleFilterPane-fndListFilterButton-fndButton-button")));
        filterIconButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }

}

