package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.Map;

public class AircraftTurnDetailsPage extends BasePage {

    private WebDriver driver;

    public AircraftTurnDetailsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell")
    private WebElement elementId;

    @FindBy(xpath = "//fnd-granite-button-wrapper/a/span[2]")
    private WebElement simpleXpath;

    @FindBy(xpath = "//a[@title='Request Deferral']")
    private WebElement requestDeferralButton;

    @FindBy(id = "AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell-fndCommandDropdown-fndMenu-Details-fndButton-button")
    private WebElement detailsButton;

    @FindBy(xpath = "//granite-badge[@id='FaultDetails-FaultDetailsGroup-Objstate-badgeComponent-button']")
    private WebElement faultStatusBadge;

    @FindBy(xpath = "//fnd-input-field[@id='MyWorkPage-AvExeTaskGroup-TaskName']//form[@tooltip]")
    private WebElement descriptionField;

    private Map<String, String> taskDetailsMap = new HashMap<>();

    public void captureAndClickRaisedFault() {
        elementId.click();
        System.out.println("Element with ID 'AircraftTurnDetails-TaskList-fndRow-0-fndCommandCell' clicked.");
    }

    public void clickDetailsButton() {
        detailsButton.click();
        System.out.println("Element clicked.");
    }

    public void clickRequestDeferralButton() {
        requestDeferralButton.click();
        System.out.println("Request Deferral button clicked.");
    }

    public String getFaultStatus() {
        return faultStatusBadge.getText().trim();
    }

    public void putDescriptionValueToMap() {
        String description = descriptionField.getAttribute("tooltip");
        taskDetailsMap.put("Description", description);
        System.out.println("Description added to the map");
    }

    public String getDescriptionValueFromMap() {
        return taskDetailsMap.get("Description");
    }
}
