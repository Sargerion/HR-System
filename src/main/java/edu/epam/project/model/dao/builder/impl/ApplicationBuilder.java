package edu.epam.project.model.dao.builder.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.table.*;
import edu.epam.project.model.entity.*;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ApplicationBuilder implements EntityBuilder<Application> {
    @Override
    public Application build(ResultSet resultSet) throws SQLException {
        Integer applicationId = resultSet.getInt(ApplicationsColumn.ID);
        Integer vacancyId = resultSet.getInt(VacanciesColumn.ID);
        String vacancyName = resultSet.getString(VacanciesColumn.NAME);
        Integer specialtyId = resultSet.getInt(SpecialtiesColumn.ID);
        String specialtyName = resultSet.getString(SpecialtiesColumn.NAME);
        Specialty specialty = new Specialty(specialtyId, specialtyName);
        BigDecimal vacancySalary = resultSet.getBigDecimal(VacanciesColumn.SALARY);
        Integer vacancyNeedWorkExperience = resultSet.getInt(VacanciesColumn.NEED_WORK_EXPERIENCE);
        Integer companyId = resultSet.getInt(CompaniesColumn.ID);
        String companyName = resultSet.getString(CompaniesColumn.NAME);
        String companyOwner = resultSet.getString(CompaniesColumn.OWNER);
        String companyAddress = resultSet.getString(CompaniesColumn.ADDRESS);
        String companyHrLogin = resultSet.getString(CompaniesColumn.HR_UNIQUE_LOGIN);
        Company company = new Company(companyId, companyName, companyOwner, companyAddress, companyHrLogin);
        boolean isVacancyActive = resultSet.getBoolean(VacanciesColumn.VACANCY_IS_ACTIVE);
        Vacancy vacancy = new Vacancy(vacancyId, vacancyName, specialty, vacancySalary, vacancyNeedWorkExperience, company, isVacancyActive);
        Integer finderId = resultSet.getInt(FindersColumn.ID);
        BigDecimal requireSalary = resultSet.getBigDecimal(FindersColumn.REQUIRE_SALARY);
        Integer workExperience = resultSet.getInt(FindersColumn.WORK_EXPERIENCE);
        String finderWorkStatus = resultSet.getString(FindersColumn.WORK_STATUS);
        Finder finder = new Finder(finderId, requireSalary, workExperience, specialty, finderWorkStatus);
        return new Application(applicationId, vacancy, finder);
    }
}