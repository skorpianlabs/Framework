package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AircraftTurnsPage extends BasePage {

    private WebDriver driver;

    public AircraftTurnsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "MyAircraftTurnsCardPage-FlightsList-toggleFilterPane-fndListFilterButton-fndButton-button")
    private WebElement filterPanelButton;

    @FindBy(id = "MyAircraftTurnsCardPage-FlightsList-filterPane-fndMoreFieldsButton-moreButton")
    private WebElement moreFieldsButton;

    @FindBy(id = "MyAircraftTurnsCardPage-FlightsList-filterPane-fndMoreFieldsButton-LegNo")
    private WebElement arrivalFlightCheckbox;

    @FindBy(id = "MyAircraftTurnsCardPage-FlightsList-filterPane-fndFieldFilter-LegNo-optionsPane")
    private WebElement parentDiv;

    @FindBy(css = "#MyAircraftTurnsCardPage-FlightsList-filterPane-fndFieldFilter-LegNo-optionsPane input")
    private WebElement inputField;

    @FindBy(xpath = "//span[@data-fnd='card-title-label' and text()='YOW - IFSAD-1524 - (AIRLINE)']/following::button[@type='button' and contains(@class, 'button')]")
    private WebElement buttonElement;

    @FindBy(xpath = "//label[text()='System']/parent::*//button")
    private WebElement systemButton;

    @FindBy(xpath = "//span[text()='INTRODUCTION']")
    private WebElement systemValue;

    @FindBy(xpath = "//*[@id='MyAircraftTurnsCardPage-FlightsList-filterPane-fndFieldFilter-LegNo']/div/fnd-filter-badge/fnd-filter-badge-content/div/div")
    private WebElement buttonXpath;

    @FindBy(xpath = "//div[@role='tab' and @aria-controls='Deferred Faults' and text()=' Deferred Faults ']")
    private WebElement deferredFaultsTab;

    @FindBy(xpath = "//fnd-command-cell[.//div[contains(@class, 'granite-menu-trigger') and contains(@class, 'icon-ellipsis-vertical')]]")
    private WebElement commandCell;

    @FindBy(id = "AircraftTurnDetails-DeferredFaultsListInStandalone-fndRow-0-fndCommandCell-fndCommandDropdown-fndMenu-Details-fndButton-button")
    private WebElement deferDetailButton;

    public void clickFilterPanelButton() {
        filterPanelButton.click();
        System.out.println("User clicked the filter icon");
    }

    public void clickMoreFieldsButton() {
        moreFieldsButton.click();
        System.out.println("User clicked the 'More Fields' button");
    }

    public void clickArrivalFlightCheckbox() {
        if (!arrivalFlightCheckbox.isSelected()) {
            arrivalFlightCheckbox.click();
        }
        System.out.println("Arrival Flight checkbox is selected: " + arrivalFlightCheckbox.isSelected());
    }

    public void clickParentDiv() {
        parentDiv.click();
        System.out.println("User clicked the parent div.");
    }

    public void enterTextIntoInputField(String text) {
        clickParentDiv();
        inputField.clear();
        inputField.sendKeys(text);
        System.out.println("Entered text into the input field: " + text);
    }

    public void clickButtonRelativeToSpan() {
        buttonElement.click();
        System.out.println("User clicked the button relative to the span");
    }

    public void clickSystemButton() {
        systemButton.click();
        System.out.println("User clicked the System button.");
    }

    public void clickSystemValue() {
        systemValue.click();
        System.out.println("User clicked the System value.");
    }

    public void clickArrivalFlight() {
        buttonXpath.click();
        System.out.println("Button clicked.");
    }

    public void clickDeferredFaultsTab() {
        deferredFaultsTab.click();
        System.out.println("Clicked on 'Deferred Faults' tab");
    }

    public void clickDeferDetailButton() {
        deferDetailButton.click();
        System.out.println("Granite Button clicked successfully.");
    }

    public void clickCommandCell() {
        commandCell.click();
        System.out.println("Command cell clicked successfully.");
    }
}
