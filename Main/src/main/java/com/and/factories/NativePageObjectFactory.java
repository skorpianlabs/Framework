package com.and.factories;

import com.and.nativePages.*;
import io.appium.java_client.ios.IOSDriver;

public class NativePageObjectFactory {

    private final IOSDriver driver;
    private NativeLoginPage loginPage;
    private NativeRaiseFaultPage raiseFault;
    private NativeHomePage homePage;
    private NativeAircraftTurnsPage aircraftTurnsPage;
    private NativeAircraftTurnDetailsPage aircraftTurnDetailsPage;
    private NativeTechnicianLobbyPage technicianLobbyPage;
    private NativeBasePage nativeBasePage;

    public NativePageObjectFactory(IOSDriver driver) {
        this.driver = driver;
    }

    public NativeRaiseFaultPage getNativeRaiseFaultService() {
        if (raiseFault == null) {
            raiseFault = new NativeRaiseFaultPage(driver);
        }
        return raiseFault;
    }

    public NativeLoginPage getNativeLoginPageService() {
        if (loginPage == null) {
            loginPage = new NativeLoginPage(driver);
        }
        return loginPage;
    }

    public NativeHomePage getNativeHomePageService() {
        if (homePage == null) {
            homePage = new NativeHomePage(driver);
        }
        return homePage;
    }

    public NativeAircraftTurnsPage getNativeAircraftTurnsPageService() {
        if (aircraftTurnsPage == null) {
            aircraftTurnsPage = new NativeAircraftTurnsPage(driver);
        }
        return aircraftTurnsPage;
    }

    public NativeAircraftTurnDetailsPage getNativeAircraftTurnDetailsPageService() {
        if (aircraftTurnDetailsPage == null) {
            aircraftTurnDetailsPage = new NativeAircraftTurnDetailsPage(driver);
        }
        return aircraftTurnDetailsPage;
    }

    public NativeTechnicianLobbyPage getNativeTechnicianLobbyPagePageService() {
        if (technicianLobbyPage == null) {
            technicianLobbyPage = new NativeTechnicianLobbyPage(driver);
        }
        return technicianLobbyPage;
    }

    public NativeBasePage getNativeBaseService() {
        if (nativeBasePage == null) {
            nativeBasePage = new NativeBasePage(driver);
        }
        return nativeBasePage;
    }


}
