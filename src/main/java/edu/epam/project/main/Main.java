package edu.epam.project.main;

import edu.epam.project.command.CommandName;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserType;
import edu.epam.project.exception.ConnectionException;
import edu.epam.project.pool.ConnectionPool;
import org.intellij.lang.annotations.Language;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.*;

public class Main {

    @Language("SQL")
    private static final String SELECT_USER_BY_LOGIN = "SELECT user_id, user_login, user_password, user_email, " +
            "(SELECT user_types.user_type_name FROM users, user_types WHERE users.user_type_id = user_types.user_type_id) AS user_type_name, " +
            "(SELECT user_statuses.user_status_name FROM users, user_statuses WHERE users.user_status_id = user_statuses.user_status_id) AS user_status_name " +
            "FROM users WHERE user_login = ?;";

    public static void main(String[] args) throws ConnectionException, SQLException {
//        String passw = "Qwerty1";
//        System.out.println(Encrypter.encryptPassword(passw));
//        String login = "sergio_1";
//        try (Connection connection = ConnectionPool.getInstance().getConnection();
//             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_USER_BY_LOGIN)) {
//            preparedStatement.setString(1, login);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            //String foundPassword = resultSet.getString(UsersColumn.PASSWORD);
//            System.out.println("orekgoewg");
//            resultSet.next();
//                System.out.println(resultSet.getInt(1) + "\n" +
//                        resultSet.getString(2) + "\n" +
//                        resultSet.getString(3)+ "\n"+
//                        resultSet.getString(4)+ "\n" +
//                        resultSet.getString(5)+ "\n"+
//                        resultSet.getString(6)+ "\n");
//
//        }
//        ScheduledExecutorService executorService =Executors.newSingleThreadScheduledExecutor();
//        System.out.println("edu.epam.project.exception.ServiceException: Incorrect password".substring("edu.epam.project.exception.ServiceException: Incorrect password".indexOf(":") + 1));
        List<String> list = new ArrayList<>();
    }
}