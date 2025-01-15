package utility;


import org.openqa.selenium.WebDriver;
import pagesWeb.*;

public class PageObjectFactory  {
    private WebDriver driver;

    // Initialize page objects here
    private LoginPage loginPage;
    private RaiseFault raiseFault;
    private LandingPage landingPage;
    private HomePage homePage;
    private AircraftTurnsPage aircraftTurnsPage;

    // Constructor to initialize the driver
    public PageObjectFactory(WebDriver driver) {
        this.driver = driver;

    }
    public RaiseFault getRaiseFaultService() {
        if (raiseFault == null) {
            raiseFault = new RaiseFault(driver);
        }
        return raiseFault;
    }
    public LoginPage getLoginPageService() {
        if (loginPage == null) {
            loginPage = new LoginPage(driver);
        }
        return loginPage;
    }public HomePage getHomePageService() {
        if (homePage == null) {
            homePage = new HomePage(driver);
        }
        return homePage;
    }
    public AircraftTurnsPage getAircraftTurnsPageService() {
        if (aircraftTurnsPage == null) {
            aircraftTurnsPage = new AircraftTurnsPage(driver);
        }
        return aircraftTurnsPage;
    }
}

