package com.and;

import com.and.utility.PropertyManager;
import com.and.utility.apiUtils.Token;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.lang.reflect.Method;

public class TokenSmokeMainSimple {

    public static void main(String[] args) {
        // Trust dev/self-signed TLS if any
        RestAssured.useRelaxedHTTPSValidation();

        // --- Credentials you provided (avoid committing to VCS in real projects) ---
        String username = "todo-admin";
        String password = "Hn1grc7Q6vweRE41Ud4p";

        // --- Seed all required properties in memory (no file changes) ---
        PropertyManager pm = PropertyManager.getInstance();
        putProp(pm, "AUTH_URL",       "https://plt-eu.nxs-dev.ifscsc.cloud/iam/oauth2/authorize"); // /authorize
        putProp(pm, "TOKEN_URL",      "https://plt-eu.nxs-dev.ifscsc.cloud/iam/oauth2/token");      // /token
        putProp(pm, "CLIENT_ID",      "www");                                                       // public client
        putProp(pm, "REDIRECT_URI",   "https://oauth.pstmn.io/v1/callback");                        // Postman callback
        putProp(pm, "SCOPE",          "kairos");                                                    // add "openid" if you need id_token
        putProp(pm, "LOGIN_ACTION_URL","https://plt-eu.nxs-dev.ifscsc.cloud/iam/login");            // login form action (adjust if different)
        putProp(pm, "LOGIN_FIELD_USER","username");                                                 // from your HTML <input name="username">
        putProp(pm, "LOGIN_FIELD_PASS","password");                                                 // typical name for password input

        // Optional: if you want to smoke-call any protected API after getting the token
        putProp(pm, "SMOKE_API_URL",  ""); // e.g., https://api.your-backend.local/v1/resource

        try {
            System.out.println("Requesting access token via PKCE…");

            // IMPORTANT: ensure your Token.generateBearerTokenWithPasswordCredential(...)
            // actually contains the PKCE flow you implemented (not the legacy ROPC).
            String accessToken = Token.generateBearerTokenWithPasswordCredential(username, password);

            if (accessToken == null || accessToken.isBlank()) {
                System.err.println("❌ Token is null/empty");
                System.exit(3);
            }

            System.out.println("✅ Token obtained. Prefix: "
                    + accessToken.substring(0, Math.min(48, accessToken.length())) + "…");
            System.out.println("Length: " + accessToken.length());

            // Optional: call protected API if configured
            String smokeApi = pm.getProperty("SMOKE_API_URL");
            if (smokeApi != null && !smokeApi.isBlank()) {
                Response r = RestAssured.given()
                        .auth().oauth2(accessToken)
                        .get(smokeApi);
                System.out.printf("Smoke API %s → %d %s%n", smokeApi, r.statusCode(), r.statusLine());
                System.out.println(r.getBody().asString());
            }

        } catch (Exception e) {
            System.err.println("❌ PKCE token flow failed: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    /**
     * Tries PropertyManager#setProperty(key, value) if it exists;
     * otherwise falls back to System.setProperty.
     */
    private static void putProp(PropertyManager pm, String key, String value) {
        try {
            Method setProp = pm.getClass().getMethod("setProperty", String.class, String.class);
            setProp.invoke(pm, key, value);
        } catch (NoSuchMethodException nsme) {
            System.setProperty(key, value);
        } catch (Exception ignore) {
            System.setProperty(key, value);
        }
    }
}
