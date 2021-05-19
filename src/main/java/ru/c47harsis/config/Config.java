package ru.c47harsis.config;

import lombok.extern.slf4j.Slf4j;

import java.util.EnumSet;
import java.util.Locale;

@Slf4j
@SuppressWarnings("unused")
public enum Config {
    BOT_TOKEN,
    BOT_USERNAME,
    DB_HOST,
    DB_PORT,
    DB_NAME,
    DB_USERNAME,
    DB_PASSWORD;

    private static final String CONFIG_FILE = "settings.yml";

    static {
        ConfigReader configReader = new ConfigReader(CONFIG_FILE);
        for (Config property : EnumSet.allOf(Config.class)) {
            String value = System.getenv(property.name());
            if (value != null) {
                property.value = value;
                log.debug("Property {} set to {} from environment", property.name(), property.value);
                continue;
            }

            String propertyKey = property.name().toLowerCase(Locale.ROOT).replace('_', '.');
            value = configReader.readValue(propertyKey);
            if (value != null) {
                property.value = value;
                log.debug("Property {} set to {} from {}", property.name(), property.value, CONFIG_FILE);
                continue;
            }

            if (property.value == null && property.required) {
                IllegalStateException e = new IllegalStateException(
                        "Invalid config: can not fill required property " + property.name());
                log.error(e.getMessage());
                throw e;
            }
        }
    }

    private final boolean required;
    private String value;

    Config() {
        this(null, true);
    }

    Config(String defaultValue) {
        this(defaultValue, true);
    }

    Config(boolean required) {
        this(null, required);
    }

    Config(String defaultValue, boolean required) {
        this.value = defaultValue;
        this.required = required;
    }

    public String getValue() {
        return value;
    }
}