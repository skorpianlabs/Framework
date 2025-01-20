package com.and.and.apiservice;

import PojoAPI.Fault;
import io.cucumber.datatable.DataTable;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

import java.util.List;
import java.util.Map;

public class RaiseFaultAPI extends BaseAPI {

    private String accessToken;
    String endpointRaiseFault = "FlmAircraftTurnsDetailsHandling.svc/RaiseFaultVirtualSet";
    String endpointRaiseFaultService = "FlmAircraftTurnsDetailsHandling.svc/RaiseFaultService";
    Response virtualResponse;
    Response serviceResponse;

    public RaiseFaultAPI() {
    }

    public Response sendRaiseFaultRequest(DataTable dataTable) {
        Response finalResponse = null;
        accessToken = getAccesToken();

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

                    String virtualUrl = BASE_URL + endpointRaiseFault;
                    String serviceUrl = BASE_URL + endpointRaiseFaultService;

                    virtualResponse = given()
                            .contentType(ContentType.JSON)
                            .header("Authorization", "Bearer " + accessToken)
                            .body(jsonBody)
                            .when()
                            .post(virtualUrl);

                    serviceResponse = given()
                            .contentType(ContentType.JSON)
                            .header("Authorization", "Bearer " + accessToken)
                            .body(jsonBody)
                            .when()
                            .post(serviceUrl);

                    finalResponse = serviceResponse;
                } catch (Exception e) {
                    System.err.println("Error processing row: " + faultDetailsMap);
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.err.println("Error processing DataTable: " + e.getMessage());
            e.printStackTrace();
        }

        return finalResponse;
    }
}
