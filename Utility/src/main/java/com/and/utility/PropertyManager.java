package com.and.utility;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

/**
 * PropertyManager class to load the properties from the properties file
 */
public class PropertyManager {
    private static final List<String> PROPERTY_FILE_PATHS = List.of(
            "properties/RESTAPI.properties",
            "properties/login.properties",
            "properties/database.properties",
            "properties/config.properties"
    );


    private static PropertyManager instance;
    private Properties properties;

    private PropertyManager() {
        loadProperties();
    }

    /**
     * Get the instance of the PropertyManager
     *
     * @return The instance of the PropertyManager
     */
    public static synchronized PropertyManager getInstance() {
        if (instance == null) {
            instance = new PropertyManager();
        }
        return instance;
    }

    /**
     * Load the properties from the properties files
     */
    private void loadProperties() {
        properties = new Properties();
        for (String filePath : PROPERTY_FILE_PATHS) {
            try (InputStream input = getClass().getClassLoader().getResourceAsStream(filePath)) {
                if (input == null) {
                    throw new RuntimeException("File not found: " + filePath);
                }
                properties.load(input);
            } catch (IOException e) {
                throw new RuntimeException("Unable to load properties file: " + filePath, e);
            }
        }
    }


    /**
     * Get the property value for the given key
     *
     * @param key The key for which the value is required
     * @return The value of the property
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}