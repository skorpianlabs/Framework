package pagesWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class TechnicianLobbyPage {

    private WebDriver driver;

    public TechnicianLobbyPage(WebDriver driver) {
        this.driver = driver;
    }

    private By raiseFaultLobby = By.xpath("//div[contains(@class, 'lobby-text-area') and text()='Raise Fault']");

    public void clickRaiseFault() {
        // Wait for the "Raise Fault" element to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(raiseFaultLobby));

        // Click on the "Raise Fault" element
        element.click();
    }


}
