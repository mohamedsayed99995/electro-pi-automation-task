package com.electropi.automation.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class Config {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream input =
                     Config.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new IllegalStateException("config.properties was not found");
            }

            PROPERTIES.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Unable to load test configuration", e);
        }
    }

    private Config() {
    }

    public static String baseUrl() {
        return System.getProperty("base.url",
                PROPERTIES.getProperty("base.url"));
    }

    public static String browser() {
        return System.getProperty("browser",
                PROPERTIES.getProperty("browser", "chromium"));
    }

    public static boolean headless() {
        return Boolean.parseBoolean(System.getProperty("headless",
                PROPERTIES.getProperty("headless", "true")));
    }

    public static double slowMo() {
        return Double.parseDouble(System.getProperty("slowmo",
                PROPERTIES.getProperty("slowmo", "0")));
    }

    public static int timeoutMs() {
        return Integer.parseInt(System.getProperty("timeout.ms",
                PROPERTIES.getProperty("timeout.ms", "15000")));
    }

    public static String adminEmail() {
        return requiredEnvironmentVariable("STORE_ADMIN_EMAIL");
    }

    public static String adminPassword() {
        return requiredEnvironmentVariable("STORE_ADMIN_PASSWORD");
    }

    private static String requiredEnvironmentVariable(String name) {
        String value = System.getenv(name);

        if (value == null || value.isBlank()) {
            throw new IllegalStateException(
                    "Required environment variable is missing: " + name);
        }

        return value;
    }
}
