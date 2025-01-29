package com.and.nativePages;

import com.and.constant.CommonConstant;
import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

public class NativeTechnicianLobbyPage extends NativeBasePage {

    private final By myAircraftTurnsTitle = By.id("My Aircraft Turns");
    private final By raiseFaultTitle = By.id("Raise Fault");
    private final By myWorkTitle = By.id("My Work");

    public NativeTechnicianLobbyPage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Method for checking the presence of My Aircraft Turns
     */
    public String checkMyAircraftTurnsLobbyIcon() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            waitUntilElementVisible(myAircraftTurnsTitle);
            return driver.findElement(myAircraftTurnsTitle).getText();
        } catch (Exception e) {
            throw new AssertionError(CommonConstant.ELEMENT_NOT_FOUND + myAircraftTurnsTitle, e);
        }
    }

    /**
     * Method for clicking the My Aircraft Turns Lobby Icon
     */
    public void clickMyAircraftTurnsLobbyIcon() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            waitUntilElementVisible(myAircraftTurnsTitle);
            driver.findElement(myAircraftTurnsTitle).click();
        } catch (Exception e) {
            throw new AssertionError(CommonConstant.ELEMENT_NOT_CLICKABLE + myAircraftTurnsTitle, e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    /**
     * Method for clicking the Raise Fault Lobby Icon
     */
    public void clickRaiseFaultLobbyIcon() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            waitUntilElementVisible(raiseFaultTitle);
            driver.findElement(raiseFaultTitle).click();
        } catch (Exception e) {
            throw new AssertionError(CommonConstant.ELEMENT_NOT_CLICKABLE + raiseFaultTitle, e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    /**
     * Method for clicking the My Work Lobby Icon
     */
    public void clickMyWorkLobbyIcon() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            waitUntilElementVisible(myWorkTitle);
            driver.findElement(myWorkTitle).click();
        } catch (Exception e) {
            throw new AssertionError(CommonConstant.ELEMENT_NOT_CLICKABLE + myWorkTitle, e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

}
