package edu.epam.project.model.dao.builder.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.entity.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyBuilder implements EntityBuilder<Company> {
    @Override
    public Company build(ResultSet resultSet) throws SQLException {
        return null;
    }
}
