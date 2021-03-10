package edu.epam.project.model.entity;

import java.math.BigDecimal;

public class Finder extends Entity {

    private static final String NOT_HIRE_STATUS = "Not Hire";
    private BigDecimal requireSalary;
    private Integer workExperience;
    private Specialty specialty;
    private String workStatus;

    public Finder() {
    }

    public Finder(Integer finderId, BigDecimal requireSalary, Integer workExperience, Specialty specialty, String workStatus) {
        super(finderId);
        this.requireSalary = requireSalary;
        this.workExperience = workExperience;
        this.specialty = specialty;
        this.workStatus = workStatus;
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

    public String getWorkStatus() {
        return workStatus;
    }

    public void setWorkStatus(String workStatus) {
        this.workStatus = workStatus;
    }

    public static String getNotHireStatus() {
        return NOT_HIRE_STATUS;
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
        return workExperience.equals(finder.workExperience) && requireSalary.equals(finder.requireSalary) &&
                specialty.equals(finder.specialty) && workStatus.equals(finder.workStatus);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (requireSalary != null ? requireSalary.hashCode() : 0)
                + (workExperience != null ? workExperience.hashCode() : 0)
                + (specialty != null ? specialty.hashCode() : 0)
                + (workStatus != null ? workStatus.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Finder{");
        sb.append(super.toString());
        sb.append("requireSalary=").append(requireSalary);
        sb.append(", workExperience=").append(workExperience);
        sb.append(", specialty='").append(specialty).append('\'');
        sb.append(", finderWorkStatus='").append(workStatus).append('\'');
        sb.append('}');
        return sb.toString();
    }
}