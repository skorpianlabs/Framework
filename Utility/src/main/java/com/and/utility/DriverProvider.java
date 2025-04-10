package com.and.utility;

import com.and.utility.nativeUtil.Capabilities;
import com.google.inject.Inject;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;

import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class DriverProvider {

    // Map to store the WebDriver instances for each thread
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

    // @Before hook to initialize both ChromeDriver and EdgeDriver for each thread
    @Before
    public static void initializeDriver() throws MalformedURLException {

        long threadId = Thread.currentThread().getId();

        // Initialize ChromeDriver if not already initialized
        if (!DriverMap.containsKey(threadId)) {

            WebDriver webDriver = null;

            // Get the browser type from the properties file
            String browser = Optional.ofNullable(PropertyManager.getInstance().getProperty("webdriver.browser"))
                    .orElse("chrome")
                    .toLowerCase();

            // Initialize WebDriver based on the browser type
            switch (browser) {
                case "chrome":
                    webDriver = new ChromeDriver();
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

            // Get the initial URL from the properties file
            String url = PropertyManager.getInstance().getProperty("webdriver.url");

            webDriver.get(url);
            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Store the WebDriver instance in the DriverMap
            DriverMap.put(threadId, webDriver);

            // Initialize FluentWait objects for the current thread
            FluentWait<WebDriver> fluentWait = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(30))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);

            FluentWait<WebDriver> fluentWaitMini = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(8))
                    .pollingEvery(Duration.ofSeconds(1))
                    .ignoring(NoSuchElementException.class);

            FluentWait<WebDriver> fluentWaitMax = new FluentWait<>(webDriver)
                    .withTimeout(Duration.ofSeconds(59))
                    .pollingEvery(Duration.ofSeconds(10))
                    .ignoring(NoSuchElementException.class);

            fluentWaitMap.put(threadId, fluentWait);
            fluentWaitMiniMap.put(threadId, fluentWaitMini);
            fluentWaitMaxMap.put(threadId, fluentWaitMax);
        }

        // Initialize the database connection if not already initialized for this thread
        if (dbConnectionManger == null) {
            dbConnectionManger = new DBConnectionManger("src/main/resources/properties/database.properties");
            dbConnectionManger2 = new DBConnectionManger("src/main/resources/properties/database2.properties");
        }

        if (!connectionMap.containsKey(threadId)) {
            try {
                Connection connection = dbConnectionManger.getConnection();
                connectionMap.put(threadId, connection);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to get database connection1 for thread " + threadId, e);
            }
        }

        // Initialize the second database connection if not already initialized for this thread
        if (!connectionMap2.containsKey(threadId)) {
            try {
                Connection connection2 = dbConnectionManger2.getConnection();
                connectionMap2.put(threadId, connection2);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to get database connection2 for thread " + threadId, e);
            }
        }
    }

    // Method to get WebDriver for the current thread
    public static WebDriver getWEBDriver() {
        return DriverMap.get(Thread.currentThread().getId());
    }

    // Method to get IOSDriver for the current thread
    public static IOSDriver getIOSDriver() {
        return iOSDriverMap.get(Thread.currentThread().getId());
    }

    // Method to get database connection 1
    public static Connection getConnection() {
        return connectionMap.get(Thread.currentThread().getId());
    }

    // Method to get database connection 2
    public static Connection getConnection2() {
        return connectionMap2.get(Thread.currentThread().getId());
    }

    // Method to get FluentWait for the current thread
    public static FluentWait<WebDriver> getFluentWait() {
        return fluentWaitMap.get(Thread.currentThread().getId());
    }

    // Method to get Mini FluentWait for the current thread
    public static FluentWait<WebDriver> getFluentWaitMini() {
        return fluentWaitMiniMap.get(Thread.currentThread().getId());
    }

    // Method to get Max FluentWait for the current thread
    public static FluentWait<WebDriver> getFluentWaitMax() {
        return fluentWaitMaxMap.get(Thread.currentThread().getId());
    }

    // @After hook to quit both drivers after each scenario
    @After
    public static void quitDriver(Scenario scenario) {
        long threadId = Thread.currentThread().getId();

        // Quit ChromeDriver if it exists
        WebDriver webDriverDriver = DriverMap.get(threadId);
        if (webDriverDriver != null) {
            if (scenario.isFailed()) {
                final byte[] scrnShot = ((TakesScreenshot) webDriverDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(scrnShot, "image/png", scenario.getName());
            }
            webDriverDriver.quit();
            DriverMap.remove(threadId);
        }

        // Quit IOSDriver if it exists
        IOSDriver iosDriver = iOSDriverMap.get(threadId);
        if (iosDriver != null) {
            if (scenario.isFailed()) {
                final byte[] scrnShot = ((TakesScreenshot) iosDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(scrnShot, "image/png", scenario.getName());
            }
            iosDriver.quit();
            iOSDriverMap.remove(threadId);
        }

        // Close the database connection if it exists
        Connection connection = connectionMap.get(threadId);
        if (connection != null) {
            try {
                if (!connection.isClosed()) {
                    connection.close(); // Return the connection to the pool
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            connectionMap.remove(threadId);
        }

        // Close the second database connection if it exists
        Connection connection2 = connectionMap2.get(threadId);
        if (connection2 != null) {
            try {
                if (!connection2.isClosed()) {
                    connection2.close(); // Return the connection to the pool
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

    // Wait until an element is visible (default FluentWait)
    public static WebElement waitForVisibility(WebElement element) {
        return getFluentWait().until(ExpectedConditions.visibilityOf(element));
    }

    // Wait until an element is clickable (default FluentWait)
    public static WebElement waitForElementToBeClickable(WebElement element) {
        return getFluentWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    // Mini wait until an element is visible
    public static WebElement miniWaitForVisibility(WebElement element) {
        return getFluentWaitMini().until(ExpectedConditions.visibilityOf(element));
    }

    // Max wait until an element is visible
    public static WebElement maxWaitForVisibility(WebElement element) {
        return getFluentWaitMax().until(ExpectedConditions.visibilityOf(element));
    }

    // Wait until an element is present in the DOM
    public static WebElement waitForPresenceOfElementLocated(By locator) {
        return getFluentWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Mini wait until an element is present in the DOM
    public static WebElement miniWaitForPresenceOfElementLocated(By locator) {
        return getFluentWaitMini().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Max wait until an element is present in the DOM
    public static WebElement maxWaitForPresenceOfElementLocated(By locator) {
        return getFluentWaitMax().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    // Wait until an element is invisible
    public static Boolean waitForInvisibilityOf(WebElement element) {
        return getFluentWait().until(ExpectedConditions.invisibilityOf(element));
    }

    // Mini wait until an element is invisible
    public static Boolean miniWaitForInvisibilityOf(WebElement element) {
        return getFluentWaitMini().until(ExpectedConditions.invisibilityOf(element));
    }

    // Max wait until an element is invisible
    public static Boolean maxWaitForInvisibilityOf(WebElement element) {
        return getFluentWaitMax().until(ExpectedConditions.invisibilityOf(element));
    }

    // Wait until an alert is present
    public static Alert waitForAlert() {
        return getFluentWait().until(ExpectedConditions.alertIsPresent());
    }

    // Mini wait until an alert is present
    public static Alert miniWaitForAlert() {
        return getFluentWaitMini().until(ExpectedConditions.alertIsPresent());
    }

    // Max wait until an alert is present
    public static Alert maxWaitForAlert() {
        return getFluentWaitMax().until(ExpectedConditions.alertIsPresent());
    }

    // Max wait: Wait until an element is clickable
    public static WebElement maxWaitForElementToBeClickable(WebElement element) {
        return getFluentWaitMax().until(ExpectedConditions.elementToBeClickable(element));
    }

    // Mini wait: Wait until an element is clickable
    public static WebElement miniWaitForElementToBeClickable(WebElement element) {
        return getFluentWaitMini().until(ExpectedConditions.elementToBeClickable(element));
    }

    // Max wait: Wait until an element is clickable (using By locator)
    public static WebElement maxWaitForElementToBeClickable(By locator) {
        return getFluentWaitMax().until(ExpectedConditions.elementToBeClickable(locator));
    }

    // Mini wait: Wait until an element is clickable (using By locator)
    public static WebElement miniWaitForElementToBeClickable(By locator) {
        return getFluentWaitMini().until(ExpectedConditions.elementToBeClickable(locator));
    }
}
