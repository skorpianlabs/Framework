package pagesWeb;

import static org.junit.jupiter.api.Assertions.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage  {

    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }
    public  String validateText_xpath = "//span[@class='header-title' and text()='Home']";

    private  By navigationLink = By.id("searchInputInsideNavigationMenu");

    public  String navigationToTurns_xpath = "//span[@class='subtitle ng-star-inserted' and text()='Aviation Maintenance / Mobile Maintenance']";

    private By navigationToLobby = By.xpath("//span[contains(@class, 'subtitle') and text()='Aviation Maintenance']");



    public  void ValidateText(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
       WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(validateText_xpath)));
       String TextValue = element.getText();

       assertEquals("Home", TextValue);
    }
    public  void clickNavigationLink() throws InterruptedException {
        // Wait for the element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement buttonElement = wait.until(ExpectedConditions.visibilityOfElementLocated(navigationLink));
        System.out.println("clicked navigation link");
        // Perform the click action
        buttonElement.click();
        Thread.sleep(2000);
    }

    // Method to send keys to the input element
    public  void navigationSearchKey(String searchKey) {
        // Wait for the input element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement inputElement = wait.until(ExpectedConditions.visibilityOfElementLocated(navigationLink));

        // Clear any existing text and send the search key
        inputElement.clear();  // Clears any pre-existing text in the input field (optional)
        inputElement.sendKeys(searchKey);  // Sends the search key to the input field
    }

    // Method to click the "Aviation Maintenance / Mobile Maintenance" element
    public  void clickAviationMaintenanceMenu() throws InterruptedException {
        // Wait for the element to be visible and clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(navigationToTurns_xpath)));

        // Perform the click action
        element.click();
        System.out.println("clicked Mobile maintenance");
        Thread.sleep(5000);
    }
    public void clickAviationMaintenanceTitleForRF() {
        // Wait for the "Aviation Maintenance" element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(navigationToLobby));

        // Click on the "Aviation Maintenance" element
        element.click();
    }
}



