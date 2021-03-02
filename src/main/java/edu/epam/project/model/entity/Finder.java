package edu.epam.project.model.entity;


import java.math.BigDecimal;

public class Finder extends Entity{

    private BigDecimal requireSalary;
    private Integer workExperience;
    private Specialty specialty;

    public Finder() {
    }

    public Finder(Integer finderId, BigDecimal requireSalary, Integer workExperience) {
        super(finderId);
        this.requireSalary = requireSalary;
        this.workExperience = workExperience;
    }

    public Finder(Integer finderId, BigDecimal requireSalary, Integer workExperience, Specialty specialty) {
        super(finderId);
        this.requireSalary = requireSalary;
        this.workExperience = workExperience;
        this.specialty = specialty;
    }

    public BigDecimal getRequireSalary() {
        return requireSalary;
    }

    public void setRequireSalary(BigDecimal requireSalary) {
        this.requireSalary = requireSalary;
    }

    public Integer getWorkExperience() {
        return workExperience;
    }

    public void setWorkExperience(Integer workExperience) {
        this.workExperience = workExperience;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
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
        Finder finder = (Finder) o;
        return workExperience.equals(finder.workExperience) &&
                requireSalary.equals(finder.requireSalary) && specialty.equals(finder.specialty);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (requireSalary != null ? requireSalary.hashCode() : 0)
                + (workExperience != null ? workExperience.hashCode() : 0)
                + (specialty != null ? specialty.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Finder{");
        sb.append(super.toString());
        sb.append("requireSalary=").append(requireSalary);
        sb.append(", workExperience=").append(workExperience);
        sb.append(", specialty='").append(specialty).append('\'');
        sb.append('}');
        return sb.toString();
    }
}