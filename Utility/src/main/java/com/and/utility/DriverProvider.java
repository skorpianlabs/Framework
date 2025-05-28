package com.and.utility;

import com.and.utility.nativeUtil.Capabilities;
import com.google.inject.Inject;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.*;

public class DriverProvider {

    private static final Map<Long, WebDriver> DriverMap = new HashMap<>();
    private static final Map<Long, Connection> connectionMap = new HashMap<>();
    private static final Map<Long, Connection> connectionMap2 = new HashMap<>();
    private static final Map<Long, IOSDriver> iOSDriverMap = new HashMap<>();
    private static final Map<Long, FluentWait<WebDriver>> fluentWaitMap = new HashMap<>();
    private static final Map<Long, FluentWait<WebDriver>> fluentWaitMiniMap = new HashMap<>();
    private static final Map<Long, FluentWait<WebDriver>> fluentWaitMaxMap = new HashMap<>();

    @Inject
    private static Capabilities capabilities;
    private static DBConnectionManger dbConnectionManger;
    private static DBConnectionManger dbConnectionManger2;

    // New @Before hook that skips scenarios with @api tag
    @Before(order = 0)
    public void initializeDriver(Scenario scenario) throws MalformedURLException {
        if (!scenario.getSourceTagNames().contains("@api")) {
            initializeDriverIfNotApi();
        }
        else {
            System.out.println("Skipping WebDriver initialization for @api scenario: " + scenario.getName());
        }
    }

    // This is now a plain method
    public static void initializeDriverIfNotApi() throws MalformedURLException {
        long threadId = Thread.currentThread().getId();

        if (!DriverMap.containsKey(threadId)) {
            WebDriver webDriver = null;

            String browser = Optional.ofNullable(PropertyManager.getInstance().getProperty("webdriver.browser"))
                    .orElse("chrome")
                    .toLowerCase();

            switch (browser) {
                case "chrome":
                    Map<String, Object> prefs = new HashMap<>();
                    prefs.put("credentials_enable_service", false);
                    prefs.put("profile.password_manager_enabled", false);
                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", prefs);
                    webDriver = new ChromeDriver(options);
                    break;
                case "firefox":
                    webDriver = new FirefoxDriver();
                    break;
                case "edge":
                    webDriver = new EdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser type: " + browser);
            }

            String url = PropertyManager.getInstance().getProperty("webdriver.url");
            webDriver.get(url);
            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            DriverMap.put(threadId, webDriver);

            fluentWaitMap.put(threadId, new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class));

            fluentWaitMiniMap.put(threadId, new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(8))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class));

            fluentWaitMaxMap.put(threadId, new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(59))
                    .pollingEvery(Duration.ofSeconds(10))
                    .ignoring(NoSuchElementException.class));
        }

        if (dbConnectionManger == null) {
            dbConnectionManger = new DBConnectionManger("src/main/resources/properties/database.properties");
            dbConnectionManger2 = new DBConnectionManger("src/main/resources/properties/database2.properties");
        }

        if (!connectionMap.containsKey(threadId)) {
            try {
                connectionMap.put(threadId, dbConnectionManger.getConnection());
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to get database connection1 for thread " + threadId, e);
            }
        }

        if (!connectionMap2.containsKey(threadId)) {
            try {
                connectionMap2.put(threadId, dbConnectionManger2.getConnection());
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to get database connection2 for thread " + threadId, e);
            }
        }
    }

    @After
    public static void quitDriver(Scenario scenario) {
        long threadId = Thread.currentThread().getId();

        WebDriver webDriverDriver = DriverMap.get(threadId);
        if (webDriverDriver != null) {
            if (scenario.isFailed()) {
                final byte[] scrnShot = ((TakesScreenshot) webDriverDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(scrnShot, "image/png", scenario.getName());
            }
            webDriverDriver.quit();
            DriverMap.remove(threadId);
        }

        IOSDriver iosDriver = iOSDriverMap.get(threadId);
        if (iosDriver != null) {
            if (scenario.isFailed()) {
                final byte[] scrnShot = ((TakesScreenshot) iosDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(scrnShot, "image/png", scenario.getName());
            }
            iosDriver.quit();
            iOSDriverMap.remove(threadId);
        }

        Connection connection = connectionMap.get(threadId);
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionMap.remove(threadId);
        }

        Connection connection2 = connectionMap2.get(threadId);
        if (connection2 != null) {
            try {
                if (!connection2.isClosed()) {
                    connection2.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionMap2.remove(threadId);
        }

        fluentWaitMap.remove(threadId);
        fluentWaitMiniMap.remove(threadId);
        fluentWaitMaxMap.remove(threadId);
    }

    // Utility methods
    public static WebDriver getWEBDriver() {
        return DriverMap.get(Thread.currentThread().getId());
    }

    public static IOSDriver getIOSDriver() {
        return iOSDriverMap.get(Thread.currentThread().getId());
    }

    public static Connection getConnection() {
        return connectionMap.get(Thread.currentThread().getId());
    }

    public static Connection getConnection2() {
        return connectionMap2.get(Thread.currentThread().getId());
    }

    public static FluentWait<WebDriver> getFluentWait() {
        return fluentWaitMap.get(Thread.currentThread().getId());
    }

    public static FluentWait<WebDriver> getFluentWaitMini() {
        return fluentWaitMiniMap.get(Thread.currentThread().getId());
    }

    public static FluentWait<WebDriver> getFluentWaitMax() {
        return fluentWaitMaxMap.get(Thread.currentThread().getId());
    }

    public static WebElement waitForVisibility(WebElement element) {
        return getFluentWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForElementToBeClickable(WebElement element) {
        return getFluentWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement miniWaitForVisibility(WebElement element) {
        return getFluentWaitMini().until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement maxWaitForVisibility(WebElement element) {
        return getFluentWaitMax().until(ExpectedConditions.visibilityOf(element));
    }

    public static WebElement waitForPresenceOfElementLocated(By locator) {
        return getFluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement miniWaitForPresenceOfElementLocated(By locator) {
        return getFluentWaitMini().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static WebElement maxWaitForPresenceOfElementLocated(By locator) {
        return getFluentWaitMax().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static Boolean waitForInvisibilityOf(WebElement element) {
        return getFluentWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public static Boolean miniWaitForInvisibilityOf(WebElement element) {
        return getFluentWaitMini().until(ExpectedConditions.invisibilityOf(element));
    }

    public static Boolean maxWaitForInvisibilityOf(WebElement element) {
        return getFluentWaitMax().until(ExpectedConditions.invisibilityOf(element));
    }

    public static Alert waitForAlert() {
        return getFluentWait().until(ExpectedConditions.alertIsPresent());
    }

    public static Alert miniWaitForAlert() {
        return getFluentWaitMini().until(ExpectedConditions.alertIsPresent());
    }

    public static Alert maxWaitForAlert() {
        return getFluentWaitMax().until(ExpectedConditions.alertIsPresent());
    }

    public static WebElement maxWaitForElementToBeClickable(WebElement element) {
        return getFluentWaitMax().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement miniWaitForElementToBeClickable(WebElement element) {
        return getFluentWaitMini().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static WebElement maxWaitForElementToBeClickable(By locator) {
        return getFluentWaitMax().until(ExpectedConditions.elementToBeClickable(locator));
    }

    public static WebElement miniWaitForElementToBeClickable(By locator) {
        return getFluentWaitMini().until(ExpectedConditions.elementToBeClickable(locator));
    }
}
