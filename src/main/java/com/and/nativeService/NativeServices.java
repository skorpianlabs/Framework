package com.and.nativeService;

import io.cucumber.datatable.DataTable;
import org.openqa.selenium.By;

import java.util.List;
import java.util.Map;

import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

public class NativeServices extends NativeBaseService {

    public NativeServices() {
        setupNativePageFactory();
    }

    public void routeToMyAircraftTurnsPage(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            Map<String, String> mapValues = data.get(0);
            String searchKey = mapValues.get("searchKey");
            String turnName = mapValues.get("inputFieldText");

            nativePageObjectFactory.getNativeTechnicianLobbyPagePageService().clickMyAircraftTurnsLobbyIcon();
            nativePageObjectFactory.getNativeBaseService().search(turnName);
            nativePageObjectFactory.getNativeBaseService().clickElement(By.id(turnName));

        } catch (Exception e) {
            logger.error("An error occurred while routing to the Aircraft Turns page: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public void routeToLoginDetailsPage(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            Map<String, String> mapValues = data.get(0);
            String username = mapValues.get("username");
            String password = mapValues.get("password");

            nativePageObjectFactory.getNativeLoginPageService().clickOnLoginButton();

        } catch (Exception e) {
            logger.error("An error occurred while navigating to the login page: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

}
