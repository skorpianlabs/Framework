package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
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
        systemButton.click();
        System.out.println("Button clicked successfully!");
    }

    public void captureSelectedValueSystem() {
        systemValue.click();
    }

    public void clickButtonRaiseFault() {
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.elementToBeClickable(raiseFaultButton));
            Thread.sleep(2000);
            raiseFaultButton.click();
        } catch (Exception e) {
            System.out.println("An error occurred while clicking the 'Raise Fault' button: " + e.getMessage());
        }
    }

    public void clickButtonRelativeToFaultSource() {
        faultSourceButton.click();
        System.out.println("Button clicked successfully!");
    }

    public void captureSelectedValueFaultSource() {
        faultSourceValue.click();
    }

    public void clickButtonRelativeToFaultSeverity() {
        faultSeverityButton.click();
        System.out.println("Button clicked successfully!");
    }

    public void captureSelectedValueFaultSeverity() {
        faultSeverityValue.click();
    }

    public void enterDescription(String descriptionText) {
        descriptionField.clear();
        descriptionField.sendKeys(descriptionText);
    }

    public void clickButtonOK() throws InterruptedException {
        okButton.click();
        Thread.sleep(3000);
    }

    public void clickAircraftInput() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(aircraftInputField));
        aircraftInputField.click();
    }

    public void clickIFSADText() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(ifsadTextSpan));
        ifsadTextSpan.click();
    }
}
