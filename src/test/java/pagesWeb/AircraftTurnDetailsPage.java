package pagesWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AircraftTurnDetailsPage  {

    private WebDriver driver;

    public AircraftTurnDetailsPage(WebDriver driver) {
        this.driver = driver;
    }

    private   By ELEMENT_ID = By.id("AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell");
    private   By SIMPLE_XPATH = By.xpath("//fnd-granite-button-wrapper/a/span[2]");
    private   By REQUEST_DEFERRAL_BUTTON_XPATH = By.xpath("//a[@title='Request Deferral']");
    private   By DETAILS_BUTTON = By.id("AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell-fndCommandDropdown-fndMenu-Details-fndButton-button");
    private   By FAULT_STATUS_BADGE = By.xpath("//granite-badge[@id='FaultDetails-FaultDetailsGroup-Objstate-badgeComponent-button']");

    public void captureAndClickraisedFault() {
        // Create WebDriverWait object with a 10-second timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the element is clickable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(ELEMENT_ID));

        // Click the element
        element.click();

        // Optional: Print a message indicating the element was clicked
        System.out.println("Element with ID 'AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell' clicked.");
    }
    public void clickDetailsButton() {
        // Create WebDriverWait object with a 10-second timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the element is clickable
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(DETAILS_BUTTON));

        // Click the element
        element.click();

        // Optional: Print a message indicating the element was clicked
        System.out.println("Element clicked.");
    }
    public void clickRequestDeferralButton() {
        // Create WebDriverWait object with a 10-second timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the element is clickable
        WebElement requestDeferralButton = wait.until(ExpectedConditions.elementToBeClickable(REQUEST_DEFERRAL_BUTTON_XPATH));

        // Click the "Request Deferral" button
        requestDeferralButton.click();

        // Optional: Print a message indicating the element was clicked
        System.out.println("Request Deferral button clicked.");
    }
    public String getFaultStatus() {
        // Wait for the granite-badge element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement statusBadge = wait.until(ExpectedConditions.visibilityOfElementLocated(FAULT_STATUS_BADGE));

        // Retrieve the text from the granite-badge element
        String statusText = statusBadge.getText().trim();

        // Return the captured status
        return statusText;
    }
}
