package edu.epam.project.util.mail;

import edu.epam.project.entity.User;
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

    private static final Logger logger = LogManager.getLogger();

    private MimeMessage mimeMessage;
    private String sendToEmail;
    private String mailSubject;
    private String mailBody;

    public MailSender(String sendToEmail) {
        this.sendToEmail = sendToEmail;
    }

    public MailSender(String sendToEmail, String mailSubject, String mailBody) {
        this.sendToEmail = sendToEmail;
        this.mailSubject = mailSubject;
        this.mailBody = mailBody;
    }

    public void send() throws MailSendException {
        try {
            initializeMessage();
            Transport.send(mimeMessage);
        } catch (MessagingException e) {
            throw new MailSendException(e);
        }
    }

    public void sendActivationFinder(User user) throws MailSendException {
        setMailSubject(MessageСontent.ACTIVATE_MESSAGE_FINDER);
        setMailBody(MessageСontent.ACTIVATE_LINK + "userId=" + user.getUserId() + "&confirm_token=" + user.getConfirmationToken());
        send();
        logger.info("Activation sent to finder with login -> {}", user.getLogin());
    }

    public void sendActivationHR(User user) throws MailSendException {
        setMailSubject(MessageСontent.ACTIVATE_MESSAGE_HR);
        setMailBody(MessageСontent.ACTIVATE_LINK + "userId=" + user.getUserId() + "&confirm_token=" + user.getConfirmationToken());
        send();
        logger.info("Activation sent to HR with login -> {}", user.getLogin());
    }

    public void sendNotificationToHR(User user) throws MailSendException {
        setMailSubject(MessageСontent.NOTIFICATION_MESSAGE_HR);
        setMailBody(MessageСontent.NOTIFICATION_BODY);
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
}