package utility;


import org.openqa.selenium.WebDriver;
import pagesWeb.*;

public class WebPageObjectFactory {
    private WebDriver driver;

    // Initialize page objects here
    private LoginPage loginPage;
    private RaiseFault raiseFault;
    private HomePage homePage;
    private AircraftTurnsPage aircraftTurnsPage;
    private AircraftTurnDetailsPage aircraftTurnDetailsPage;
    private TechnicianLobbyPage technicianLobbyPage;

    // Constructor to initialize the driver
    public WebPageObjectFactory(WebDriver driver) {
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
    public AircraftTurnDetailsPage getAircraftTurnDetailsPageService() {
        if (aircraftTurnDetailsPage == null) {
            aircraftTurnDetailsPage = new AircraftTurnDetailsPage(driver);
        }
        return aircraftTurnDetailsPage;
    }
    public TechnicianLobbyPage getTechnicianLobbyPagePageService() {
        if (technicianLobbyPage == null) {
            technicianLobbyPage = new TechnicianLobbyPage(driver);
        }
        return technicianLobbyPage;
    }
}

