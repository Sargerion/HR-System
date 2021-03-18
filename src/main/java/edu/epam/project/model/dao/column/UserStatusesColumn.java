package edu.epam.project.model.dao.column;

/**
 * Class, which gives string constants, which contains real user_statuses table fields.
 * @author Sargerion.
 */
public class UserStatusesColumn {

    public static final String ID = "user_status_id";
    public static final String NAME = "user_status_name";
    public static final int BLOCKED = 1;
    public static final int ACTIVE = 2;
    public static final int NOT_ACTIVE = 3;

    private UserStatusesColumn() {

    }
}