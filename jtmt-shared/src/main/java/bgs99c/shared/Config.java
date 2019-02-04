package bgs99c.shared;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Config {
    private Properties properties = new Properties();
    public Config() {
        try {
            properties.load(getClass().getResourceAsStream("/server.properties"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public String getHost() {
        return properties.getProperty("host");
    }
}
