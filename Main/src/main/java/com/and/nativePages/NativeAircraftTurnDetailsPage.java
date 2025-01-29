package com.and.nativePages;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

public class NativeAircraftTurnDetailsPage extends NativeBasePage {

    private final By raiseFaultBtn = By.id("Raise Fault");

    public NativeAircraftTurnDetailsPage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    /**
     * Method for clicking Raise Fault Button
     */
    public void ClickRaiseFault() {
        logger.info(STARTED + getCurrentMethodName());
        clickElement(raiseFaultBtn);
        logger.info(ENDED + getCurrentMethodName());
    }

}
