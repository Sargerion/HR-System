package edu.epam.project.entity;

public class User extends Entity {

    private Integer userId;
    public String login;
    private String email;
    private UserType type;
    private UserStatus status;
    private String activateKey;

    public User() {

    }

    public User(Integer userId, String login, String email, UserType type, UserStatus status) {
        this.userId = userId;
        this.login = login;
        this.email = email;
        this.type = type;
        this.status = status;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getActivateKey() {
        return activateKey;
    }

    public void setActivateKey(String activateKey) {
        this.activateKey = activateKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        User user = (User) o;
        return userId.equals(user.userId) && login.equals(user.login) &&
                email.equals(user.email) && type == user.type && status == user.status;
    }

    @Override
    public int hashCode() {
        int result = (userId != null ? userId.hashCode() : 0);
        result = 31 * result +
                (login != null ? login.hashCode() : 0) +
                (email != null ? email.hashCode() : 0) +
                (type != null ? type.hashCode() : 0) +
                (status != null ? status.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("User{");
        sb.append("userId=").append(userId);
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", activateKey=").append(activateKey);
        sb.append('}');
        return sb.toString();
    }
}