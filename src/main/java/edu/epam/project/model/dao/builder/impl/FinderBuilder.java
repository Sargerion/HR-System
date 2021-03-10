package edu.epam.project.model.dao.builder.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.table.FindersColumn;
import edu.epam.project.model.dao.table.SpecialtiesColumn;
import edu.epam.project.model.entity.Finder;
import edu.epam.project.model.entity.Specialty;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FinderBuilder implements EntityBuilder<Finder> {
    @Override
    public Finder build(ResultSet resultSet) throws SQLException {
        Integer finderId = resultSet.getInt(FindersColumn.ID);
        BigDecimal requireSalary = resultSet.getBigDecimal(FindersColumn.REQUIRE_SALARY);
        Integer workExperience = resultSet.getInt(FindersColumn.WORK_EXPERIENCE);
        Integer specialtyId = resultSet.getInt(SpecialtiesColumn.ID);
        String specialtyName = resultSet.getString(SpecialtiesColumn.NAME);
        Specialty specialty = new Specialty(specialtyId, specialtyName);
        String finderWorkStatus = resultSet.getString(FindersColumn.WORK_STATUS);
        return new Finder(finderId, requireSalary, workExperience, specialty, finderWorkStatus);
    }
}