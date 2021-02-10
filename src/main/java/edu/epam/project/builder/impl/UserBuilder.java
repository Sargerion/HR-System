package edu.epam.project.builder.impl;

import edu.epam.project.builder.EntityBuilder;
import edu.epam.project.dao.table.UserStatusesColumn;
import edu.epam.project.dao.table.UserTypesColumn;
import edu.epam.project.dao.table.UsersColumn;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.entity.UserType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.ResultSet;
import java.sql.SQLException;

public enum UserBuilder implements EntityBuilder<User> {

    INSTANCE;
    private static final Logger logger = LogManager.getLogger();

    @Override
    public User build(ResultSet resultSet) throws SQLException {
        Integer userId = resultSet.getInt(UsersColumn.ID);
        String userLogin = resultSet.getString(UsersColumn.LOGIN);
        String userEmail = resultSet.getString(UsersColumn.EMAIL);
        UserType userType = UserType.valueOf(resultSet.getString(UserTypesColumn.NAME));
        UserStatus userStatus = UserStatus.valueOf(resultSet.getString(UserStatusesColumn.NAME));
        return new User(userId, userLogin, userEmail, userType, userStatus);
    }
}