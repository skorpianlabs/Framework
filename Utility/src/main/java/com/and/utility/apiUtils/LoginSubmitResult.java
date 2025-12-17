
package com.and.utility.apiUtils;

import io.restassured.response.Response;

/** Result of submitting the parsed login form: the response and absolute form action URL used. */
public final class LoginSubmitResult {
    public final Response response;
    public final String actionAbs;

    public LoginSubmitResult(Response response, String actionAbs) {
        this.response = response;
        this.actionAbs = actionAbs;
    }
}
