package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static constant.CommonConstant.ENDED;
import static constant.CommonConstant.STARTED;

import java.time.Duration;

public class RaiseFault extends BasePage {

    private WebDriver driver;

    public RaiseFault(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//label[@title='System']/following::button//i")
    private WebElement systemButton;

    @FindBy(xpath = "//span[text()='INTRODUCTION']")
    private WebElement systemValue;

    @FindBy(xpath = "//label[@title='Aircraft']/following::button//i")
    private WebElement aircraftInputField;

    @FindBy(xpath = "//span[contains(@class, 'value wrap-content') and text()='IFSAD-2000 (737-NG)']")
    private WebElement ifsadTextSpan;

    @FindBy(xpath = "//button[@title='Raise Fault']")
    private WebElement raiseFaultButton;

    @FindBy(xpath = "//label[@title='Fault Source']/following::button//i")
    private WebElement faultSourceButton;

    @FindBy(xpath = "//span[text()='AUTH']")
    private WebElement faultSourceValue;

    @FindBy(xpath = "//label[@title='Fault Severity']/following::button//i")
    private WebElement faultSeverityButton;

    @FindBy(xpath = "//span[text()='MEL']")
    private WebElement faultSeverityValue;

    @FindBy(xpath = "//label[@title='Description']/following::textarea")
    private WebElement descriptionField;

    @FindBy(xpath = "//*[@id='RaiseFaultAssistant-fndToolbar-action-fndButton-RaiseFaultAssistant_RaiseFault-button']")
    private WebElement okButton;

    public void clickButtonRelativeToLabelSystem() {
        logger.info(STARTED + getCurrentMethodName());
        systemButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void captureSelectedValueSystem() {
        logger.info(STARTED + getCurrentMethodName());
        systemValue.click();
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

    public void captureSelectedValueFaultSource() {
        logger.info(STARTED + getCurrentMethodName());
        faultSourceValue.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickButtonRelativeToFaultSeverity() {
        logger.info(STARTED + getCurrentMethodName());
        faultSeverityButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void captureSelectedValueFaultSeverity() {
        logger.info(STARTED + getCurrentMethodName());
        faultSeverityValue.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterDescription(String descriptionText) {
        logger.info(STARTED + getCurrentMethodName());
        descriptionField.clear();
        descriptionField.sendKeys(descriptionText);
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
        aircraftInputField.click();
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickIFSADText() {
        logger.info(STARTED + getCurrentMethodName());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(ifsadTextSpan));
        ifsadTextSpan.click();
        logger.info(ENDED + getCurrentMethodName());
    }
}
