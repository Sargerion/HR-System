package edu.epam.project.model.dao.builder.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.table.UserStatusesColumn;
import edu.epam.project.model.dao.table.UserTypesColumn;
import edu.epam.project.model.dao.table.UsersColumn;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.UserStatus;
import edu.epam.project.model.entity.UserType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserBuilder implements EntityBuilder<User> {
    @Override
    public User build(ResultSet resultSet) throws SQLException {
        Integer userId = resultSet.getInt(UsersColumn.ID);
        String userLogin = resultSet.getString(UsersColumn.LOGIN);
        String userEmail = resultSet.getString(UsersColumn.EMAIL);
        UserType userType = UserType.valueOf(resultSet.getString(UserTypesColumn.NAME));
        UserStatus userStatus = UserStatus.valueOf(resultSet.getString(UserStatusesColumn.NAME));
        String confirmationToken = resultSet.getString(UsersColumn.CONFIRMATION_TOKEN);
        return new User(userId, userLogin, userEmail, userType, userStatus, confirmationToken);
    }
}