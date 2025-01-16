package stepDefinition;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import java.util.HashMap;
import java.util.Map;

public class WebDriverProvider {

    // Map to store the WebDriver instances for each thread
    private static final Map<Long, WebDriver> chromeDriverMap = new HashMap<>();
    private static final Map<Long, WebDriver> mobileDriverMap = new HashMap<>();
  //  private static Injector injector;

    // @Before hook to initialize both ChromeDriver and EdgeDriver for each thread
    @Before
    public static void initializeDriver() {

    //    injector = Guice.createInjector(new PageObjectModule());
        long threadId = Thread.currentThread().getId();

        // Initialize ChromeDriver if not already initialized
        if (!chromeDriverMap.containsKey(threadId)) {
            WebDriver chromeDriver = new ChromeDriver();
            chromeDriver.get("https://ifsmxmm24r2dev3cmb.rnd.ifsdevworld.com/");
            chromeDriver.manage().window().maximize();
            chromeDriverMap.put(threadId, chromeDriver);
        }

        // Initialize EdgeDriver if not already initialized
        if (!mobileDriverMap.containsKey(threadId)) {
            WebDriver edgeDriver = new EdgeDriver();
            edgeDriver.get("https://ifsmxmm24r2dev3cmb.rnd.ifsdevworld.com/");
            edgeDriver.manage().window().maximize();
            mobileDriverMap.put(threadId, edgeDriver);
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

    // @After hook to quit both drivers after each scenario
    @After
    public static void quitDriver(Scenario scenario) {
        long threadId = Thread.currentThread().getId();

        // Quit ChromeDriver if it exists
        WebDriver chromeDriver = chromeDriverMap.get(threadId);
        if (chromeDriver != null) {
            if(scenario.isFailed()){
                final byte[] scrnShot = ((TakesScreenshot)chromeDriver).getScreenshotAs(OutputType.BYTES);
                scenario.attach(scrnShot, "image/png", scenario.getName());
            }
            chromeDriver.quit();
            chromeDriverMap.remove(threadId);
        }

        // Quit EdgeDriver if it exists
        WebDriver edgeDriver = mobileDriverMap.get(threadId);
        if (edgeDriver != null) {
            edgeDriver.quit();
            mobileDriverMap.remove(threadId);
        }
    }
}
