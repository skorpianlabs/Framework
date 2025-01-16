package pagesWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RaiseFault  {

    private WebDriver driver;
    private  By SystemButton = By.xpath("By.xpath(//label[text()='System']/parent::*//button");
    private  By Systemvalue = By.xpath("//span[text()='INTRODUCTION']");
    private By aircraftInputField = By.xpath("//label[@title='Aircraft']/following::button//i");
    private By ifsadTextSpan = By.xpath("//span[contains(@class, 'value wrap-content') and text()='IFSAD-2000 (737-NG)']");

    public RaiseFault(WebDriver driver) {
        this.driver = driver;
    }
    public  void clickButtonRelativeToLabelSystem() {
        try {
            // Set up WebDriverWait with a 10-second timeout (adjust the duration as needed)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Locate the button element relative to the label with text "System"
            WebElement buttonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//label[@title='System']/following::button//i")
            ));

            // Once the button is visible, click it
            buttonElement.click();
            System.out.println("Button clicked successfully!");
        } catch (Exception e) {
            System.out.println("Error while clicking the button: " + e.getMessage());
        }
    }
    public  void captureSelectedValueSystem() {
        try {
            // Define the XPath to locate the span element with text 'INTRODUCTION'
            By systemValue = By.xpath("//span[text()='INTRODUCTION']");

            // Wait for the element to be visible before trying to find it (use WebDriverWait)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement systemValueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(systemValue));

            // Capture the text of the element
            systemValueElement.click();

        } catch (Exception e) {
            System.out.println("Error while capturing dropdown value: " + e.getMessage());
        }
    }

    public  void clickButtonRaiseFault() {
        try {
            // Set up WebDriverWait with a 10-second timeout (adjust the duration as needed)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Hard-code the title value 'Raise Fault' in the XPath
            By buttonLocator = By.xpath("//button[@title='Raise Fault']");

            // Wait for the button to be visible and clickable
            WebElement buttonElement = wait.until(ExpectedConditions.elementToBeClickable(buttonLocator));

            // Click the button
            buttonElement.click();
            System.out.println("Button with title 'Raise Fault' clicked successfully!");
        } catch (Exception e) {
            System.out.println("Error while clicking the button: " + e.getMessage());
        }
    }
    public  void clickButtonRelativeToFaultSource() {
        try {
            // Set up WebDriverWait with a 10-second timeout (adjust the duration as needed)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Locate the button element relative to the label with text "System"
            WebElement buttonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//label[@title='Fault Source']/following::button//i")
            ));

            // Once the button is visible, click it
            buttonElement.click();
            System.out.println("Button clicked successfully!");
        } catch (Exception e) {
            System.out.println("Error while clicking the button: " + e.getMessage());
        }
    }
    public  void captureSelectedValueFaultSource() {
        try {
            // Define the XPath to locate the span element with text 'INTRODUCTION'
            By systemValue = By.xpath("//span[text()='AUTH']");

            // Wait for the element to be visible before trying to find it (use WebDriverWait)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement systemValueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(systemValue));

            // Capture the text of the element
            systemValueElement.click();

        } catch (Exception e) {
            System.out.println("Error while capturing dropdown value: " + e.getMessage());
        }
    }
    public  void clickButtonRelativeToFaultSeverity() {
        try {
            // Set up WebDriverWait with a 10-second timeout (adjust the duration as needed)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

            // Locate the button element relative to the label with text "System"
            WebElement buttonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.xpath("//label[@title='Fault Severity']/following::button//i")
            ));

            // Once the button is visible, click it
            buttonElement.click();
            System.out.println("Button clicked successfully!");
        } catch (Exception e) {
            System.out.println("Error while clicking the button: " + e.getMessage());
        }
    }
    public  void captureSelectedValueFaultSeverity() {
        try {
            // Define the XPath to locate the span element with text 'INTRODUCTION'
            By systemValue = By.xpath("//span[text()='MEL']");

            // Wait for the element to be visible before trying to find it (use WebDriverWait)
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            WebElement systemValueElement = wait.until(ExpectedConditions.visibilityOfElementLocated(systemValue));

            // Capture the text of the element
            systemValueElement.click();

        } catch (Exception e) {
            System.out.println("Error while capturing dropdown value: " + e.getMessage());
        }
    }
    public  void enterDescription(String descriptionText) {
        // Locate the textarea element relative to the label with title 'Description'
        WebElement descriptionField = driver.findElement(By.xpath("//label[@title='Description']/following::textarea"));

        // Clear any pre-existing text in the textarea and send the new value
        descriptionField.clear();
        descriptionField.sendKeys(descriptionText);
    }
    public  void clickButtonOK() throws InterruptedException {
        // Locate the button element using the provided XPath
        WebElement button = driver.findElement(By.xpath("//*[@id='RaiseFaultAssistant-fndToolbar-action-fndButton-RaiseFaultAssistant_RaiseFault-button']"));

        // Click the button
        button.click();
        Thread.sleep(3000);
    }
    public void clickAircraftInput() {
        // Wait for the "Aircraft" input field to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(aircraftInputField));

        // Click on the "Aircraft" input field
        element.click();
    }
    public void clickIFSADText() {
        // Wait for the "IFSAD-2000 (737-NG)" span element to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(ifsadTextSpan));

        // Click on the "IFSAD-2000 (737-NG)" span element
        element.click();
    }
}
