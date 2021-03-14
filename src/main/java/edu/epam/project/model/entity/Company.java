package edu.epam.project.model.entity;

/**
 * Class for encapsulating Company object.
 * @author Sargerion.
 */
public class Company extends Entity {

    private String name;
    private String owner;
    private String address;
    private String hrLogin;

    public Company(Integer companyId, String name, String owner, String address, String hrLogin) {
        super(companyId);
        this.name = name;
        this.owner = owner;
        this.address = address;
        this.hrLogin = hrLogin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getHrLogin() {
        return hrLogin;
    }

    public void setHrLogin(String hrLogin) {
        this.hrLogin = hrLogin;
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
        Company company = (Company) o;
        return name.equals(company.name) && owner.equals(company.owner) && address.equals(company.address) && hrLogin.equals(company.hrLogin);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31
                + (name != null ? name.hashCode() : 0)
                + (owner != null ? owner.hashCode() : 0)
                + (address != null ? address.hashCode() : 0)
                + (hrLogin != null ? hrLogin.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Company{");
        sb.append(super.toString());
        sb.append("name='").append(name).append('\'');
        sb.append(", owner='").append(owner).append('\'');
        sb.append(", address='").append(address).append('\'');
        sb.append(", hrLogin='").append(hrLogin).append('\'');
        sb.append('}');
        return sb.toString();
    }
}