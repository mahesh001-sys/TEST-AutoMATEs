package com.automates.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Loads framework configuration from config.properties.
 */
public class ConfigLoader {

    private final Properties properties;

    public ConfigLoader() {
        properties = new Properties();
        loadProperties();
    }

    private void loadProperties() {

        try (InputStream inputStream =
                     getClass()
                             .getClassLoader()
                             .getResourceAsStream("config.properties")) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "config.properties not found in classpath");
            }

            properties.load(inputStream);

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Unable to load config.properties",
                    e);
        }
    }

    /**
     * Returns a configuration value.
     *
     * @param key configuration key
     * @return configuration value
     */
    public String get(String key) {

        String value = properties.getProperty(key);

        if (value == null) {
            throw new IllegalArgumentException(
                    "Configuration key not found: " + key);
        }

        return value.trim();
    }

    /**
     * Returns an integer configuration value.
     */
    public int getInt(String key) {
        return Integer.parseInt(get(key));
    }

    /**
     * Returns a boolean configuration value.
     */
    public boolean getBoolean(String key) {
        return Boolean.parseBoolean(get(key));
    }
}
