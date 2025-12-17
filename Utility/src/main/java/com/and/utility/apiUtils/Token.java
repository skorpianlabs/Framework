
package com.and.utility.apiUtils;

import com.and.utility.PropertyManager;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
// ⭐ Added: JSoup to parse login form and hidden inputs
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.net.URI;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import static com.and.utility.constants.CommonValues.ENDED;
import static com.and.utility.constants.CommonValues.STARTED;
import static io.restassured.RestAssured.given;

/**
 * Token class to generate bearer tokens for service and user flows.
 * Supports:
 *  - Client Credentials (app-only)
 *  - Legacy ROPC (password grant) [avoid in prod]
 *  - Authorization Code + PKCE (public client) with headless login — mirrors Postman flow
 */
public class Token {

    private static final Logger logger = LogManager.getLogger(Token.class);

    private static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();
    private static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    private static final String AUTH_PATH =
            "/auth/realms/" + PropertyManager.getInstance().getProperty("AUTH_REALM")
                    + "/protocol/openid-connect/token";

    // ====================================================================================
    // Client Credentials grant: app-only token (no user)
    // ====================================================================================
    public static String generateBearerTokenWithClientCredential() {
        logger.info(STARTED + "generateBearerTokenWithClientCredential");
        try {
            String clientId = PropertyManager.getInstance().getProperty("CLIENT_ID");
            String secretKey = PropertyManager.getInstance().getProperty("SECRET");
            String tokenUrl  = PropertyManager.getInstance().getProperty("TOKEN_URL");

            Response response = given()
                    .auth().preemptive().basic(clientId, secretKey)
                    .contentType(CONTENT_TYPE)
                    .formParam("grant_type", "client_credentials")
                    .formParam("scope", "openid")
                    .post(tokenUrl);

            if (response.statusCode() != 200) {
                logger.error("Client credentials failed: {} -> {}", response.statusLine(), response.getBody().asString());
                throw new IllegalStateException("Client credentials failed: " + response.statusLine());
            }

            String accessToken = response.jsonPath().getString("access_token");
            logger.info(ENDED + "generateBearerTokenWithClientCredential");
            return accessToken;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ====================================================================================
    // Legacy ROPC (password) grant — keep for explicit test utilities
    // ====================================================================================
    public static String generateBearerTokenWithPasswordCredentialold(String username, String password) {
        logger.info(STARTED + "generateBearerTokenWithPasswordCredential");
        try {
            String clientId = PropertyManager.getInstance().getProperty("CLIENT_ID");
            String secretKey = PropertyManager.getInstance().getProperty("SECRET");
            String tokenUrl  = PropertyManager.getInstance().getProperty("TOKEN_URL");

            Response response = given()
                    .auth().preemptive().basic(clientId, secretKey)
                    .contentType(CONTENT_TYPE)
                    .formParam("grant_type", "password")
                    .formParam("username", username)
                    .formParam("password", password)
                    .formParam("scope", "openid")
                    .post(tokenUrl);

            if (response.statusCode() != 200) {
                logger.error("ROPC failed: {} -> {}", response.statusLine(), response.getBody().asString());
                throw new IllegalStateException("ROPC failed: " + response.statusLine());
            }

            String accessToken = response.jsonPath().getString("access_token");
            logger.info(ENDED + "generateBearerTokenWithPasswordCredential");
            return accessToken;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // ====================================================================================
    // Authorization Code + PKCE (public client) — mirrors Postman flow
    // ====================================================================================
    /**
     * PKCE headless login flow:
     *  1) Build PKCE, GET /authorize (fresh session per run).
     *  2) Follow 302 to GET /iam/login?session=... (resolve relative Location).
     *  3) Parse login page (form action + hidden inputs) and POST credentials to /iam/login.
     *  4) Follow 302(s) until Location starts with redirect_uri (do not GET it), extract code.
     *  5) POST /token with code_verifier to get access_token (NO client_secret for public client).
     */
    public static String generateBearerTokenWithPasswordCredential(String username, String password) {
        logger.info("Started: generateBearerTokenWithPkce");
        try {
            PropertyManager pm = PropertyManager.getInstance();

            // --- Properties ---
            String authUrl     = pm.getProperty("AUTH_URL");         // e.g., https://.../oauth2/authorize
            String tokenUrl    = pm.getProperty("TOKEN_URL");        // e.g., https://.../oauth2/token
            String clientId    = pm.getProperty("CLIENT_ID");        // public client (www)
            String redirectUri = pm.getProperty("REDIRECT_URI");     // e.g., http://localhost:8080/callback
            String scope       = pm.getProperty("SCOPE");            // e.g., kairos (add openid/offline_access as needed)

            // --- Build PKCE ---
            String codeVerifier  = PkceUtils.generateCodeVerifier(64);
            String codeChallenge = PkceUtils.generateCodeChallengeS256(codeVerifier);
            String state         = "st-" + System.currentTimeMillis();
            String nonce         = "nn-" + System.nanoTime();

            // --- Build /authorize URL (use '&') ---
            String authorizeUrl = authUrl
                    + "?response_type=code"
                    + "&client_id=" + enc(clientId)
                    + "&scope=" + enc(scope != null ? scope : "kairos")
                    + "&redirect_uri=" + enc(redirectUri)        // MUST exactly match registered URI
                    + "&code_challenge=" + enc(codeChallenge)
                    + "&code_challenge_method=S256"
                    + "&state=" + enc(state)
                    + "&nonce=" + enc(nonce);

            logger.info("Authorize URL: {}", authorizeUrl);

            // ⭐ Added: single session filter to carry cookies like a browser
            SessionFilter session = new SessionFilter();

            // --- 1) GET /authorize ---
            Response authorizeResp = reqGet(session, authorizeUrl); // ⭐ Added: null-safe request helper

            String finalLocation = null;
            Response loginPageResp = null;
            String baseForResolution = authorizeUrl;

            if (isRedirect(authorizeResp)) {
                String loc = authorizeResp.getHeader("Location");
                String absLoc = absolutize(loc, baseForResolution);      // ⭐ Added: resolve relative Location
                absLoc = normalizeRedirect(absLoc, redirectUri);         // ⭐ Added: trim malformed embedded callback
                logger.info("→ 302 Location from /authorize: {}", absLoc);

                // If it points straight to redirect_uri, stop (do not GET it)
                if (absLoc != null && absLoc.startsWith(redirectUri)) {
                    finalLocation = absLoc;
                } else {
                    loginPageResp = reqGet(session, absLoc);
                    baseForResolution = absLoc;
                }
            } else if (authorizeResp.statusCode() == 200) {
                loginPageResp = authorizeResp; // login page delivered directly
            } else {
                throw new IllegalStateException("Unexpected /authorize response: " + authorizeResp.statusLine());
            }

            // --- 2) If login page, parse and submit the form ---
            Response postLoginResp = null;
            if (finalLocation == null) {
                if (loginPageResp == null) {
                    throw new IllegalStateException("Login page was not reached; last Location=" +
                            authorizeResp.getHeader("Location"));
                }

                LoginSubmitResult lr = submitParsedLoginForm(loginPageResp, session, baseForResolution, username, password); // ⭐ Added
                postLoginResp = lr.response;

                // After POST /iam/login, follow 302 chain to final redirect_uri with code
                finalLocation = follow302UntilRedirectUri(postLoginResp, session, lr.actionAbs, redirectUri); // ⭐ Added
            }

            if (finalLocation == null || !finalLocation.startsWith(redirectUri)) {
                throw new IllegalStateException("Did not reach redirect URI. Final Location=" + finalLocation);
            }

            // --- 3) Extract code + validate state ---
            String code = extractQueryParam(finalLocation, "code");     // ⭐ Added: safe extractor
            String returnedState = extractQueryParam(finalLocation, "state");
            if (code == null) throw new IllegalStateException("Authorization code not found.");
            if (returnedState != null && !returnedState.equals(state)) {
                throw new IllegalStateException("State mismatch (CSRF protection).");
            }

            // --- 4) Exchange code for tokens (NO client_secret for public client) ---
            Response tokenResp = RestAssured.given()
                    .relaxedHTTPSValidation()
                    .contentType(ContentType.URLENC)
                    .formParam("grant_type", "authorization_code")
                    .formParam("code", code)
                    .formParam("redirect_uri", redirectUri)   // EXACT same string as in /authorize
                    .formParam("client_id", clientId)
                    .formParam("code_verifier", codeVerifier) // same verifier used for challenge
                    .post(tokenUrl);

            if (tokenResp.statusCode() != 200) {
                throw new IllegalStateException("Token exchange failed: " + tokenResp.statusLine()
                        + " => " + tokenResp.getBody().asString());
            }

            String accessToken = tokenResp.jsonPath().getString("access_token");
            logger.info("Ended: generateBearerTokenWithPkce");
            return accessToken;

        } catch (Exception e) {
            throw new RuntimeException("PKCE flow failed", e);
        }
    }

    // ====================================================================================
    // Helpers
    // ====================================================================================

    private static String enc(String s) {
        return java.net.URLEncoder.encode(s, StandardCharsets.UTF_8);
    }

    // ⭐ Added: lightweight helper to detect redirects with Location header
    private static boolean isRedirect(Response r) {
        int sc = r.statusCode();
        return sc >= 300 && sc < 400 && r.getHeader("Location") != null;
    }

    // ⭐ Added: null-safe GET with session filter
    private static Response reqGet(SessionFilter session, String url) {
        var req = RestAssured.given().relaxedHTTPSValidation().redirects().follow(false);
        if (session != null) req.filter(session);
        return req.get(url);
    }

    // ⭐ Added: null-safe POST with session filter and form params
    private static Response reqPost(SessionFilter session, String url, Map<String, String> formParams) {
        var req = RestAssured.given()
                .relaxedHTTPSValidation()
                .redirects().follow(false)
                .contentType(ContentType.URLENC);
        if (session != null) req.filter(session);
        if (formParams != null && !formParams.isEmpty()) req.formParams(formParams);
        return req.post(url);
    }

    /**
     * Resolve a Location string against baseUrl. If the location is already absolute, return as-is.
     * (Fixes bugs like ".../iam/https://oauth.pstmn.io/v1/callback?..." or encoded variants.)
     */
    // ⭐ Added
    private static String absolutize(String location, String baseUrl) {
        if (location == null || location.isBlank()) return location;
        try {
            if (location.startsWith("http://") || location.startsWith("https://")) return location;
            URI base = URI.create(baseUrl);
            return base.resolve(location).toString();
        } catch (Exception e) {
            return location; // fallback if malformed
        }
    }

    /**
     * Normalize badly-formed redirects that embed an absolute URL after '/iam/'.
     * Examples fixed:
     *  - https://plt-eu.../iam/https://oauth.pstmn.io/v1/callback?...  -> https://oauth.pstmn.io/v1/callback?...
     *  - https://plt-eu.../iam/https%3A%2F%2Foauth.pstmn.io%2Fv1%2Fcallback?... -> decode -> https://oauth.pstmn.io/v1/callback?...
     */
    // ⭐ Added
    private static String normalizeRedirect(String url, String redirectUriPrefix) {
        if (url == null) return null;
        if (url.startsWith(redirectUriPrefix)) return url;

        int idxEnc = url.indexOf("/https%3A%2F%2F");
        if (idxEnc >= 0) {
            String encoded = url.substring(idxEnc + 1); // drop leading slash
            return URLDecoder.decode(encoded, StandardCharsets.UTF_8);
        }

        int idxPlain = url.indexOf("/https://");
        if (idxPlain >= 0) {
            return url.substring(idxPlain + 1); // drop leading slash
        }

        return url;
    }

    // ⭐ Added: safe extractor (split on '&', decode value once)
    private static String extractQueryParam(String url, String name) throws Exception {
        URI uri = URI.create(url);
        String query = uri.getRawQuery();
        if (query == null) return null;
        for (String pair : query.split("&")) {
            int idx = pair.indexOf('=');
            String k = idx > 0 ? pair.substring(0, idx) : pair;
            String v = idx > 0 ? pair.substring(idx + 1) : "";
            if (name.equals(k)) return URLDecoder.decode(v, StandardCharsets.UTF_8);
        }
        return null;
    }

    /**
     * After POST /iam/login, follow 302(s) until Location starts with redirectUriPrefix.
     * Does NOT GET the final callback (just returns the Location).
     * Also normalizes embedded/encoded callback URLs before deciding.
     */
    // ⭐ Added
    private static String follow302UntilRedirectUri(
            Response start,
            SessionFilter session,
            String initialUrl,
            String redirectUriPrefix) {

        Response current = start;
        String currentUrl = initialUrl;
        int max = 15;

        while (max-- > 0) {
            int sc = current.statusCode();
            if (sc >= 300 && sc < 400) {
                String loc = current.getHeader("Location");
                if (loc == null) throw new IllegalStateException("3xx without Location header at " + currentUrl);

                String absLoc = absolutize(loc, currentUrl);
                absLoc = normalizeRedirect(absLoc, redirectUriPrefix);
                logger.info("→ 302 Location: {}", absLoc);

                // STOP on the callback – DO NOT GET it
                if (absLoc.startsWith(redirectUriPrefix)) {
                    return absLoc; // final callback found
                }

                currentUrl = absLoc;
                current = reqGet(session, absLoc);
                continue;
            }

            // For your flow we expect 302s here; 200 would indicate an unexpected HTML hop (consent, etc.)
            if (sc == 200) {
                throw new IllegalStateException("Expected 302 after login POST, but got 200 at " + currentUrl);
            }

            throw new IllegalStateException("Unexpected status " + sc + " while following redirects at " + currentUrl);
        }

        throw new IllegalStateException("Too many redirects without reaching redirect_uri (" + redirectUriPrefix + ").");
    }

    // ====================================================================================
    // Login parsing & submit (JSoup)
    // ====================================================================================

    /** Result of submitting the parsed login form: the response and absolute form action URL used. */
    // ⭐ Added
    private static final class LoginSubmitResult {
        final Response response;
        final String actionAbs;
        LoginSubmitResult(Response response, String actionAbs) {
            this.response = response;
            this.actionAbs = actionAbs;
        }
    }

    /**
     * Parses the login HTML page, collects all inputs (including hidden), overwrites username/password,
     * and POSTs to the form action URL. Returns the response and the absolute form action URL used.
     */
    // ⭐ Added
    private static LoginSubmitResult submitParsedLoginForm(
            Response loginPageResp,
            SessionFilter session,
            String baseUrlForResolution,
            String username,
            String password) {

        String html = loginPageResp.getBody().asString();
        Document doc = Jsoup.parse(html);

        Element form = doc.selectFirst("form");
        if (form == null) throw new IllegalStateException("Login form not found in page.");

        String action = form.hasAttr("action") ? form.attr("action") : "";
        if (action == null || action.isBlank()) {
            throw new IllegalStateException("Login form has no action.");
        }
        String actionAbs = absolutize(action, baseUrlForResolution);

        Map<String,String> formParams = new LinkedHashMap<>();
        for (Element input : form.select("input[name]")) {
            String name  = input.attr("name");
            String value = input.attr("value");
            formParams.put(name, value != null ? value : "");
        }

        Element userInput = form.selectFirst("input[type=text][name], input[type=email][name]");
        Element passInput = form.selectFirst("input[type=password][name]");
        String userField = userInput != null ? userInput.attr("name") : "username";
        String passField = passInput != null ? passInput.attr("name") : "password";

        formParams.put(userField, username);
        formParams.put(passField, password);

        Response postResp = reqPost(session, actionAbs, formParams);
        return new LoginSubmitResult(postResp, actionAbs);
    }

    // ====================================================================================
    // Existing Keycloak-style helpers (ROPC authenticate)
    // ====================================================================================

    public static void authenticate(String username) {
        getToken(username, username);
    }

    public static void authenticate(String username, String password) {
        getToken(username, password);
    }

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

    public static String getToken() {
        if (tokenHolder.get() == null) {
            throw new IllegalStateException("No token present in ThreadLocal. Call authenticate(username,password) explicitly.");
        }
        return tokenHolder.get();
    }

    public static void clearToken() {
        tokenHolder.remove();
        logger.info("Token cleared from ThreadLocal.");
    }
}
