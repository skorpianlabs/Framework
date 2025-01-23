package com.and.nativeService;

import com.and.constant.CommonConstant;
import com.and.utility.nativeUtil.NativePageObjectFactory;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;

public class NativeBaseService {
    protected IOSDriver driver;
    protected NativePageObjectFactory nativePageObjectFactory;
    protected Logger logger = LogManager.getLogger(RaiseFaultNativeService.class);

    protected void setupNativePageFactory() {
        if (nativePageObjectFactory == null) {
            this.driver = NativeDriverProvider.getIosDriver();
            this.nativePageObjectFactory = new NativePageObjectFactory(driver);
        }
    }

    public String getCurrentMethodName() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        return methodName;
    }

    /**
     * Method for clicking an element
     */
    public void clickElement(By by) {
        try {
            driver.findElement(by).click();
        } catch (Exception e) {
            throw new AssertionError(CommonConstant.ELEMENT_NOT_CLICKABLE + by, e);
        }
    }


}
