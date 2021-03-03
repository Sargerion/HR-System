package edu.epam.project.model.dao.builder.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.table.SpecialtiesColumn;
import edu.epam.project.model.dao.table.VacanciesColumn;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.Vacancy;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VacancyBuilder implements EntityBuilder<Vacancy> {
    @Override
    public Vacancy build(ResultSet resultSet) throws SQLException {
        Integer vacancyId = resultSet.getInt(VacanciesColumn.ID);
        String vacancyName = resultSet.getString(VacanciesColumn.NAME);
        Integer specialtyId = resultSet.getInt(SpecialtiesColumn.ID);
        String specialtyName = resultSet.getString(SpecialtiesColumn.NAME);
        Specialty specialty = new Specialty(specialtyId, specialtyName);
        BigDecimal vacancySalary = resultSet.getBigDecimal(VacanciesColumn.SALARY);
        Integer vacancyNeedWorkExperience = resultSet.getInt(VacanciesColumn.NEED_WORK_EXPERIENCE);
        return new Vacancy(vacancyId, vacancyName, specialty, vacancySalary, vacancyNeedWorkExperience);
    }
}