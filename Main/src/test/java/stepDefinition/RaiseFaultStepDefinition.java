package stepDefinition;

import com.and.apiservice.RaiseFaultAPI;
import com.and.nativeService.NativeServices;
import com.and.nativeService.RaiseFaultNativeService;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import com.and.webservice.RaiseFaultWebService;
import com.google.inject.Inject;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import org.openqa.selenium.WebDriver;
import com.and.webservice.ChannelDecider;
import com.and.webservice.WebServices;
import static org.junit.jupiter.api.Assertions.*;

public class RaiseFaultStepDefinition {

    private WebDriver driver;

    @Inject
    protected WebServices webservices;

    @Inject
    protected NativeServices nativeServices;

    @Inject
    protected ChannelDecider channelDecider;

    @Inject
    protected RaiseFaultWebService raiseFaultWebService;

    @Inject
    protected RaiseFaultAPI raiseFaultAPI;

    @Inject
    protected RaiseFaultNativeService raiseFaultNativeService;

    private String channel;

    private String description;


    @Given("Technician Raise a Fault")
    public void technicianRaiseAFault(DataTable dataTable) throws InterruptedException {

        channel= channelDecider.findPlatform(dataTable);
        if (channel.equals("web"))  {
            raiseFaultWebService.raiseAFault(dataTable);
        }
        if (channel.equals("mobile")){
            raiseFaultNativeService.raiseAFault(dataTable);
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
            nativeServices.routeToLoginDetailsPage(dataTable);
        }
        else{
            System.out.println("use API path");
        }
    }

    @Then("Technician Verify The Raised Fault")
    public void technicianVerifyTheRaisedFault(DataTable dataTable) {
        channel= channelDecider.findPlatform(dataTable);
        if (channel.equals("web"))  {
            description = raiseFaultWebService.routeFaultDetails(dataTable);
            assertEquals("DESC01", description);
        }
        else if (channel.equals("mobile")){

            System.out.println("For mobile implementation");
        }
        else{
            System.out.println("use API path");
        }

    }
}
