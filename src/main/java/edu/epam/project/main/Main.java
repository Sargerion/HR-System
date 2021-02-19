package edu.epam.project.main;

import edu.epam.project.exception.ConnectionException;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ConnectionException, SQLException {
//        String passw = "Qwerty1";
//        System.out.println(Encrypter.encryptPassword(passw));
//        ScheduledExecutorService executorService =Executors.newSingleThreadScheduledExecutor();
//        System.out.println("edu.epam.project.exception.ServiceException: Incorrect password".substring("edu.epam.project.exception.ServiceException: Incorrect password".indexOf(":") + 1));
        List<String> list = new ArrayList<>();
        String s ="D:/project_directory/user_avatars/smth.jps";
        String name = "user_avatars";
        System.out.println(s.substring(s.lastIndexOf(name)));
//        System.out.println(s.substring(s.lastIndexOf(name) + name.length() + 1,s.lastIndexOf('/')));
        //System.out.println("nullpict.jpg".replaceAll("null", ""));
        //System.out.println(s.substring(s.lastIndexOf('/') + 1));
    }
}