package com.and.pagesweb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

import java.time.Duration;

public class RaiseFault extends BasePage {

    private WebDriver driver;

    public RaiseFault(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//label[@title='System']/following::button//i")
    private WebElement systemButton;

    @FindBy(xpath = "//label[@title='Aircraft']/following::button//i")
    private WebElement aircraftButton;

    @FindBy(xpath = "//button[@title='Raise Fault']")
    private WebElement raiseFaultButton;

    @FindBy(xpath = "//label[@title='Fault Source']/following::button//i")
    private WebElement faultSourceButton;

    @FindBy(xpath = "//label[@title='Fault Severity']/following::button//i")
    private WebElement faultSeverityButton;

    @FindBy(xpath = "//label[@title='Description']/following::textarea")
    private WebElement descriptionFieldInputField;

    @FindBy(xpath = "//*[@id='RaiseFaultAssistant-fndToolbar-action-fndButton-RaiseFaultAssistant_RaiseFault-button']")
    private WebElement okButton;

    @FindBy(xpath = "//label[@title='Aircraft']/following::input")
    private WebElement aircraftInputField;

    @FindBy(xpath = "//label[@title='System']/following::input")
    private WebElement systemInputField;

    @FindBy(xpath = "//label[@title='Fault Source']/following::input")
    private WebElement faultSourceInputField;

    @FindBy(xpath = "//label[@title='Logbook Type']/following::input")
    private WebElement logbookTypeInputField;

    @FindBy(xpath = "//label[@title='Logbook Reference']/following::input")
    private WebElement logbookReferenceInputField;

    @FindBy(xpath = "//label[@title='Found During Flight']/following::input")
    private WebElement foundDuringFlightInputField;

    @FindBy(xpath = "//label[@title='Phase of Flight']/following::input")
    private WebElement phaseOfFlightInputField;

    @FindBy(xpath = "//label[@title='Fault Type']/following::input")
    private WebElement faultTypeInputField;

    @FindBy(xpath = "//label[@title='Fault Severity']/following::input")
    private WebElement faultSeverityInputField;

    @FindBy(xpath = "//label[@title='Fault Code']/following::input")
    private WebElement faultCodeInputField;


    public void clickButtonRelativeToLabelSystem() {
        logger.info(STARTED + getCurrentMethodName());
        systemButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickButtonRaiseFault() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(raiseFaultButton));
            Thread.sleep(2000);
            raiseFaultButton.click();
        } catch (Exception e) {
            logger.error("An error occurred while clicking the 'Raise Fault' button: " + e.getMessage());
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickButtonRelativeToFaultSource() {
        logger.info(STARTED + getCurrentMethodName());
        faultSourceButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickButtonRelativeToFaultSeverity() {
        logger.info(STARTED + getCurrentMethodName());
        faultSeverityButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterDescription(String descriptionText) {
        logger.info(STARTED + getCurrentMethodName());
        descriptionFieldInputField.clear();
        descriptionFieldInputField.sendKeys(descriptionText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickButtonOK() throws InterruptedException {
        logger.info(STARTED + getCurrentMethodName());
        okButton.click();
        Thread.sleep(3000);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickAircraftInput() {
        logger.info(STARTED + getCurrentMethodName());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(aircraftInputField));
        aircraftButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }
    public void enterAircraft(String aircraftText) {
        logger.info(STARTED + getCurrentMethodName());
        aircraftInputField.clear();
        aircraftInputField.sendKeys(aircraftText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterSystem(String systemText) {
        logger.info(STARTED + getCurrentMethodName());
        systemInputField.clear();
        systemInputField.sendKeys(systemText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterFaultSource(String faultSourceText) {
        logger.info(STARTED + getCurrentMethodName());
        faultSourceInputField.clear();
        faultSourceInputField.sendKeys(faultSourceText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterLogbookType(String logbookTypeText) {
        logger.info(STARTED + getCurrentMethodName());
        logbookTypeInputField.clear();
        logbookTypeInputField.sendKeys(logbookTypeText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterLogbookReference(String logbookReferenceText) {
        logger.info(STARTED + getCurrentMethodName());
        logbookReferenceInputField.clear();
        logbookReferenceInputField.sendKeys(logbookReferenceText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterFoundDuringFlight(String foundDuringFlightText) {
        logger.info(STARTED + getCurrentMethodName());
        foundDuringFlightInputField.clear();
        foundDuringFlightInputField.sendKeys(foundDuringFlightText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterPhaseOfFlight(String phaseOfFlightText) {
        logger.info(STARTED + getCurrentMethodName());
        phaseOfFlightInputField.clear();
        phaseOfFlightInputField.sendKeys(phaseOfFlightText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterFaultType(String faultTypeText) {
        logger.info(STARTED + getCurrentMethodName());
        faultTypeInputField.clear();
        faultTypeInputField.sendKeys(faultTypeText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterFaultSeverity(String faultSeverityText) {
        logger.info(STARTED + getCurrentMethodName());
        faultSeverityInputField.clear();
        faultSeverityInputField.sendKeys(faultSeverityText);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterFaultCode(String faultCodeText) {
        logger.info(STARTED + getCurrentMethodName());
        faultCodeInputField.clear();
        faultCodeInputField.sendKeys(faultCodeText);
        logger.info(ENDED + getCurrentMethodName());
    }


}
