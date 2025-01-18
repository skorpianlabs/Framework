package pagesWeb;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class RaiseFaultAPI {


    // Define method to send the POST request with authentication token
    public static void sendRaiseFaultRequest() {
        // Set the token URL and credentials for authentication
        String tokenUrl = "https://ifsmxmm24r2bntcmb.rnd.ifsdevworld.com/auth/realms/ifsmxmm24r2bntcmb/protocol/openid-connect/token";
        String clientId = "IFS_maintenix_odata";
        String clientSecret = "cD00gHxKIBr0BGrt2DMW";
        String username = "will";
        String password = "will";

        // Prepare the body for token request
        Map<String, String> formParams = new HashMap<>();
        formParams.put("grant_type", "password");
        formParams.put("client_id", clientId);
        formParams.put("client_secret", clientSecret);
        formParams.put("username", username);
        formParams.put("password", password);

        // Sending POST request to obtain the access token
        Response tokenResponse = given()
                .contentType(ContentType.URLENC)
                .formParams(formParams)  // Send the parameters for the token
                .when()
                .post(tokenUrl);  // Make the request to get the token

        // Extract the access token from the response
        String accessToken = tokenResponse.jsonPath().getString("access_token");
        System.out.println("Access Token: " + accessToken);

        // Now use this token to make the RaiseFault request

        // Set the base URL for RaiseFault request
        String url = "https://ifsmxmm24r2bntcmb.rnd.ifsdevworld.com/main/ifsapplications/projection/v1/FlmAircraftTurnsDetailsHandling.svc/RaiseFaultVirtualSet";


        // Create the body (JSON data) for the RaiseFault request
        String jsonBody = "{\n" +
                "  \"AircraftId\": 1090,\n" +  // Integer value without quotes
                "  \"SubSystemId\": 475,\n" +    // Integer value without quotes
                "  \"FaultSourceCode\": \"AUTH\",\n" +
                "  \"Description\": \"testAPI\",\n" +
                "  \"FoundOnId\": 3878,\n" +  // Integer value without quotes
                "  \"Source\": \"FLIGHT\",\n" +
                "  \"FaultSeverityCode\": \"MEL\"\n" +
                "}";

        // Sending POST request with the authorization token
        Response raiseFaultResponse = given()
                .contentType(ContentType.JSON)  // Specify that we are sending JSON
                .header("Authorization", "Bearer " + accessToken)  // Set the Authorization header with the token
                .body(jsonBody)  // Add the request body
                .when()
                .post(url);  // Send the POST request to the RaiseFault API

        // Print the response status and body for RaiseFault request
        System.out.println("RaiseFault Response Status Code: " + raiseFaultResponse.getStatusCode());
        System.out.println("RaiseFault Response Body: " + raiseFaultResponse.getBody().asString());

        // Set the base URL for RaiseFault SO
        String urlSO = "https://ifsmxmm24r2bntcmb.rnd.ifsdevworld.com/main/ifsapplications/projection/v1/FlmAircraftTurnsDetailsHandling.svc/RaiseFaultService";

        String jsonBodyServiceObject = "{\n" +
                "  \"AircraftId\": 1090,\n" +  // Integer value without quotes\n" +
                "  \"SubSystemId\": 475,\n" +    // Integer value without quotes\n" +
                "  \"FaultSourceCode\": \"AUTH\",\n" +
                "  \"Description\": \"testAPI\",\n" +
                "  \"FoundOnId\": 3878,\n" +  // Integer value without quotes\n" +
                "  \"Source\": \"FLIGHT\",\n" +
                "  \"FaultSeverityCode\": \"MEL\",\n" +  // Existing field\n" +
                "  \"LogbookTypeCode\": null,\n" +  // New field added\n" +
                "  \"LogbookReference\": null,\n" +  // New field added\n" +
                "  \"FaultCode\": null\n" +  // New field added\n" +
                "}";
        // Sending POST request with the authorization token for service object
        Response raiseFaultResponseSO = given()
                .contentType(ContentType.JSON)  // Specify that we are sending JSON
                .header("Authorization", "Bearer " + accessToken)  // Set the Authorization header with the token
                .body(jsonBodyServiceObject)  // Add the request body
                .when()
                .post(urlSO);  // Send the POST request to the RaiseFault API

        // Print the response status and body for RaiseFault request
        System.out.println("RaiseFault Response Status Code: " + raiseFaultResponseSO.getStatusCode());
        System.out.println("RaiseFault Response Body: " + raiseFaultResponseSO.getBody().asString());

    }
}

