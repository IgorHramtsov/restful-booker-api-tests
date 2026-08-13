package restful_booker.config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class BookerConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = BookerConfig.class.getClassLoader().getResourceAsStream("booker.properties")) {

            if (inputStream == null) {
                throw new IllegalStateException("booker.properties was not found");
            }

            PROPERTIES.load(inputStream);

        } catch (IOException exception) {
            throw new RuntimeException("Failed to load booker.properties", exception);
        }
    }

    private BookerConfig() {
    }

    public static String get(String key) {
        String value = PROPERTIES.getProperty(key);

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Property is missing: " + key);
        }

        return value;
    }
}