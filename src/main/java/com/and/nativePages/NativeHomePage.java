package com.and.nativePages;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.support.PageFactory;

public class NativeHomePage extends NativeBasePage {

    public NativeHomePage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

}
