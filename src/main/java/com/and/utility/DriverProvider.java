package com.and.utility;

import com.and.utility.nativeUtil.Capabilities;
import com.google.inject.Inject;
import io.appium.java_client.ios.IOSDriver;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class DriverProvider {

    // Map to store the WebDriver instances for each thread
    private static final Map<Long, WebDriver> DriverMap = new HashMap<>();
    private static final Map<Long, WebDriver> mobileDriverMap = new HashMap<>();
    private static final Map<Long, Connection> connectionMap = new HashMap<>();
    private static final Map<Long, IOSDriver> iOSDriverMap = new HashMap<>();
    @Inject
    private static Capabilities capabilities;
    private static DBConnectionManger dbConnectionManger;

    // @Before hook to initialize both ChromeDriver and EdgeDriver for each thread
    @Before
    public static void initializeDriver() throws MalformedURLException {

        long threadId = Thread.currentThread().getId();

        // Initialize ChromeDriver if not already initialized
        if (!DriverMap.containsKey(threadId)) {
            // Properties file path
            String propertiesFilePath = "src/test/java/config.properties";
            Properties properties = new Properties();

            // Load the properties file inside the if block
            try (FileInputStream fis = new FileInputStream(propertiesFilePath)) {
                properties.load(fis);
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to load properties file", e);
            }

            WebDriver webDriver = null;

            // Get the browser type from the properties file
            String browser = properties.getProperty("webdriver.browser", "chrome").toLowerCase();

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

            // Get the URL from properties file (the environment URL)
            String url = properties.getProperty("webdriver.url", "https://default-url.com");

            // Navigate to the environment URL
            webDriver.get(url);
            webDriver.manage().window().maximize();
            webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

            // Store the WebDriver instance in the DriverMap
            DriverMap.put(threadId, webDriver);
        }

        /*if (!iOSDriverMap.containsKey(threadId)) {
            IOSDriver iOSDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities.getCapabilities());

            iOSDriverMap.put(threadId, iOSDriver);

        }*/

        if (dbConnectionManger == null) {
            dbConnectionManger = new DBConnectionManger("src/test/java/database.properties");
        }

        // Initialize the database connection if not already initialized for this thread
        if (!connectionMap.containsKey(threadId)) {
            try {
                Connection connection = dbConnectionManger.getConnection();
                connectionMap.put(threadId, connection);
            } catch (SQLException e) {
                e.printStackTrace();
                throw new RuntimeException("Failed to get database connection for thread " + threadId, e);
            }
        }
    }

    // Method to get ChromeDriver for the current thread
    public static WebDriver getWEBDriver() {
        return DriverMap.get(Thread.currentThread().getId());
    }

    public static Connection getConnection() {
        return connectionMap.get(Thread.currentThread().getId());
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

    }
}
