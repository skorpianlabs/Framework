package pagesWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.time.Duration;

public class AircraftTurnsPage  {

    private WebDriver driver;

    public AircraftTurnsPage(WebDriver driver) {
        this.driver = driver;
    }

    private  By FILTER_PANEL_BUTTON = By.id("MyAircraftTurnsCardPage-FlightsList-toggleFilterPane-fndListFilterButton-fndButton-button");
    private  By MORE_FIELDS_BUTTON = By.id("MyAircraftTurnsCardPage-FlightsList-filterPane-fndMoreFieldsButton-moreButton");
    private  By ARRIVAL_FLIGHT_CHECKBOX = By.id("MyAircraftTurnsCardPage-FlightsList-filterPane-fndMoreFieldsButton-LegNo");
    private  By PARENT_DIV = By.id("MyAircraftTurnsCardPage-FlightsList-filterPane-fndFieldFilter-LegNo-optionsPane");
    private  By INPUT_FIELD = By.cssSelector("#MyAircraftTurnsCardPage-FlightsList-filterPane-fndFieldFilter-LegNo-optionsPane input");
    private  By BUTTON_ELEMENT = By.xpath("//span[@data-fnd='card-title-label' and text()='YOW - IFSAD-1524 - (AIRLINE)']/following::button[@type='button' and contains(@class, 'button')]");
    private  By SYSTEM_BUTTON = By.xpath("//label[text()='System']/parent::*//button");
    private  By SYSTEM_VALUE = By.xpath("//span[text()='INTRODUCTION']");
    private   By BUTTON_XPATH = By.xpath("//*[@id='MyAircraftTurnsCardPage-FlightsList-filterPane-fndFieldFilter-LegNo']/div/fnd-filter-badge/fnd-filter-badge-content/div/div");
    private   By DEFERRED_FAULTS_TAB = By.xpath("//div[@role='tab' and @aria-controls='Deferred Faults' and text()=' Deferred Faults ']");
    private   By COMMAND_CELL = By.xpath("//fnd-command-cell[.//div[contains(@class, 'granite-menu-trigger') and contains(@class, 'icon-ellipsis-vertical')]]");
    private   By DeferDetail = By.id("AircraftTurnDetails-DeferredFaultsListInStandalone-fndRow-0-fndCommandCell-fndCommandDropdown-fndMenu-Details-fndButton-button");
    public  WebElement getFilterPanelButton() {
        return driver.findElement(FILTER_PANEL_BUTTON);
    }

    // Method to click the filter panel button
    public  void clickFilterPanelButton() throws InterruptedException {
        // Wait for the filter button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        WebElement filterButton = wait.until(ExpectedConditions.elementToBeClickable(getFilterPanelButton()));

        // Click the filter button
        filterButton.click();
        System.out.println("User clicked the filter icon");
        Thread.sleep(10000);
    }

    // Method to get the 'More Fields' button div element
    public  WebElement getMoreFieldsButton() {
        return driver.findElement(MORE_FIELDS_BUTTON);
    }

    // Method to click the 'More Fields' button
    public  void clickMoreFieldsButton() {
        // Wait for the More Fields button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        WebElement moreFieldsButton = wait.until(ExpectedConditions.elementToBeClickable(getMoreFieldsButton()));

        // Click the 'More Fields' button
        moreFieldsButton.click();
        System.out.println("User clicked the 'More Fields' button");
    }

    // Method to get the 'Arrival Flight' checkbox input element
    private  WebElement getArrivalFlightCheckbox() {
        return driver.findElement(ARRIVAL_FLIGHT_CHECKBOX);
    }

    // Method to click the 'Arrival Flight' checkbox
    public  void clickArrivalFlightCheckbox() {
        // Wait for the checkbox to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(getArrivalFlightCheckbox()));

        System.out.println("Checkbox is selected: " + checkbox.isSelected());
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }

    // Method to get the parent div element
    public  WebElement getParentDiv() {
        return driver.findElement(PARENT_DIV);
    }

