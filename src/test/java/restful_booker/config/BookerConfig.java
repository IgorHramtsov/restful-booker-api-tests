package restful_booker.config;

import java.io.IOException;
import java.util.Properties;

public final class BookerConfig {

    private static final Properties PROPERTIES = new Properties();

    static {
        try {
            PROPERTIES.load(
                    BookerConfig.class
                            .getClassLoader()
                            .getResourceAsStream("booker.properties")
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private BookerConfig() {
    }

    public static String get(String key) {
        return PROPERTIES.getProperty(key);
    }
}