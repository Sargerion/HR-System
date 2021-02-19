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

public class MailSender {

    private static final MailSender instance = new MailSender();
    private static final Logger logger = LogManager.getLogger();
    private MimeMessage mimeMessage;
    private String sendToEmail;
    private String mailSubject;
    private String mailBody;

    private MailSender() {
    }

    public static MailSender getInstance() {
        return instance;
    }

    public void sendActivationFinder(User user) throws MailSendException {
        setSendToEmail(user.getEmail());
        setMailSubject(MessageContent.ACTIVATE_MESSAGE_FINDER);
        setMailBody(MessageContent.ACTIVATE_LINK + "userId=" + user.getEntityId() + "&confirm_token=" + user.getConfirmationToken());
        send();
        logger.info("Activation sent to finder with login -> {}", user.getLogin());
    }

    public void sendActivationHR(User user) throws MailSendException {
        setSendToEmail(user.getEmail());
        setMailSubject(MessageContent.ACTIVATE_MESSAGE_HR);
        setMailBody(MessageContent.ACTIVATE_LINK + "userId=" + user.getEntityId() + "&confirm_token=" + user.getConfirmationToken());
        send();
        logger.info("Activation sent to HR with login -> {}", user.getLogin());
    }

    public void sendNotificationToHR(User user) throws MailSendException {
        setSendToEmail(user.getEmail());
        setMailSubject(MessageContent.NOTIFICATION_MESSAGE_HR);
        setMailBody(MessageContent.NOTIFICATION_BODY);
        send();
        logger.info("Notification sent to HR with login -> {}", user.getLogin());
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