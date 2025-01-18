package APIServiceLayer;

import PojoAPI.APIConfigData;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseAPI {

    // Instance variables for configuration
    protected String tokenUrl;
    protected String clientId;
    protected String clientSecret;
    protected String username;
    protected String password;

    protected String BASE_URL = "https://ifsmxmm24r2dev3cmb.rnd.ifsdevworld.com/main/ifsapplications/projection/v1/";

    // Method to load configuration from the properties file and return the access token
    public String getAccesToken() {
        Properties properties = new Properties();
        APIConfigData configData = new APIConfigData();
        String accessToken = null;

        try (FileInputStream inputStream = new FileInputStream("src/test/java/apiconfig.properties")) {
            properties.load(inputStream);

            // Set values from the properties file into the configData POJO
            configData.setTokenUrl(properties.getProperty("tokenUrl"));
            configData.setClientId(properties.getProperty("clientId"));
            configData.setClientSecret(properties.getProperty("clientSecret"));
            configData.setUsername(properties.getProperty("username"));
            configData.setPassword(properties.getProperty("password"));

            // Assign the values to the instance variables
            this.tokenUrl = configData.getTokenUrl();
            this.clientId = configData.getClientId();
            this.clientSecret = configData.getClientSecret();
            this.username = configData.getUsername();
            this.password = configData.getPassword();

            // Prepare form parameters for the token request
            Map<String, String> formParams = new HashMap<>();
            formParams.put("grant_type", "password");
            formParams.put("client_id", this.clientId);
            formParams.put("client_secret", this.clientSecret);
            formParams.put("username", this.username);
            formParams.put("password", this.password);

            // Sending POST request to obtain the access token
            Response tokenResponse = given()
                    .contentType(ContentType.URLENC)
                    .formParams(formParams)  // Send the parameters for the token
                    .when()
                    .post(this.tokenUrl);  // Make the request to get the token

            // Extract the access token from the response
            accessToken = tokenResponse.jsonPath().getString("access_token");

            // Optionally, print the access token for debugging
            System.out.println("Access Token: " + accessToken);

        } catch (IOException e) {
            e.printStackTrace();  // Handle the error more gracefully, maybe throw a custom exception
        }

        // Return the access token (instead of the config data)
        return accessToken;
    }
    // Common method to convert any object to JSON
    public String convertObjectToJson(Object object) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            e.printStackTrace();
            return "{}";  // Return an empty JSON object in case of error
        }
    }
}
