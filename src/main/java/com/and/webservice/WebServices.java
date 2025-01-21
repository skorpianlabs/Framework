package com.and.webservice;

import io.cucumber.datatable.DataTable;
import com.and.utility.WebDriverProvider;
import static com.and.constant.CommonConstant.ENDED;
import static com.and.constant.CommonConstant.STARTED;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

public class WebServices extends BaseService {

    String description;

    public WebServices() {
        super(WebDriverProvider.getChromeDriver());
        setupPageFactory();
    }

    public void routeToLoginDetailsPage(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            Map<String, String> mapValues = data.get(0);
            String username = mapValues.get("username");
            String password = mapValues.get("password");

            webPageObjectFactory.getLoginPageService().clickOnAurenaLink();
            webPageObjectFactory.getLoginPageService().giveUserName(username);
            webPageObjectFactory.getLoginPageService().givePassword(password);
            webPageObjectFactory.getLoginPageService().clickLogin();
            Thread.sleep(3000);

        } catch (Exception e) {
            logger.error("An error occurred while navigating to the login page: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public void routeToTechnicianLobbyPage(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            for (Map<String, String> mapValues : data) {
                String page = mapValues.get("page");

                if ("lobby".equalsIgnoreCase(page)) {
                    String searchKey = mapValues.get("searchKey");

                    webPageObjectFactory.getHomePageService().clickNavigationLink();
                    webPageObjectFactory.getHomePageService().navigationSearchKey(searchKey);
                    webPageObjectFactory.getHomePageService().clickAviationMaintenanceTitleForRF();
                    break;
                }
            }

        } catch (Exception e) {
            logger.error("An error occurred while routing Technician Lobby page: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public void routeToMyAircraftTurnsPage(DataTable dataTable) {
        logger.info(STARTED + getCurrentMethodName());
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            Map<String, String> mapValues = data.get(0);
            String searchKey = mapValues.get("searchKey");
            String inputFieldText = mapValues.get("inputFieldText");

            webPageObjectFactory.getHomePageService().clickNavigationLink();
            webPageObjectFactory.getHomePageService().navigationSearchKey(searchKey);
            webPageObjectFactory.getHomePageService().clickAviationMaintenanceMenu();
            webPageObjectFactory.getAircraftTurnsPageService().clickFilterPanelButton();
            webPageObjectFactory.getAircraftTurnsPageService().clickTurnMenuItem("Arrival Flight");
            webPageObjectFactory.getAircraftTurnsPageService().enterTextIntoInputField(inputFieldText);
            webPageObjectFactory.getAircraftTurnsPageService().clickSelectedTurn();

        } catch (Exception e) {
            logger.error("An error occurred while routing to the Aircraft Turns page: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public void routeToTaskDetailsPage() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            webPageObjectFactory.getAircraftTurnDetailsPageService().captureAndClickRaisedFault();
            webPageObjectFactory.getAircraftTurnDetailsPageService().clickDetailsButton();
            Thread.sleep(2000);

        } catch (Exception e) {
            logger.error("An error occurred while selecting raised fault: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public void putTaskDetailsIntoAMap() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            webPageObjectFactory.getAircraftTurnDetailsPageService().putDescriptionValueToMap();

        } catch (Exception e) {
            logger.error("An error occurred while putting task details into map: {}", e.getMessage(), e);
        }
        logger.info(ENDED + getCurrentMethodName());
    }

    public String getTaskDetailsFromMap() {
        logger.info(STARTED + getCurrentMethodName());
        try {
            description = webPageObjectFactory.getAircraftTurnDetailsPageService().getDescriptionValueFromMap();
            logger.info(ENDED + getCurrentMethodName());
            return description;

        } catch (Exception e) {
            logger.error("An error occurred while retrieving task details from map: {}", e.getMessage(), e);
            return null;
        }

    }

}
