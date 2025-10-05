package utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;
import java.util.Properties;

public class ConfigManager {
    private static final Properties PROPERTIES = new Properties();

    static {
        try (InputStream inputStream = getResourceAsStream("config.properties")) {
            if (inputStream != null) {
                PROPERTIES.load(inputStream);
            }
        } catch (IOException ignored) {
        }
    }

    private static InputStream getResourceAsStream(String fileName) {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream stream = classLoader.getResourceAsStream(fileName);
        if (stream == null) {
            stream = ConfigManager.class.getClassLoader().getResourceAsStream(fileName);
        }
        return stream;
    }

    public static String get(String key, String defaultValue) {
        String sys = System.getProperty(key);
        if (Objects.nonNull(sys) && !sys.isBlank()) {
            return sys;
        }
        String env = System.getenv(key);
        if (Objects.nonNull(env) && !env.isBlank()) {
            return env;
        }
        String value = PROPERTIES.getProperty(key);
        if (Objects.nonNull(value) && !value.isBlank()) {
            return value;
        }
        return defaultValue;
    }

    public static String get(String key) {
        return get(key, null);
    }
}


