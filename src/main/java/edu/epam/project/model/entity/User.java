package edu.epam.project.model.entity;

/**
 * Class for encapsulating User object, contains UserType and UserStatus encapsulate enums like fields.
 * @author Sargerion.
 */
public class User extends Entity {

    private String login;
    private String email;
    private UserType type;
    private UserStatus status;
    private String confirmationToken;
    private String avatarName;

    public User() {
    }

    public User(Integer userId, String login, String email, UserType type, UserStatus status) {
        super(userId);
        this.login = login;
        this.email = email;
        this.type = type;
        this.status = status;
    }

    public User(Integer userId, String login, String email, UserType type, UserStatus status, String confirmationToken) {
        super(userId);
        this.login = login;
        this.email = email;
        this.type = type;
        this.status = status;
        this.confirmationToken = confirmationToken;
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

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public String getAvatarName() {
        return avatarName;
    }

    public void setAvatarName(String avatarName) {
        this.avatarName = avatarName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        User user = (User) o;
        return login.equals(user.login) &&
                email.equals(user.email) && type == user.type && status == user.status;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
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
        sb.append(super.toString());
        sb.append(", login='").append(login).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", type=").append(type);
        sb.append(", status=").append(status);
        sb.append(", confirmationToken=").append(confirmationToken);
        sb.append('}');
        return sb.toString();
    }
}