package com.and.utility.nativeUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DeviceUtils {

    private static Properties prop;

    /**
     * Method for getting the data stored in the capabilities.properties file
     */
    public static String getPropertyFile(String keyProperty) throws IOException {
        prop = new Properties();
        FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "src/main/java/com/and/resources/capabilities.properties");
        prop.load(fis);
        return prop.getProperty(keyProperty);
    }

}
