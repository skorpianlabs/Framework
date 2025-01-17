package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static constant.CommonConstant.ENDED;
import static constant.CommonConstant.STARTED;
import java.util.HashMap;
import java.util.Map;

public class AircraftTurnDetailsPage extends BasePage {

    private WebDriver driver;

    public AircraftTurnDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell")
    private WebElement RaiseFaultButton;

    @FindBy(xpath = "//fnd-granite-button-wrapper/a/span[2]")
    private WebElement simpleXpath;

    @FindBy(xpath = "//a[@title='Request Deferral']")
    private WebElement requestDeferralButton;

    @FindBy(id = "AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell-fndCommandDropdown-fndMenu-Details-fndButton-button")
    private WebElement DetailsButton;

    @FindBy(xpath = "//granite-badge[@id='FaultDetails-FaultDetailsGroup-Objstate-badgeComponent-button']")
    private WebElement faultStatusBadge;

    @FindBy(xpath = "//fnd-input-field[@id='MyWorkPage-AvExeTaskGroup-TaskName']//form[@tooltip]")
    private WebElement descriptionField;

    private Map<String, String> taskDetailsMap = new HashMap<>();

    public void captureAndClickRaisedFault() {
        logger.info(STARTED + getCurrentMethodName());
        RaiseFaultButton.click();
        System.out.println("Element with ID 'AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell' clicked.");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickDetailsButton() {
        logger.info(STARTED + getCurrentMethodName());
        DetailsButton.click();
        System.out.println("Element clicked.");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickRequestDeferralButton() {
        logger.info(STARTED + getCurrentMethodName());
        requestDeferralButton.click();
        System.out.println("Request Deferral button clicked.");
        logger.info(ENDED + getCurrentMethodName());
    }

    public String getFaultStatus() {
        logger.info(STARTED + getCurrentMethodName());
        String status = faultStatusBadge.getText().trim();
        logger.info(ENDED + getCurrentMethodName());
        return status;
    }

    public void putDescriptionValueToMap() {
        logger.info(STARTED + getCurrentMethodName());
        String description = descriptionField.getAttribute("tooltip");
        taskDetailsMap.put("Description", description);
        System.out.println("Description added to the map");
        logger.info(ENDED + getCurrentMethodName());
    }

    public String getDescriptionValueFromMap() {
        logger.info(STARTED + getCurrentMethodName());
        String description = taskDetailsMap.get("Description");
        logger.info(ENDED + getCurrentMethodName());
        return description;
    }
}
