package com.and.utility.apiUtils;

import java.security.SecureRandom;
import java.security.MessageDigest;
import java.util.Base64;
import java.nio.charset.StandardCharsets;

public class PkceUtils {
    private static final SecureRandom RANDOM = new SecureRandom();
    private static final String PKCE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~";

    public static String generateCodeVerifier(int length) {
        if (length < 43 || length > 128) throw new IllegalArgumentException("Verifier must be 43–128 chars.");
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) sb.append(PKCE_CHARS.charAt(RANDOM.nextInt(PKCE_CHARS.length())));
        return sb.toString();
    }

    public static String generateCodeChallengeS256(String codeVerifier) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] digest = md.digest(codeVerifier.getBytes(StandardCharsets.US_ASCII));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(digest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create code challenge", e);
        }
    }
}
