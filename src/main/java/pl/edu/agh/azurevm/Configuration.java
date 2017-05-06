package pl.edu.agh.azurevm;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    static final Properties properties;

    static {
        properties = new Properties();
        InputStream in = Configuration.class.getResourceAsStream("/app-config.properties");
        try {
            properties.load(in);
            in.close();
        } catch (Exception e) {
            throw new RuntimeException("Provide /app-config.properties file in /src/main/resources directory", e);
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

}