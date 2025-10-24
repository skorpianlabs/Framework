package com.and.utility.apiUtils;

import com.and.utility.PropertyManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import static com.and.utility.constants.CommonValues.ENDED;
import static com.and.utility.constants.CommonValues.STARTED;
import static io.restassured.RestAssured.given;

/**
 * Token class to generate bearer token for service user
 */
public class Token {
    static Logger logger = LogManager.getLogger(Token.class);
    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String AUTH_PATH ="/auth/realms/" + PropertyManager.getInstance().getProperty("AUTH_REALM") + "/protocol/openid-connect/token";

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
        logger.info(STARTED + "generateBearerTokenWithClientCredential");
        try {
            String clientId = PropertyManager.getInstance().getProperty("CLIENT_ID");
            String secretKey = PropertyManager.getInstance().getProperty("SECRET");
            String tokenUrl = PropertyManager.getInstance().getProperty("TOKEN_URL");

            Response response = given().auth().preemptive().basic(clientId, secretKey).contentType(CONTENT_TYPE)
                    .formParam("grant_type", "client_credentials")
                    .formParam("scope", "openid")
                    .when()
                    .post(tokenUrl);

            String accessToken = response.jsonPath().getString("access_token");
            logger.info(ENDED + "generateBearerTokenWithClientCredential");
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
     * This method can be used to check password expirations as this does not refresh the token
     *
     * @param username The username of the user
     * @param password The password of the user
     * @return Bearer token as a String
     * @throws RuntimeException if an error occurs during the token generation process
     *      */
    public static String generateBearerTokenWithPasswordCredential(String username, String password) {
        logger.info(STARTED + "generateBearerTokenWithPasswordCredential");
        try {
            String clientId = PropertyManager.getInstance().getProperty("CLIENT_ID");
            String secretKey = PropertyManager.getInstance().getProperty("SECRET");
            String tokenUrl = PropertyManager.getInstance().getProperty("TOKEN_URL");

            Response response = given()
                    .auth().preemptive().basic(clientId, secretKey)
                    .contentType(CONTENT_TYPE)
                    .formParam("grant_type", "password")
                    .formParam("username", username)
                    .formParam("password", password)
                    .formParam("scope", "openid")
                    .when()
                    .post(tokenUrl);

            String accessToken = response.jsonPath().getString("access_token");

            logger.info(ENDED + "generateBearerTokenWithPasswordCredential");

            return accessToken;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    /**
     * Authenticates using the default username and password from the environment configuration.
     */
    public static void authenticate() {
        String baseUrl = PropertyManager.getInstance().getProperty("webdriver.url");
        String clientId = PropertyManager.getInstance().getProperty("CLIENT_ID");
        String environmentType = PropertyManager.getInstance().getProperty("ENVIRONMENT");
        String defaultUsername = PropertyManager.getInstance().getProperty("DEV_ENV_AUTH_DEFAULT_USERNAME");
        String defaultPassword = PropertyManager.getInstance().getProperty("DEV_ENV_AUTH_DEFAULT_PASSWORD");
        String bntUsername = PropertyManager.getInstance().getProperty("BNT_ENV_AUTH_DEFAULT_USERNAME");
        String bntPassword = PropertyManager.getInstance().getProperty("BNT_ENV_AUTH_DEFAULT_PASSWORD");

        if(environmentType.equals("dev")) {
            getToken(defaultUsername,defaultPassword);
        }
        else {
            getToken(bntUsername,bntPassword );
        }
    }

    /**
     * Authenticates using the provided username. The username is also used as the password.
     *
     * @param username the username to authenticate with
     */
    public static void authenticate(String username) {
        getToken(username, username);
    }

    /**
     * Authenticates using the provided username and password.
     *
     * @param username the username to authenticate with
     * @param password the password to authenticate with
     */
    public static void authenticate(String username, String password) {
        getToken(username, password);
    }

    /**
     * Sends a request to the authentication endpoint to retrieve an access token.
     *
     * @param username the username to authenticate with
     * @param password the password to authenticate with
     * @throws RuntimeException if the authentication fails or the token is null/empty
     */
    private static void getToken(String username, String password) {
        String baseUrl = PropertyManager.getInstance().getProperty("webdriver.url");
        String clientId = PropertyManager.getInstance().getProperty("CLIENT_ID");
        Response response = RestAssured.given()
                .baseUri(baseUrl)
                .basePath(AUTH_PATH)
                .header("Content-Type", CONTENT_TYPE)
                .formParam("client_id", clientId)
                .formParam("username", username)
                .formParam("password", password)
                .formParam("grant_type", "password")
                .post();

        if (response.statusCode() == 200) {
            String token = response.jsonPath().getString("access_token");
            if (token == null || token.isEmpty()) {
                throw new RuntimeException("Authentication failed: Token is null or empty.");
            }
            tokenHolder.set(token);
            logger.info("Authentication successful. Token obtained.");
        } else {
            logger.error("Authentication failed: {}", response.statusLine());
            throw new RuntimeException("Failed to authenticate: " + response.statusLine());
        }
    }

    /**
     * Retrieves the current access token. If no token is available, it authenticates using the default credentials.
     *
     * @return the current access token
     */
    public static String getToken() {
        if (tokenHolder.get() == null) {
            authenticate();
        }
        return tokenHolder.get();
    }

    /**
     * Clears the current access token from the ThreadLocal storage.
     */
    public static void clearToken() {
        tokenHolder.remove();
        logger.info("Token cleared from ThreadLocal.");
    }
}
