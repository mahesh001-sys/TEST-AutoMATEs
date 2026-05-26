package com.automates.config;

import com.automates.utils.Logger;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads and exposes application configuration from a {@code .properties} file.
 *
 * <p>Call {@link #load(String)} once at startup; then access values via
 * {@link #get(String)} or {@link #get(String, String)}.</p>
 *
 * @author Banoth Mahesh Kumar
 */
public final class ConfigLoader {

    private static final Properties props  = new Properties();
    private static final Logger     logger = Logger.getInstance();
    private static       boolean    loaded = false;

    private ConfigLoader() {}

    /**
     * Loads properties from the given file path.
     *
     * @param path path to the {@code .properties} file
     */
    public static synchronized void load(String path) {
        try (FileInputStream fis = new FileInputStream(path)) {
            props.load(fis);
            loaded = true;
            logger.info("Configuration loaded from: " + path);
        } catch (IOException e) {
            logger.warn("Config file not found at '" + path + "' — using defaults.");
            loadDefaults();
        }
    }

    /** Returns the value for {@code key}, or {@code null} if absent. */
    public static String get(String key) {
        return props.getProperty(key);
    }

    /** Returns the value for {@code key}, or {@code defaultValue} if absent. */
    public static String get(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }

    public static boolean isLoaded() { return loaded; }

    private static void loadDefaults() {
        props.setProperty("app.name",          "AutoMATEs");
        props.setProperty("app.version",       "1.0.0");
        props.setProperty("task.max.retries",  "2");
        props.setProperty("report.output.dir", "outputs");
        props.setProperty("log.level",         "INFO");
        loaded = true;
    }
}
