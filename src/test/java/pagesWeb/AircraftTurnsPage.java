package pagesWeb;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import static constant.CommonConstant.ENDED;
import static constant.CommonConstant.STARTED;

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
    private WebElement LegNoinputField;

    @FindBy(xpath = "//span[@data-fnd='card-title-label' and text()='YOW - IFSAD-1524 - (AIRLINE)']/following::button[@type='button' and contains(@class, 'button')]")
    private WebElement turnSelectionButton;

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
        logger.info(STARTED + getCurrentMethodName());
        filterPanelButton.click();
        System.out.println("User clicked the filter icon");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickMoreFieldsButton() {
        logger.info(STARTED + getCurrentMethodName());
        moreFieldsButton.click();
        System.out.println("User clicked the 'More Fields' button");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickArrivalFlightCheckbox() {
        logger.info(STARTED + getCurrentMethodName());
        if (!arrivalFlightCheckbox.isSelected()) {
            arrivalFlightCheckbox.click();
        }
        System.out.println("Arrival Flight checkbox is selected: " + arrivalFlightCheckbox.isSelected());
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickParentDiv() {
        logger.info(STARTED + getCurrentMethodName());
        parentDiv.click();
        System.out.println("User clicked the parent div.");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterTextIntoInputField(String text) {
        logger.info(STARTED + getCurrentMethodName());
        clickParentDiv();
        LegNoinputField.clear();
        LegNoinputField.sendKeys(text);
        System.out.println("Entered text into the input field: " + text);
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickSelectedTurn() {
        logger.info(STARTED + getCurrentMethodName());
        turnSelectionButton.click();
        System.out.println("User clicked the button relative to the span");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickSystemButton() {
        logger.info(STARTED + getCurrentMethodName());
        systemButton.click();
        System.out.println("User clicked the System button.");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickSystemValue() {
        logger.info(STARTED + getCurrentMethodName());
        systemValue.click();
        System.out.println("User clicked the System value.");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickArrivalFlight() {
        logger.info(STARTED + getCurrentMethodName());
        buttonXpath.click();
        System.out.println("Button clicked.");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickDeferredFaultsTab() {
        logger.info(STARTED + getCurrentMethodName());
        deferredFaultsTab.click();
        System.out.println("Clicked on 'Deferred Faults' tab");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickDeferDetailButton() {
        logger.info(STARTED + getCurrentMethodName());
        deferDetailButton.click();
        System.out.println("Granite Button clicked successfully.");
        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickCommandCell() {
        logger.info(STARTED + getCurrentMethodName());
        commandCell.click();
        System.out.println("Command cell clicked successfully.");
        logger.info(ENDED + getCurrentMethodName());
    }
}
