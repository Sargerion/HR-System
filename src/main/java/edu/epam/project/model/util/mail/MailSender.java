package edu.epam.project.model.util.mail;

import edu.epam.project.model.entity.User;
import edu.epam.project.exception.MailSendException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Class MailSender used to delivers messages to users, class used Singleton pattern.
 * @author Sargerion.
 */
public class MailSender {

    private static final Logger logger = LogManager.getLogger();
    private static final MailSender instance = new MailSender();
    private static final String ACTIVATE_MESSAGE = "You have registered on CringeLinkedIn, please confirm registration by link";
    private static final String ACTIVATE_LINK = "http://localhost:8081/cringelinkedin/controller?command=activate&";
    private static final String HIRE_MESSAGE = "Congrats, you've got work in ";
    private static final String NO_HIRE_MESSAGE = "Unfortunately, you've not got work in ";
    private MimeMessage mimeMessage;
    private String sendToEmail;
    private String mailSubject;
    private String mailBody;

    private MailSender() {
    }

    public static MailSender getInstance() {
        return instance;
    }

    public void sendActivationUser(User user) throws MailSendException {
        setSendToEmail(user.getEmail());
        setMailSubject(ACTIVATE_MESSAGE);
        setMailBody(ACTIVATE_LINK + "userId=" + user.getEntityId() + "&confirm_token=" + user.getConfirmationToken());
        send();
        logger.info("Activation sent to user with login -> {}", user.getLogin());
    }

    public void sendHireNotificationFinder(User user, String companyName) throws MailSendException {
        setSendToEmail(user.getEmail());
        setMailSubject(companyName);
        setMailBody(HIRE_MESSAGE + companyName);
        send();
        logger.info("Hire message sent to finder with login -> {}", user.getLogin());
    }

    public void sendNoHireNotificationFinder(User user, String companyName) throws MailSendException {
        setSendToEmail(user.getEmail());
        setMailSubject(companyName);
        setMailBody(NO_HIRE_MESSAGE + companyName);
        send();
        logger.info("No hire message sent to finder with login -> {}", user.getLogin());
    }

    private void initializeMessage() throws MessagingException {
        MailBuilder mailBuilder = new MailBuilder();
        Session mailSession = MailFactory.createMailSession(mailBuilder.getProperties());
        mailSession.setDebug(true);
        mimeMessage = new MimeMessage(mailSession);
        mimeMessage.setSubject(mailSubject);
        mimeMessage.setContent(mailBody, "text/html");
        mimeMessage.setRecipient(Message.RecipientType.TO, new InternetAddress(sendToEmail));
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public String getSendToEmail() {
        return sendToEmail;
    }

    public void setSendToEmail(String sendToEmail) {
        this.sendToEmail = sendToEmail;
    }

    private void send() throws MailSendException {
        try {
            initializeMessage();
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MailSendException(e);
        }
    }
}