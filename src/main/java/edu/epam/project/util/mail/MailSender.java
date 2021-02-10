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
import java.util.Properties;

public class MailSender {

    private static final Logger logger = LogManager.getLogger();
    public static final String ACTIVATE_MESSAGE = "You have registered on CringeLinkedIn, please confirm registration by link";//todo:replace
    public static final String ACTIVATE_LINK = "http://localhost:8081/Epam_FinalProject_war_exploded/controller?command=activate&";
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

    public void sendActivation(User user) throws MailSendException {
        setMailSubject("Confirmation of registration");
        setMailBody(ACTIVATE_LINK + "userId=" + user.getUserId() + "&confirm_token=" + user.getConfirmationToken());
        send();
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