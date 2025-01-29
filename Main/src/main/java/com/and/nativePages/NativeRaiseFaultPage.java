package com.and.nativePages;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import java.util.Objects;

import static com.and.constant.CommonConstant.*;

public class NativeRaiseFaultPage extends NativeBasePage {
    private final By searchfieldSystem = By.xpath("//XCUIElementTypeStaticText[@name=\"Field_SubSystemId_2\"]");
    private final By fieldAircraftId = By.xpath("//XCUIElementTypeStaticText[@name=\"Field_AircraftId_1\"] | //XCUIElementTypeTextView[@name=\"Field_AircraftRef.AircraftMob_1\"]");
    private final By selectedAircraftId = By.id("Aircraft:");
    private final By searchBar = By.id("SearchBar");
    private final By faultSource = By.xpath("//XCUIElementTypeStaticText[@name=\"Field_FaultSourceCode_3\"] | //XCUIElementTypeStaticText[@name=\"Field_FaultSourceCode_4\"]");
    private final By fieldLogBook = By.xpath("//XCUIElementTypeStaticText[@name=\"Field_LogbookTypeCode_5\"] | //XCUIElementTypeStaticText[@name=\"Field_LogbookTypeCode_4\"]");
    private final By fieldLogBookRef = By.xpath("//XCUIElementTypeTextField[@name=\"Field_LogbookReference_6\"] | //XCUIElementTypeTextField[@name=\"Field_LogbookReference_5\"]");
    private final By fieldDescriptionType = By.xpath("//XCUIElementTypeTextView[@name=\"Field_Description_7\"] | //XCUIElementTypeTextView[@name=\"Field_Description_6\"]");
    private final By fieldFaultSeverityCode = By.xpath("//XCUIElementTypeStaticText[@name=\"Field_FaultSeverityCode_12\"] | (//XCUIElementTypeStaticText[@name=\"Field_FaultSeverityCode_10\"])[2]");
    private final By raiseFaultBtn = By.id("Raise Fault");

    public NativeRaiseFaultPage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void enterAircraft(String aircraftName) {
        logger.info(STARTED + getCurrentMethodName());

        if (Objects.equals(driver.findElement(fieldAircraftId).getAttribute(VALUE_ATTR), "Required")) {
            clickElement(fieldAircraftId);
            search(aircraftName);
            clickElement(selectedAircraftId);
        }

        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterSystem(String system) {
        logger.info(STARTED + getCurrentMethodName());

        clickElement(searchfieldSystem);
        search(system);
        clickElement(By.id(system));

        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterFaultSource(String source) {
        logger.info(STARTED + getCurrentMethodName());

        clickElement(faultSource);
        search(source);
        clickElement(By.id(source));

        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterLogbookType(String logbook) {
        logger.info(STARTED + getCurrentMethodName());

        clickElement(fieldLogBook);
        search(logbook);
        clickElement(By.id(logbook));

        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterLogbookReference(String logReference) {
        logger.info(STARTED + getCurrentMethodName());

        if (Objects.equals(driver.findElement(fieldLogBookRef).getAttribute(VALUE_ATTR), "Required")) {
            inputValue(fieldLogBookRef, logReference);
        } else {
            clearValue(fieldLogBookRef);
            inputValue(fieldLogBookRef, logReference);
        }

        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterDescription(String description) {
        logger.info(STARTED + getCurrentMethodName());

        if (Objects.equals(driver.findElement(fieldDescriptionType).getAttribute(VALUE_ATTR), "Required")) {
            inputValue(fieldDescriptionType, description);
        } else {
            clearValue(fieldDescriptionType);
            inputValue(fieldDescriptionType, description);
        }

        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterFoundDuringFlight(String description) {
        logger.info(STARTED + getCurrentMethodName());

        driver.findElement(fieldDescriptionType).sendKeys(description);

        logger.info(ENDED + getCurrentMethodName());
    }

    public void enterFaultSeverity(String faultSeverityCode) {
        logger.info(STARTED + getCurrentMethodName());

        clickElement(fieldFaultSeverityCode);
        search(faultSeverityCode);
        clickElement(By.id(faultSeverityCode));

        logger.info(ENDED + getCurrentMethodName());
    }

    public void clickRaiseFault() {
        logger.info(STARTED + getCurrentMethodName());
        clickElement(raiseFaultBtn);
        logger.info(ENDED + getCurrentMethodName());
    }
}
