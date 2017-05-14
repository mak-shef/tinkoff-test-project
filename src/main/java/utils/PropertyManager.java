package utils;

import exceptions.PropertyLoadException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyManager {

    private static final String PROP_FILE = "/application.properties";
    private static PropertyManager instance = new PropertyManager();

    private Properties properties;

    private PropertyManager() {
        properties = loadPropertyFile();
    }

    public static PropertyManager getInstance() {
        return instance;
    }

    public String getProperty(String propertyId) {
        String value;
        if (properties.containsKey(propertyId)) {
            value = properties.getProperty(propertyId);
        } else {
            value = loadProperty(propertyId);
        }
        return value;
    }

    private Properties loadPropertyFile() {
        Properties props = new Properties();
        File file = new File(PROP_FILE);
            if (file.exists()) {
                try {
                    InputStream stream = new FileInputStream(PROP_FILE);
                    props.load(stream);
                } catch (IOException e) {
                    throw new PropertyLoadException(e.getLocalizedMessage());
                }
            }
        return props;
    }

    public static String loadProperty(String name) {
        Properties props = new Properties();
        try {
            props.load(PropertyManager.class.getResourceAsStream(PROP_FILE));
        } catch (IOException e) {
            throw new PropertyLoadException(e.getLocalizedMessage());
        }
        if (name != null) {
            String val = props.getProperty(name);
            return val!=null?val:"";
        }
        return "";
        //TODO добавить ошибку что свойства нет
    }
}

