
package com.and.utility.apiUtils;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Represents the next navigation step when resolving OAuth redirect flows headlessly.
 * It can be:
 *  - GET  : follow a URL via HTTP GET
 *  - POST : submit a form to a URL via HTTP POST with parameters
 *  - NONE : no next hop could be determined from the current HTML/response
 */
public final class NextHop {

    public enum Kind { GET, POST, NONE }

    private final Kind kind;
    private final String url;                  // absolute URL
    private final Map<String, String> params;  // POST params (null for GET/NONE)

    private NextHop(Kind kind, String url, Map<String, String> params) {
        this.kind  = kind;
        this.url   = url;
        this.params = (params == null)
                ? null
                : Collections.unmodifiableMap(new LinkedHashMap<>(params));
    }

    /** Factory: GET next hop to URL. */
    public static NextHop get(String url) {
        return new NextHop(Kind.GET, url, null);
    }

    /** Factory: POST next hop to URL with params. */
    public static NextHop post(String url, Map<String, String> params) {
        return new NextHop(Kind.POST, url, params);
    }

    /** Factory: no next hop available. */
    public static NextHop none() {
        return new NextHop(Kind.NONE, null, null);
    }

    // --- Getters ---
    public Kind getKind() { return kind; }
    public String getUrl() { return url; }
    /** Returns an unmodifiable map of POST params (or null if kind != POST). */
    public Map<String, String> getParams() { return params; }

    @Override
    public String toString() {
        return "NextHop{kind=" + kind + ", url='" + url + "', params=" + params + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NextHop)) return false;
        NextHop nextHop = (NextHop) o;
        return kind == nextHop.kind &&
                Objects.equals(url, nextHop.url) &&
                Objects.equals(params, nextHop.params);
    }

    @Override
    public int hashCode() {
        return Objects.hash(kind, url, params);
    }
}
