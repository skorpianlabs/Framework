package stepDefinition;

import APIServiceLayer.RaiseFaultAPI;
import io.restassured.response.Response;
import serviceLayer.RaiseFaultWebService;
import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import serviceLayer.ChannelDecider;
import serviceLayer.WebServices;
import static org.junit.jupiter.api.Assertions.*;

public class DeferFaultStepDefinition {

    private WebDriver driver;

    @Inject
    protected WebServices webservices;

    @Inject
    protected ChannelDecider channelDecider;

    @Inject
    protected RaiseFaultWebService raiseFaultWebService;

    @Inject
    protected RaiseFaultAPI raiseFaultAPI;

    private String channel;


    @Given("Technician Raise a Fault")
    public void technicianRaiseAFault(DataTable dataTable) throws InterruptedException {

        channel= channelDecider.findPlatform(dataTable);
        if (channel.equals("web"))  {
            raiseFaultWebService.raiseAFault(dataTable);
            assertEquals("ABC", raiseFaultWebService.assertRaisedFaultFromTurn());
        }
        if (channel.equals("mobile")){

            System.out.println("For mobile implementation");
        }
        else{
            Response response = raiseFaultAPI.sendRaiseFaultRequest(dataTable);
        }


    }

    @Given("Technician Raise a Fault in mobile")
    public void technicianRaiseAFaultInMobile(DataTable dataTable) throws InterruptedException {
        // You can use the injected 'driver' instead of initializing here

    }

    @Given("Technician is logged in")
    public void technicianIsLoggedIn(DataTable dataTable) throws InterruptedException  {

        channel= channelDecider.findPlatform(dataTable);
        if (channel.equals("web"))  {
            webservices.routeToLoginDetailsPage(dataTable);
        }
        if (channel.equals("mobile")){

            System.out.println("For mobile implementation");
        }
        else{
            System.out.println("use API path");
        }
    }
}
