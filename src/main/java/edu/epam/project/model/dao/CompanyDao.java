package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Company;

import java.util.Optional;

public interface CompanyDao extends BaseDao<Integer, Company>{
    boolean isExistsCompanyName(String companyName) throws DaoException;

    boolean isExistsCompanyHrLogin(String hrLogin) throws DaoException;

    Optional<String> findCompanyNameByHrLogin(String hrLogin) throws DaoException;

    Optional<Company> findCompanyByHrLogin(String hrLogin) throws DaoException;
}