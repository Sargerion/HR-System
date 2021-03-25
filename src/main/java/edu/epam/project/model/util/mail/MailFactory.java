package edu.epam.project.model.util.mail;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import java.util.Properties;

/**
 * Class MailFactory used for authentication in mail and creates mail session.
 * @author Sargerion.
 */
class MailFactory {

    public static final String MAIL_USER_NAME = "mail.user.name";
    public static final String MAIL_USER_PASSWORD = "mail.user.password";

    static Session createMailSession(Properties mailProperties) {
        String userName = mailProperties.getProperty(MAIL_USER_NAME);
        String userPassword = mailProperties.getProperty(MAIL_USER_PASSWORD);
        return Session.getDefaultInstance(
                mailProperties,
                new Authenticator() {
                    @Override
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(userName, userPassword);
                    }
                });
    }
}