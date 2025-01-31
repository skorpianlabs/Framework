package com.and.pagesweb;

import com.and.utility.DriverProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.time.Duration;

public class BasePage {

    public final Logger logger = LogManager.getLogger(BasePage.class);
    protected WebDriver driver = DriverProvider.getWEBDriver();
    /**
     * Method for getting the current executing method name
     */
    public String getCurrentMethodName() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        return methodName;
    }


}