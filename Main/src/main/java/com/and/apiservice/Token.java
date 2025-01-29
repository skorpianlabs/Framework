package com.and.apiservice;

import com.and.utility.PropertyManager;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

/**
 * Token class to generate bearer token for service user
 */
public class Token {

    /**
     * Generates a Bearer token using the client credentials grant type.
     *
     * This method retrieves the client ID, secret key, and token URL from the
     * application properties and uses them to request an access token.
     *
     * @return Bearer token as a String
     * @throws RuntimeException if an error occurs during the token generation process
     */
    public static String generateBearerTokenWithClientCredential() {
        try {
            String clientId = PropertyManager.getInstance().getProperty("CLIENT_ID");
            String secretKey = PropertyManager.getInstance().getProperty("SECRET");
            String tokenUrl = PropertyManager.getInstance().getProperty("TOKEN_URL");

            Response response = given().auth().preemptive().basic(clientId, secretKey).contentType("application/x-www-form-urlencoded")
                    .formParam("grant_type", "client_credentials")
                    .formParam("scope", "openid")
                    .when()
                    .post(tokenUrl);

            String accessToken = response.jsonPath().getString("access_token");

            System.out.println("Access Token: " + accessToken);

            return accessToken;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Generates a Bearer token using the password grant type.
     *
     * This method retrieves the client ID, secret key, and token URL from the
     * application properties and uses them along with the provided username
     * and password to request an access token.
     *
     * @param username The username of the user
     * @param password The password of the user
     * @return Bearer token as a String
     * @throws RuntimeException if an error occurs during the token generation process
     */
    public static String generateBearerTokenWithPasswordCredential(String username, String password) {
        try {
            String clientId = PropertyManager.getInstance().getProperty("CLIENT_ID");
            String secretKey = PropertyManager.getInstance().getProperty("SECRET");
            String tokenUrl = PropertyManager.getInstance().getProperty("TOKEN_URL");

            Response response = given()
                    .auth().preemptive().basic(clientId, secretKey)
                    .contentType("application/x-www-form-urlencoded")
                    .formParam("grant_type", "password")
                    .formParam("username", username)
                    .formParam("password", password)
                    .formParam("scope", "openid")
                    .when()
                    .post(tokenUrl);

            String accessToken = response.jsonPath().getString("access_token");

            System.out.println("Access Token: " + accessToken);

            return accessToken;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
