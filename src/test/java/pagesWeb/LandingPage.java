package pagesWeb;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;

public class LandingPage extends BasePage  {

    private WebDriver driver;

    public LandingPage(WebDriver driver) {
        this.driver = driver;
    }
    public String ifsCloud = "//*[@id=\"aurenacont\"]/div[2]/div";

    public void  load_LandingPage() throws InterruptedException {

        driver.findElement(By.xpath(ifsCloud)).click();

    }
    public void navigateToLandingPage() {
        try {
           // WebDriverManager.chromedriver().setup();
            // Add explicit wait if needed
            // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(GlobalVariable.gl_defaultTimeout));
            // wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

            System.out.println("Page loaded");
        } catch (WebDriverException e) {
            System.out.println("Fail to access " + "landingpage" + " " + e.toString());
        }
    }
}
