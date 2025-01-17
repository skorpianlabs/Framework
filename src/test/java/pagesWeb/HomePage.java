package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

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
        String textValue = validateTextElement.getText();
        assertEquals("Home", textValue);
    }

    public void clickNavigationLink() throws InterruptedException {
        navigationLink.click();
        System.out.println("Clicked navigation link");
        Thread.sleep(2000);
    }

    public void navigationSearchKey(String searchKey) {
        navigationLink.clear();
        navigationLink.sendKeys(searchKey);
    }

    public void clickAviationMaintenanceMenu() throws InterruptedException {
        navigationToTurns.click();
        System.out.println("Clicked Mobile Maintenance");
        Thread.sleep(5000);
    }

    public void clickAviationMaintenanceTitleForRF() {
        navigationToLobby.click();
    }
}
