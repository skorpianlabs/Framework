package com.and.utility.apiUtils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

/**
 * Http class to send HTTP requests using Rest Assured
 */
public class Http {

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
     * Sends a GET request to the specified URL.
     *
     * @param extraHeaders the extra headers to include in the GET request
     * @param url          the URL to send the GET request to
     * @return the HTTP response
     */
    public static Response get(String url, Map<String, String> extraHeaders, String token) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true");

        if (extraHeaders != null && !extraHeaders.isEmpty()) {
            extraHeaders.forEach(requestSpec::header);
        }

        return requestSpec
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
     * Sends a PUT request to the specified URL with the given payload.
     *
     * @param url          the URL to send the PUT request to
     * @param payload      the JSON payload to include in the PUT request
     * @param extraHeaders the extra headers to include in the PUT request
     * @return the HTTP response
     */
    public static Response put(String url, String payload, Map<String, String> extraHeaders, String token) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true");

        if (extraHeaders != null && !extraHeaders.isEmpty()) {
            extraHeaders.forEach(requestSpec::header);
        }

        return requestSpec
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
     * Sends a PATCH request to the specified URL with the given JSON payload.
     *
     * @param url          the URL to send the PATCH request to
     * @param payload      the JSON payload to include in the PATCH request
     * @param extraHeaders the extra headers to include in the PATCH request
     * @return the HTTP response
     */
    public static Response patch(String url, Object payload, Map<String, String> extraHeaders, String token) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json; charset=utf-8")
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true");

        if (extraHeaders != null && !extraHeaders.isEmpty()) {
            extraHeaders.forEach(requestSpec::header);
        }

        return requestSpec
                .body(payload)
                .patch(url)
                .then()
                .extract()
                .response();
    }


    /**
     * Sends a DELETE request to the specified URL.
     *
     * @param url   the URL to send the DELETE request to
     * @param token the authorization token
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
     * Sends a DELETE request to the specified URL.
     *
     * @param url          the URL to send the DELETE request to
     * @param extraHeaders the extra headers to include in the DELETE request
     * @param token        the authorization token
     * @return the HTTP response
     */
    public static Response delete(String url, Map<String, String> extraHeaders, String token) {
        RequestSpecification requestSpec = RestAssured.given()
                .header("Authorization", "Bearer " + token)
                .header("X-IFS-Maintenix-Trusted-Secret", "true")
                .header("X-IFS-Maintenix-Trusted-Request-Origin", "true");

        if (extraHeaders != null && !extraHeaders.isEmpty()) {
            extraHeaders.forEach(requestSpec::header);
        }

        return requestSpec
                .delete(url)
                .then()
                .extract()
                .response();
    }

}
