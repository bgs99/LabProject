package bgs99c.shared;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    private static Properties properties = new Properties();
    static  {
        try {
            InputStream r = Config.class.getResourceAsStream("/server.properties");
            properties.load(r);
            r.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static int getPort() {
        return Integer.parseInt(properties.getProperty("port"));
    }

    public static String getHost() {
        return properties.getProperty("host");
    }
}
