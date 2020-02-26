package bean;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Property {
    private static Properties properties;

    public static String getProperty(String key) {
        if (properties == null) {
            properties = new Properties();
            try {
                properties.load(new FileInputStream("properties/file.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("properties load success");
        }
        return properties.getProperty(key);
    }
}
