package serviceLayer;

import io.cucumber.datatable.DataTable;
import stepDefinition.WebDriverProvider;

import java.util.List;
import java.util.Map;

public class RaiseFaultWebService extends BaseService {

    public RaiseFaultWebService() {
        super(WebDriverProvider.getChromeDriver());
        setupPageFactory();

    }
    String description;
    WebServices webServices = new WebServices();

    public void raiseAFaultFromATurn(DataTable dataTable) {
        try {
            webServices.routeToMyAircraftTurnsPage(dataTable);
            webPageObjectFactory.getRaiseFaultService().clickButtonRaiseFault();
            webPageObjectFactory.getRaiseFaultService().clickButtonRelativeToLabelSystem();
            webPageObjectFactory.getRaiseFaultService().captureSelectedValueSystem();
            webPageObjectFactory.getRaiseFaultService().clickButtonRelativeToFaultSource();
            webPageObjectFactory.getRaiseFaultService().captureSelectedValueFaultSource();
            webPageObjectFactory.getRaiseFaultService().enterDescription("DESC01");
            webPageObjectFactory.getRaiseFaultService().clickButtonRelativeToFaultSeverity();
            webPageObjectFactory.getRaiseFaultService().captureSelectedValueFaultSeverity();
            webPageObjectFactory.getRaiseFaultService().clickButtonOK();
            Thread.sleep(2000);
        } catch (Exception e) {
            System.out.println("Error rasie fault from a Turn: " + e.getMessage());
        }
    }

    public void raiseAFaultFromLobby(DataTable dataTable) {
        try {
            webServices.routeToTechnicianLobbyPage(dataTable);
            webPageObjectFactory.getTechnicianLobbyPagePageService().clickRaiseFault();
            webPageObjectFactory.getRaiseFaultService().clickAircraftInput();
            webPageObjectFactory.getRaiseFaultService().clickIFSADText();
            webPageObjectFactory.getRaiseFaultService().clickButtonRelativeToLabelSystem();
            webPageObjectFactory.getRaiseFaultService().captureSelectedValueSystem();
            webPageObjectFactory.getRaiseFaultService().clickButtonRelativeToFaultSource();
            webPageObjectFactory.getRaiseFaultService().captureSelectedValueFaultSource();
            webPageObjectFactory.getRaiseFaultService().enterDescription("DESC01");
            webPageObjectFactory.getRaiseFaultService().clickButtonRelativeToFaultSeverity();
            webPageObjectFactory.getRaiseFaultService().captureSelectedValueFaultSeverity();
            webPageObjectFactory.getRaiseFaultService().clickButtonOK();
        } catch (Exception e) {
            System.out.println("Error rasie fault from Lobby: " + e.getMessage());
        }
    }

    public void raiseAFault(DataTable dataTable) {
        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
            for (Map<String, String> row : data) {
                String page = row.get("page");

                if (page.equals("turn")) {
                    raiseAFaultFromATurn(dataTable);
                }
                if (page.equals("lobby")) {
                    raiseAFaultFromLobby(dataTable);
                }
            }
        } catch (Exception e) {
            System.out.println("Error rasie fault from selector: " + e.getMessage());
        }
    }

    public String assertRaisedFaultFromTurn() {
        try {
            webServices.routeToTaskDetailsPage();
            webServices.putTaskDetailsIntoAMap();
            description = webServices.getTaskDetailsFromMap();
            return description;
        } catch (Exception e) {
            System.out.println("Error asserting the raised fault: " + e.getMessage());
            return null;  // You could return null or handle the error as per your requirements
        }
    }


}
