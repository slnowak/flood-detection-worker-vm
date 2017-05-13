package pl.edu.agh.azurevm;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    static final Properties properties;

    static {
        properties = new Properties();

        try(InputStream localCfg = Configuration.class.getResourceAsStream("/app-config.properties");
            InputStream systemCfg = new FileInputStream("/etc/environment")) {

            InputStream cfg = localCfg != null ? localCfg : systemCfg;
            properties.load(cfg);

        } catch (Exception e) {
            throw new RuntimeException("Provide /app-config.properties file in /src/main/resources directory or /etc/environment file", e);
        }
    }

    public static String getString(String key) {
        return properties.getProperty(key);
    }

}