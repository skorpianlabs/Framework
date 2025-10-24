package com.and.utility.apiUtils;

import com.and.utility.PropertyManager;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

import static io.restassured.RestAssured.given;

/**
 * Http class to send HTTP requests using Rest Assured
 */
public class Http {
    private static final Logger logger = LoggerFactory.getLogger(Http.class);

    // HTTP method constants
    private static final String POST = "POST";
    private static final String GET = "GET";
    private static final String DELETE = "DELETE";
    private static final String PATCH = "PATCH";

    /**
     * Sends a GET request to the specified URL.
     *
     * @param url the URL to send the GET request to
     * @return the HTTP response
     */
    public static Response get(String url, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true")
                .get(url)
                .then()
                .extract()
                .response();
    }

    /**
     * Sends a POST request to the specified URL with the given payload.
     *
     * @param url     the URL to send the POST request to
     * @param payload the JSON payload to include in the POST request
     * @return the HTTP response
     */
    public static Response post(String url, Object payload, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true")
                .body(payload)
                .post(url)
                .then()
                .extract()
                .response();
    }

    /**
     * Sends a POST request to the specified URL with the given payload and extra headers.
     *
     * @param url          the URL to send the POST request to
     * @param payload      the JSON payload to include in the POST request
     * @param extraHeaders the extra headers to include in the POST request
     * @return the HTTP response
     */
    public static Response post(String url, String payload, Map<String, String> extraHeaders, String token) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/vnd.ifs.amapi.v2+json")
                .header("Accept", "application/vnd.ifs.amapi.v2+json")
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true")
                .body(payload);

        if (extraHeaders != null && !extraHeaders.isEmpty()) {
            extraHeaders.forEach(requestSpec::header);
        }

        return requestSpec.post(url)
                .then()
                .extract()
                .response();

    }

    /**
     * Sends a PUT request to the specified URL with the given payload.
     *
     * @param url     the URL to send the PUT request to
     * @param payload the JSON payload to include in the PUT request
     * @return the HTTP response
     */
    public static Response put(String url, String payload, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true")
                .body(payload)
                .put(url)
                .then()
                .extract()
                .response();
    }

    /**
     * Sends a PATCH request to the specified URL with the given JSON payload.
     *
     * @param url     the URL to send the PATCH request to
     * @param payload the JSON payload to include in the PATCH request
     * @return the HTTP response
     */
    public static Response patch(String url, Object payload, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true")
                .body(payload)
                .patch(url)
                .then()
                .extract()
                .response();
    }

    /**
     * Sends a DELETE request to the specified URL.
     *
     * @param url     the URL to send the DELETE request to
     * @param token   the authorization token
     * @return the HTTP response
     */
    public static Response delete(String url, String token) {
        return RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true")
                .delete(url)
                .then()
                .extract()
                .response();
    }
    /**
     * Sends a POST request to the specified URL with the given request body.
     *
     * @param url  the URL to send the request to
     * @param body the request body
     * @return the HTTP response
     * @throws IllegalArgumentException if the URL is null or empty
     */
    public static Response post(String url, Object body) {
        return executeRequest(POST, url, body);
    }

    /**
     * Sends a GET request to the specified URL.
     *
     * @param url the URL to send the request to
     * @return the HTTP response
     * @throws IllegalArgumentException if the URL is null or empty
     */
    public static Response get(String url) {
        return executeRequest(GET, url, null);
    }

    /**
     * Sends a DELETE request to the specified URL.
     *
     * @param url the URL to send the request to
     * @return the HTTP response
     * @throws IllegalArgumentException if the URL is null or empty
     */
    public static Response delete(String url) {
        return executeRequest(DELETE, url, null);
    }

    /**
     * Sends a PATCH request to the specified URL with the given request body.
     *
     * @param url  the URL to send the request to
     * @param body the request body
     * @return the HTTP response
     * @throws IllegalArgumentException if the URL is null or empty
     */
    public static Response patch(String url, Object body) {
        return executeRequest(PATCH, url, body);
    }

    /**
     * Executes an HTTP request with the specified method, URL, and optional body.
     *
     * @param method the HTTP method (e.g., GET, POST, DELETE, PATCH)
     * @param url    the URL to send the request to
     * @param body   the request body (optional)
     * @return the HTTP response
     * @throws IllegalArgumentException if the URL is null or empty
     * @throws RuntimeException         if the request fails
     */
    private static Response executeRequest(String method, String url, Object body) {
        if (url == null || url.isEmpty()) {
            throw new IllegalArgumentException("URL must not be null or empty");
        }

        try {
            var request = given()
                    .header("Authorization", "Bearer " + Token.getToken())
                    .header("X-IFS-Maintenix-Trusted-Secret", "true")
                    .header("X-IFS-Maintenix-Trusted-Request-Origin", "true")
                    .contentType("application/json");

            if (body != null) {
                request.body(body);
                logger.debug("Request Body: {}", body);
            }

            url = PropertyManager.getInstance().getProperty("AUTH_REALM") + url;

            Response response = switch (method) {
                case POST -> request.post(url);
                case GET -> request.get(url);
                case DELETE -> request.delete(url);
                case PATCH -> request.patch(url);
                default -> throw new IllegalArgumentException("Unsupported HTTP method: " + method);
            };

            logger.info("HTTP {} request to {} completed with status code: {}", method, url, response.statusCode());
            logger.debug("Response: {}", response.asString());

            return response;
        } catch (Exception e) {
            logger.error("HTTP {} request to {} failed: {}", method, url, e.getMessage());
            throw new RuntimeException("HTTP request failed for method: " + method + ", URL: " + url, e);
        }
    }
}
