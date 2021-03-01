package edu.epam.project.model.dao.builder.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.table.SpecialtiesColumn;
import edu.epam.project.model.entity.Specialty;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SpecialtyBuilder implements EntityBuilder<Specialty> {
    @Override
    public Specialty build(ResultSet resultSet) throws SQLException {
        Integer specialtyId = resultSet.getInt(SpecialtiesColumn.ID);
        String specialtyName = resultSet.getString(SpecialtiesColumn.NAME);
        return new Specialty(specialtyId, specialtyName);
    }
}