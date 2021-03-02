package edu.epam.project.model.entity;

public class Hr extends Entity {

    private String login;

    public Hr() {
    }

    public Hr(Integer hrId, String login) {
        super(hrId);
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
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
        Hr hr = (Hr) o;
        return login.equals(hr.login);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (login != null ? login.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Hr{");
        sb.append(super.toString());
        sb.append("login='").append(login).append('\'');
        sb.append('}');
        return sb.toString();
    }
}