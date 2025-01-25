package com.and.utility;


import com.and.pagesweb.*;
import org.openqa.selenium.WebDriver;

public class WebPageObjectFactory {
    private WebDriver driver;

    // Initialize page objects here
    private LoginPage loginPage;
    private RaiseFault raiseFault;
    private HomePage homePage;
    private AircraftTurnsPage aircraftTurnsPage;
    private AircraftTurnDetailsPage aircraftTurnDetailsPage;
    private TechnicianLobbyPage technicianLobbyPage;
    private DBCallExecuter dbCallExecuter;
    private FaultSearchPage faultSearchPage;
    private FaultDetailsPage faultDetailsPage;

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
    public DBCallExecuter getDbCallExecuterService() {
        if (dbCallExecuter == null) {
            dbCallExecuter = new DBCallExecuter();
        }
        return dbCallExecuter;
    }
    public  FaultSearchPage getfaultSearchPageService(){

        if (faultSearchPage == null) {
            faultSearchPage = new FaultSearchPage(driver);
        }
        return faultSearchPage;
    }
    public  FaultDetailsPage getFaultDetailsPageService(){

        if (faultDetailsPage == null) {
            faultDetailsPage = new FaultDetailsPage(driver);
        }
        return faultDetailsPage;
    }
}