    // Method to get the input field element inside the parent div
    public  WebElement getInputField() {
        return driver.findElement(INPUT_FIELD);
    }

    // Method to click the parent div element.
    public  void clickParentDiv() {
        // Wait for the parent div to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        WebElement parentDiv = wait.until(ExpectedConditions.elementToBeClickable(getParentDiv()));

        parentDiv.click();  // Clicks the parent div
        System.out.println("User clicked the parent div.");
    }

    // Method to enter text into the input field after clicking the parent div
    public  void enterTextIntoInputField(String text) throws InterruptedException {
        // First, click the parent div (which may reveal the input field)
        clickParentDiv();

        // Then, locate the input field inside the parent div and interact with it
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        WebElement inputField = wait.until(ExpectedConditions.visibilityOf(getInputField()));

        // Clear the existing text (if any) and insert the desired value
        inputField.clear();
        inputField.sendKeys(text);
        System.out.println("Entered text into the input field: " + text);
        Thread.sleep(5000);
    }

    // Method to get the <button> element relative to the <span> element
    public  WebElement getButtonElement() {
        return driver.findElement(BUTTON_ELEMENT);
    }

    // Method to click the <button> element relative to the <span> element
    public  void clickButtonRelativeToSpan() throws InterruptedException {
        // Wait for the button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(getButtonElement()));

        // Click the button
        button.click();
        System.out.println("User clicked the button relative to the span");
        Thread.sleep(10000);
    }

    // Method to get the System button element
    public  WebElement getSystemButton() {
        return driver.findElement(SYSTEM_BUTTON);
    }

    // Method to click the System button
    public  void clickSystemButton() {
        // Wait for the system button to be clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        WebElement systemButton = wait.until(ExpectedConditions.elementToBeClickable(getSystemButton()));

        // Click the system button
        systemButton.click();
        System.out.println("User clicked the System button.");
    }

    // Method to get the System value element
    public  WebElement getSystemValue() {
        return driver.findElement(SYSTEM_VALUE);
    }

    // Method to click the System value element
    public  void clickSystemValue() {
        // Wait for the system value to be visible
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10)); // Wait up to 10 seconds
        WebElement systemValue = wait.until(ExpectedConditions.visibilityOf(getSystemValue()));

        // Click the system value
        systemValue.click();
        System.out.println("User clicked the System value.");
    }

    // Method to check if the checkbox is selected
    public boolean isArrivalFlightCheckboxSelected() {
        return getArrivalFlightCheckbox().isSelected();
    }
    public void clickArrivalFlight() {
        // Create WebDriverWait object with a 10-second timeout
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the button is clickable
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(BUTTON_XPATH));

        // Click the button
        button.click();

        // Optional: Print a message indicating the button was clicked
        System.out.println("Button clicked.");
    }


    // Method to capture the element and click it
    public void clickDeferredFaultsTab() {
        // Wait for the tab to be visible and clickable
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement deferredFaultsTab = wait.until(ExpectedConditions.elementToBeClickable(DEFERRED_FAULTS_TAB));

        // Click the "Deferred Faults" tab
        deferredFaultsTab.click();
        System.out.println("Clicked on 'Deferred Faults' tab");
    }
    public void clickDeferDetailButton() {
        // Create a WebDriverWait instance with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the granite button element is clickable
        WebElement graniteButton = wait.until(ExpectedConditions.elementToBeClickable(DeferDetail));

        // Click on the granite button element
        graniteButton.click();

        System.out.println("Granite Button clicked successfully.");
    }
    public void clickCommandCell() {
        // Create a WebDriverWait instance with a timeout of 10 seconds
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        // Wait until the element is clickable
        WebElement commandCell = wait.until(ExpectedConditions.elementToBeClickable(COMMAND_CELL));

        // Click the element
        commandCell.click();

        System.out.println("Command cell clicked successfully.");
    }
}
