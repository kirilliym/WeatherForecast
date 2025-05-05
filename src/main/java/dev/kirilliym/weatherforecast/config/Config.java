package dev.kirilliym.weatherforecast.config;


import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Config.class.getClassLoader().getResourceAsStream("application.properties")) {
            if (input == null) {
                throw new RuntimeException("application.properties не найден");
            }
            props.load(input);
        } catch (Exception e) {
            throw new RuntimeException("ошибка при загрузке application.properties", e);
        }
    }

    public static String get(String key) {
        String value = props.getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException(key + " не найден в config.properties");
        }
        return value;
    }
}