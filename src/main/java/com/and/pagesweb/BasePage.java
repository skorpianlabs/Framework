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
    protected FluentWait<WebDriver> fluentWait;
    protected FluentWait<WebDriver> fluentWaitMIni;

    public BasePage(){
        fluentWait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofSeconds(5))
                .ignoring(NoSuchElementException.class);

        fluentWaitMIni = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(8))
                .pollingEvery(Duration.ofSeconds(2))
                .ignoring(NoSuchElementException.class);// Handle the exception
    }
    // Wait until an element is visible
    public WebElement waitForVisibility(By locator) {
        return fluentWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait until an element is clickable
    public WebElement waitForElementToBeClickable(By locator) {
        return fluentWait.until(ExpectedConditions.elementToBeClickable(locator));
    };
    public WebElement miniWaitForVisibility(By locator) {
        return fluentWaitMIni.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    // Wait until an element is clickable
    public WebElement miniWwaitForElementToBeClickable(By locator) {
        return fluentWaitMIni.until(ExpectedConditions.elementToBeClickable(locator));
    };

    /**
     * Method for getting the current executing method name
     */
    public String getCurrentMethodName() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        return methodName;
    }


}