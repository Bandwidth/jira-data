package jira.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Configs {

    private static Logger log = LogManager.getRootLogger();

    public static Properties getJiraProperties(){

        Properties prop = new Properties();

        try (
            InputStream input = Configs.class.getClassLoader().getResourceAsStream("config.properties")) {

            if (input == null) {
                log.error("No properties loaded");
                return null;
            }

            prop.load(input);

        } catch (IOException e) {
            log.error(e);
        }

        return prop;
    }








}
