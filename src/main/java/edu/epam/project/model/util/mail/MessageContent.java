package edu.epam.project.model.util.mail;

public class MessageContent {

    public static final String ACTIVATE_MESSAGE_FINDER = "You have registered on CringeLinkedIn, please confirm registration by link";
    public static final String ACTIVATE_LINK = "http://localhost:8081/cringelinkedin/controller?command=activate&";
    public static final String ACTIVATE_MESSAGE_HR = "You have registered on CringeLinkedIn, admin accept you, please confirm registration by link";
    public static final String NOTIFICATION_MESSAGE_HR = "You have registered on CringeLinkedIn, please wait when admin accept that you can be HR";
    public static final String NOTIFICATION_BODY = "Now you can just chill out on site";

    private MessageContent() {

    }
}