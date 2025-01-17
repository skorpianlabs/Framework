package serviceLayer;

import io.cucumber.datatable.DataTable;
import stepDefinition.WebDriverProvider;

import java.util.List;
import java.util.Map;

public class WebServices extends BaseService {

    public WebServices() {
        super(WebDriverProvider.getChromeDriver());
        setupPageFactory();
    }
    String description;

    public void routeToLoginDetailsPage(DataTable dataTable) {
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
            System.err.println("An error occurred while navigating to the login page: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void routeToTechnicianLobbyPage(DataTable dataTable) {
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            for (Map<String, String> mapValues : data) {
                // Check if the page is "lobby"
                String page = mapValues.get("page");

                if ("lobby".equalsIgnoreCase(page)) {
                    // If the page is "lobby", get the corresponding searchKey
                    String searchKey = mapValues.get("searchKey");

                    webPageObjectFactory.getHomePageService().clickNavigationLink();
                    webPageObjectFactory.getHomePageService().navigationSearchKey(searchKey);
                    webPageObjectFactory.getHomePageService().clickAviationMaintenanceTitleForRF();
                    break; // Exit the loop after processing the first matching "lobby" page
                }
            }

        } catch (Exception e) {
            System.err.println("An error occurred while routing Technician Lobby page: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void routeToMyAircraftTurnsPage(DataTable dataTable) {
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            Map<String, String> mapValues = data.get(0);
            String searchKey = mapValues.get("searchKey");
            String inputFieldText = mapValues.get("inputFieldText");

            webPageObjectFactory.getHomePageService().clickNavigationLink();
            webPageObjectFactory.getHomePageService().navigationSearchKey(searchKey);
            webPageObjectFactory.getHomePageService().clickAviationMaintenanceMenu();
            webPageObjectFactory.getAircraftTurnsPageService().clickFilterPanelButton();
            webPageObjectFactory.getAircraftTurnsPageService().clickArrivalFlight();
            webPageObjectFactory.getAircraftTurnsPageService().enterTextIntoInputField(inputFieldText);
            webPageObjectFactory.getAircraftTurnsPageService().clickButtonRelativeToSpan();

        } catch (Exception e) {
            System.err.println("An error occurred while routing to the Aircraft Turns page: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void routeToTaskDetailsPage(){
        try {
            webPageObjectFactory.getAircraftTurnDetailsPageService().captureAndClickRaisedFault();
            webPageObjectFactory.getAircraftTurnDetailsPageService().clickDetailsButton();
            Thread.sleep(2000);
        }
        catch (Exception e){
            System.err.println("An error occurred while selecting raised fault: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void putTaskDetailsIntoAMap(){
        try {
            webPageObjectFactory.getAircraftTurnDetailsPageService().putDescriptionValueToMap();
        }
        catch (Exception e){
            System.err.println("An error occurred while selecting raised fault: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public String getTaskDetailsFromMap(){
        try {
        description = webPageObjectFactory.getAircraftTurnDetailsPageService().getDescriptionValueFromMap();
        }
        catch (Exception e){
            System.err.println("An error occurred while selecting raised fault: " + e.getMessage());
            e.printStackTrace();
        }
        return description;
    }

}
