package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TechnicianLobbyPage extends BasePage {

    private WebDriver driver;

    public TechnicianLobbyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class, 'lobby-text-area') and text()='Raise Fault']") private WebElement raiseFaultButton;

    public void clickRaiseFault() {
        raiseFaultButton.click();
    }
}
