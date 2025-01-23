package com.and.nativePages;

import io.appium.java_client.ios.IOSDriver;
import org.openqa.selenium.support.PageFactory;

public class NativeAircraftTurnsPage extends NativeBasePage {

    public NativeAircraftTurnsPage(IOSDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }
}
