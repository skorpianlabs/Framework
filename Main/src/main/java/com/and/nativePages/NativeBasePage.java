package com.and.nativePages;

import com.and.constant.CommonConstant;
import com.and.constant.TimeWaitConstant;
import com.and.pagesweb.BasePage;
import io.appium.java_client.ios.IOSDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

public class NativeBasePage {

    public IOSDriver driver;
    private final By searchBar = By.id("SearchBar");
    public final WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(TimeWaitConstant.SMALL_WAIT));;

    public NativeBasePage(IOSDriver driver) {
        this.driver = driver;
    }

    public final Logger logger = LogManager.getLogger(BasePage.class);

    /**
     * Method for getting the current executing method name
     */
    public String getCurrentMethodName() {

        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        String methodName = stackTrace[2].getMethodName();
        return methodName;
    }

    /**
     * Method for searching a value
     */
    public void search(String searchValue) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            inputValue(searchBar, searchValue);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(TimeWaitConstant.MINI_WAIT));
        } catch (Exception e) {
            throw new AssertionError(CommonConstant.ELEMENT_NOT_CLICKABLE + searchBar, e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    /**
     * Method for clicking an element
     */
    public void clickElement(By by) {
        try {
            waitUntilElementVisible(by);
            driver.findElement(by).click();
        } catch (Exception e) {
            throw new AssertionError(CommonConstant.ELEMENT_NOT_CLICKABLE + by, e);
        }
    }

    /**
     * Method for inserting data into a field
     */
    public void inputValue(By by, String text) {
        try {
            waitUntilElementVisible(by);
            driver.findElement(by).sendKeys(text);
        } catch (Exception e) {
            throw new AssertionError(CommonConstant.ELEMENT_NOT_FOUND + by, e);
        }
    }

    /**
     * Method for waiting until the element is visible
     */
    public void waitUntilElementVisible(By by) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }


    /**
     * Method for clearing data in a field
     */
    public void clearValue(By by) {
        String inputText = driver.findElement(by).getAttribute("value");
        if (inputText != null) {
            for (int i = 0; i < inputText.length(); i++) {
                driver.findElement(by).sendKeys(Keys.BACK_SPACE);
            }
        }
    }


}
