package edu.epam.project.model.entity;

/**
 * Class for encapsulating Specialty object.
 * @author Sargerion.
 */
public class Specialty extends Entity{

    private String specialtyName;

    public Specialty() {
    }

    public Specialty(Integer specialtyId, String specialtyName) {
        super(specialtyId);
        this.specialtyName = specialtyName;
    }

    public String getSpecialtyName() {
        return specialtyName;
    }

    public void setSpecialtyName(String specialtyName) {
        this.specialtyName = specialtyName;
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
        Specialty specialty = (Specialty) o;
        return specialtyName.equals(specialty.specialtyName);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (specialtyName != null ? specialtyName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Specialty{");
        sb.append(super.toString());
        sb.append("specialtyName='").append(specialtyName).append('\'');
        sb.append('}');
        return sb.toString();
    }
}