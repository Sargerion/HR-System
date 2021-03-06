package edu.epam.project.model.dao.builder.impl;

import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.table.CompaniesColumn;
import edu.epam.project.model.entity.Company;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CompanyBuilder implements EntityBuilder<Company> {
    @Override
    public Company build(ResultSet resultSet) throws SQLException {
        Integer companyId = resultSet.getInt(CompaniesColumn.ID);
        String companyName = resultSet.getString(CompaniesColumn.NAME);
        String companyOwner = resultSet.getString(CompaniesColumn.OWNER);
        String companyAddress = resultSet.getString(CompaniesColumn.ADDRESS);
        String companyHrLogin = resultSet.getString(CompaniesColumn.HR_UNIQUE_LOGIN);
        return new Company(companyId, companyName, companyOwner, companyAddress, companyHrLogin);
    }
}