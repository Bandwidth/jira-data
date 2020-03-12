package jira.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *  SINGLETON
 *
 *  Example usage:
 *  PropertyStore.getInstance().getProperty("key")
 */
public class PropertyStore {

    private static Logger log = LogManager.getRootLogger();
    private static PropertyStore instance;
    private static Properties properties;

    private PropertyStore(){}

    public static PropertyStore getInstance(){

        if(instance == null){
            instance = new PropertyStore();
            properties = new Properties();

            try{
                properties.load(getPropertiesInputStreamFromFile("configJira.properties"));
            } catch (IOException e) {
                log.error(e);
            }
        }
        return instance;
    }

    public String getProperty(final String key){
        return properties.getProperty(key);
    }

    private static InputStream getPropertiesInputStreamFromFile(final String path) throws IOException {

        InputStream input = PropertyStore.class.getClassLoader().getResourceAsStream(path);

        if (input == null) {
            throw new IOException("No properties loaded");
        }
        return input;
    }

}
