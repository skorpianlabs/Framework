package com.and.nativePages;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.PageFactory;

import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

public class NativeLoginPage extends NativeBasePage{

    private final By loginBtn = By.xpath("//XCUIElementTypeButton[@name=\"LoginButton\"]");

    public NativeLoginPage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public void clickOnLoginButton(){
        logger.info(STARTED + getCurrentMethodName());
        clickElement(loginBtn);
        logger.info(ENDED + getCurrentMethodName());
    }

}
