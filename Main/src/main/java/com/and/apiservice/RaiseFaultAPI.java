package com.and.apiservice;

import com.and.PojoAPI.Fault;
import com.and.utility.PropertyManager;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;

import java.util.List;
import java.util.Map;

public class RaiseFaultAPI extends BaseAPI {

    String endpointRaiseFault = "main/ifsapplications/projection/v1/FlmAircraftTurnsDetailsHandling.svc/RaiseFaultVirtualSet";
    String endpointRaiseFaultService = "main/ifsapplications/projection/v1/FlmAircraftTurnsDetailsHandling.svc/RaiseFaultService";
    Response virtualResponse;
    Response serviceResponse;

    public RaiseFaultAPI() {
    }

    public Response sendRaiseFaultRequest(DataTable dataTable) {

        String accessToken = Token.generateBearerTokenWithPasswordCredential("alain", "alain");

        try {
            List<Map<String, String>> data = dataTable.asMaps(String.class, String.class);

            for (Map<String, String> faultDetailsMap : data) {
                try {
                    Fault fault = new Fault();
                    fault.setAircraftId(Integer.parseInt(faultDetailsMap.get("AircraftId")));
                    fault.setDescription(faultDetailsMap.get("Description"));
                    fault.setFailureTypeCode(faultDetailsMap.get("FailureTypeCode"));
                    fault.setFaultSeverityCode(faultDetailsMap.get("FaultSeverityCode"));
                    fault.setFaultSourceCode(faultDetailsMap.get("FaultSourceCode"));
                    fault.setFoundOnId(Integer.parseInt(faultDetailsMap.get("FoundOnId")));
                    fault.setLogbookReference(faultDetailsMap.get("LogbookReference"));
                    fault.setLogbookTypeCode(faultDetailsMap.get("LogbookTypeCode"));
                    fault.setPhaseOfFlightCode(faultDetailsMap.get("PhaseOfFlightCode"));
                    fault.setSubSystemId(Integer.parseInt(faultDetailsMap.get("SubSystemId")));

                    String jsonBody = convertObjectToJson(fault);

                    String baseUrl = PropertyManager.getInstance().getProperty("BASE_URL");

                    String virtualUrl = baseUrl + endpointRaiseFault;
                    String serviceUrl = baseUrl + endpointRaiseFaultService;

                    virtualResponse = Http.post(virtualUrl, jsonBody, accessToken);
                    serviceResponse = Http.post(serviceUrl, jsonBody, accessToken);

                } catch (Exception e) {
                    System.err.println("Error processing row: " + faultDetailsMap);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing DataTable: " + e.getMessage());
            e.printStackTrace();
        }

        return serviceResponse;
    }
}
