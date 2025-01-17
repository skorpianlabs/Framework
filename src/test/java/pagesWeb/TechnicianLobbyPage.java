package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static constant.CommonConstant.ENDED;
import static constant.CommonConstant.STARTED;

public class TechnicianLobbyPage extends BasePage {

    private WebDriver driver;

    public TechnicianLobbyPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(xpath = "//div[contains(@class, 'lobby-text-area') and text()='Raise Fault']")
    private WebElement raiseFaultButton;

    public void clickRaiseFault() {
        logger.info(STARTED + getCurrentMethodName());
        raiseFaultButton.click();
        logger.info(ENDED + getCurrentMethodName());
    }
}
