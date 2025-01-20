package com.and.utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoginUtils {

    private static Properties prop;

    /**
     * Method for getting the data stored in the login.properties file
     */
    public static String getPropertyFile(String keyProperty) throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream("src/test/login.properties");
        prop.load(fis);
        return prop.getProperty(keyProperty);
    }

}
