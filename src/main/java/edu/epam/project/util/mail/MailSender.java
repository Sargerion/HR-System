package edu.epam.project.util.mail;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.internet.MimeMessage;
import java.util.Properties;

public enum MailSender {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private MimeMessage mimeMessage;
    private String sendToEmail;
    MailSender(){

    }
}
