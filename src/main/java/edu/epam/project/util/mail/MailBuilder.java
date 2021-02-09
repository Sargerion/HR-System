package edu.epam.project.util.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MailBuilder {

    private static final Logger logger = LogManager.getLogger();
    private static final String PROPERTIES_PATH = "/property/mail.properties";
    private static final Properties properties = new Properties();
    public static final String ACTIVATE_MESSAGE = "You have registered on CringeLinkedIn, please confirm registration by link";
    public static final String ACTIVATE_LINK = "";

    MailBuilder() {
        try (InputStream input = MailBuilder.class.getResourceAsStream(PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            logger.error(e);
        }
    }

    Properties getProperties() {
        return properties;
    }
}
