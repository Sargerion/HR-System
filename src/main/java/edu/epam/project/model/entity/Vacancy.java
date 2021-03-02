package edu.epam.project.model.entity;

import java.math.BigDecimal;

public class Vacancy extends Entity {

    private String name;
    private Specialty specialty;
    private BigDecimal salary;
    private Integer needWorkExperience;

    public Vacancy() {
    }

    public Vacancy(Integer vacancyId, String name, Specialty specialty, BigDecimal salary, Integer needWorkExperience) {
        super(vacancyId);
        this.name = name;
        this.specialty = specialty;
        this.salary = salary;
        this.needWorkExperience = needWorkExperience;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Specialty getSpecialty() {
        return specialty;
    }

    public void setSpecialty(Specialty specialty) {
        this.specialty = specialty;
    }

    public BigDecimal getSalary() {
        return salary;
    }

    public void setSalary(BigDecimal salary) {
        this.salary = salary;
    }

    public Integer getNeedWorkExperience() {
        return needWorkExperience;
    }

    public void setNeedWorkExperience(Integer needWorkExperience) {
        this.needWorkExperience = needWorkExperience;
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
        Vacancy vacancy = (Vacancy) o;
        return name.equals(vacancy.name) && specialty.equals(vacancy.specialty) && salary.equals(vacancy.salary) && needWorkExperience.equals(vacancy.needWorkExperience);
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = result * 31 + (name != null ? name.hashCode() : 0)
                + (specialty != null ? specialty.hashCode() : 0)
                + (salary != null ? salary.hashCode() : 0)
                + (needWorkExperience != null ? needWorkExperience.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Vacancy{");
        sb.append(super.toString());
        sb.append("name= ").append(name).append(", ");
        sb.append("specialty='").append(specialty).append('\'');
        sb.append(", salary=").append(salary);
        sb.append(", needWorkExperience=").append(needWorkExperience);
        sb.append('}');
        return sb.toString();
    }
}