package serviceLayer;

import io.cucumber.datatable.DataTable;
import org.openqa.selenium.WebDriver;
import stepDefinition.WebDriverProvider;

import java.util.List;
import java.util.Map;

public class WebServices extends BaseService {


    public WebServices() {
        super(WebDriverProvider.getChromeDriver());
        setupPageFactory();
    }
    public void initializeWebFactory() throws InterruptedException {
        setupPageFactory();
    }



    public void routeToLoginDetailsPage(DataTable dataTable) throws InterruptedException {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> mapValues = data.get(0);
        String username = mapValues.get("username");
        String password = mapValues.get("password");
        pageObjectFactory.getLoginPageService().clickOnAurenaLink();
        pageObjectFactory.getLoginPageService().give_userName(username);
        pageObjectFactory.getLoginPageService().give_password(password);
        pageObjectFactory.getLoginPageService().clickLogin();
        Thread.sleep(6000);

    }
    public void routeToTurnDetailsPage(DataTable dataTable) throws InterruptedException {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> mapValues = data.get(0);
        String searchKey = mapValues.get("searchKey");
        String inputFieldText = mapValues.get("inputFieldText");
        pageObjectFactory.getHomePageService().clickNavigationLink();
        pageObjectFactory.getHomePageService().navigationSearchKey(searchKey);
        pageObjectFactory.getHomePageService().clickAviationMaintenanceMenu();
        pageObjectFactory.getAircraftTurnsPageService().clickFilterPanelButton();
        pageObjectFactory.getAircraftTurnsPageService().clickArrivalFlight();
        pageObjectFactory.getAircraftTurnsPageService().enterTextIntoInputField(inputFieldText);
        pageObjectFactory.getAircraftTurnsPageService().clickButtonRelativeToSpan();
    }

}
