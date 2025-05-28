package com.and.utility.apiUtils;

import io.restassured.response.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility to handle error responses from HTTP calls.
 */
public class ErrorResponseHandler {

    private static final Logger logger = LoggerFactory.getLogger(ErrorResponseHandler.class);

    /**
     * Handles error responses by checking status codes and throwing informative exceptions.
     *
     * @param response the HTTP response
     * @param method   the HTTP method used
     * @param url      the URL requested
     */
    public static void handleErrorResponse(Response response, String method, String url, String errorMessage) {

        if (response == null) {
            logger.error("Response is null");
            throw new IllegalStateException("Response is null");
        }

        int statusCode = response.getStatusCode();

        if (statusCode < 400) {
            return;
        }

        String baseMessage = String.format(
                "%s:\nHTTP %s request to %s failed with status code %d.\nResponse Body: %s",
                errorMessage, method, url, statusCode, response.asString()
        );

        String reason;

        switch (statusCode) {
            case 400 -> reason = "Bad Request: ";
            case 401 -> reason = "Unauthorized: ";
            case 403 -> reason = "Forbidden: ";
            case 404 -> reason = "Not Found: ";
            case 405 -> reason = "Method Not Allowed: ";
            case 408 -> reason = "Request Timeout: ";
            case 409 -> reason = "Conflict: ";
            case 415 -> reason = "Unsupported Media Type: ";
            case 422 -> reason = "Unprocessable Entity: ";
            case 429 -> reason = "Too Many Requests: ";
            case 500 -> reason = "Internal Server Error: ";
            case 501 -> reason = "Not Implemented: ";
            case 502 -> reason = "Bad Gateway: ";
            case 503 -> reason = "Service Unavailable: ";
            case 504 -> reason = "Gateway Timeout: ";
            default -> reason = "Unexpected Error: ";
        }

        String detailedMessage = reason + baseMessage;
        logger.error(detailedMessage);
    }
}
