package businessObjects;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import org.openqa.selenium.WebDriver;
import serviceLayer.BaseService;
import serviceLayer.WebServices;
import stepDefinition.WebDriverProvider;
import utility.PageObjectFactory;

import java.util.List;
import java.util.Map;

public class RaiseFaultBO extends BaseService {

    public RaiseFaultBO() {
        super(WebDriverProvider.getChromeDriver());
        setupPageFactory();

    }
    WebServices webServices = new WebServices();

    public void raiseAFaultFromATurn(DataTable dataTable) throws InterruptedException {

        webServices.routeToTurnDetailsPage(dataTable);
        pageObjectFactory.getRaiseFaultService().clickButtonRaiseFault();
        pageObjectFactory.getRaiseFaultService().clickButtonRelativeToLabelSystem();
        pageObjectFactory.getRaiseFaultService().captureSelectedValueSystem();
        pageObjectFactory.getRaiseFaultService().clickButtonRelativeToFaultSource();
        pageObjectFactory.getRaiseFaultService().captureSelectedValueFaultSource();
        pageObjectFactory.getRaiseFaultService(). enterDescription("DESC01");
        pageObjectFactory.getRaiseFaultService().clickButtonRelativeToFaultSeverity();
        pageObjectFactory.getRaiseFaultService().captureSelectedValueFaultSeverity();
        pageObjectFactory.getRaiseFaultService().clickButtonOK();
    }
    public void  raiseAFault(DataTable dataTable) throws InterruptedException {

        List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        Map<String, String> mapValues = data.get(0);
        String page = mapValues.get("page");

        if (page.equals("turn")) {
            raiseAFaultFromATurn(dataTable);
        }
        if (page.equals("lobby")) {
            // raiseAFaultFromLobby() ;
        }


    }

}
