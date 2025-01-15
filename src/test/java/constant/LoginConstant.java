package constant;

import utility.LoginUtils;

import java.io.IOException;

public class LoginConstant {

    public static final String USER_NAME;
    public static final String PASSWORD;
    public static final String USER2_NAME;
    public static final String PASSWORD2;
    public static final String MOC_USERNAME;
    public static final String MOC_PASSWORD;
    public static final String MX_LOBBY_URL;

    static {
        try {
            USER_NAME = LoginUtils.getPropertyFile("USER_NAME");
        } catch (IOException e) {
            throw new RuntimeException("Unable to get login username");
        }
    }

    static {
        try {
            PASSWORD = LoginUtils.getPropertyFile("PASSWORD");
        } catch (IOException e) {
            throw new RuntimeException("Unable to get login password");
        }
    }

    static {
        try {
            USER2_NAME = LoginUtils.getPropertyFile("USER2_NAME");
        } catch (IOException e) {
            throw new RuntimeException("Unable to get login username for user 2");
        }
    }

    static {
        try {
            PASSWORD2 = LoginUtils.getPropertyFile("PASSWORD2");
        } catch (IOException e) {
            throw new RuntimeException("Unable to get login password for user 2");
        }
    }

    static {
        try {
            MOC_PASSWORD = LoginUtils.getPropertyFile("MOC_USERNAME");
        } catch (IOException e) {
            throw new RuntimeException("Unable to get login username for moc");
        }
    }

    static {
        try {
            MOC_USERNAME = LoginUtils.getPropertyFile("MOC_PASSWORD");
        } catch (IOException e) {
            throw new RuntimeException("Unable to get login password for moc");
        }
    }

    static {
        try {
            MX_LOBBY_URL = LoginUtils.getPropertyFile("MX_LOBBY_URL");
        } catch (IOException e) {
            throw new RuntimeException("Unable to get mx lobby url");
        }
    }
}
