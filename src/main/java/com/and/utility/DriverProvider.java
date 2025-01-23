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

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class DriverProvider {

    // Map to store the WebDriver instances for each thread
    private static final Map<Long, WebDriver> chromeDriverMap = new HashMap<>();
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
        if (!chromeDriverMap.containsKey(threadId)) {
            WebDriver chromeDriver = new ChromeDriver();
            chromeDriver.get("https://ifsmxmm24r2dev3cmb.rnd.ifsdevworld.com/");
            chromeDriver.manage().window().maximize();
            chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
            chromeDriverMap.put(threadId, chromeDriver);
        }

        if (!iOSDriverMap.containsKey(threadId)) {
            IOSDriver iOSDriver = new IOSDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities.getCapabilities());

            iOSDriverMap.put(threadId, iOSDriver);

        }

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
    public static WebDriver getChromeDriver() {
        return chromeDriverMap.get(Thread.currentThread().getId());
    }

    // Method to get EdgeDriver for the current thread
    public static WebDriver getEdgeDriver() {
        return mobileDriverMap.get(Thread.currentThread().getId());
    }

    public static Connection getConnection() {
        return connectionMap.get(Thread.currentThread().getId());
    }

    // @After hook to quit both drivers after each scenario
    @After
    public static void quitDriver(Scenario scenario) {
        long threadId = Thread.currentThread().getId();

        // Quit ChromeDriver if it exists
        WebDriver chromeDriver = chromeDriverMap.get(threadId);
        if (chromeDriver != null) {
            if (scenario.isFailed()) {
                final byte[] scrnShot = ((TakesScreenshot) chromeDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(scrnShot, "image/png", scenario.getName());
            }
            chromeDriver.quit();
            chromeDriverMap.remove(threadId);
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

        // Quit EdgeDriver if it exists
        WebDriver edgeDriver = mobileDriverMap.get(threadId);
        if (edgeDriver != null) {
            edgeDriver.quit();
            mobileDriverMap.remove(threadId);
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
