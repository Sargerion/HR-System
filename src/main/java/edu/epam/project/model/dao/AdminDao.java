package edu.epam.project.model.dao;

import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.User;
import edu.epam.project.exception.DaoException;

public interface AdminDao extends BaseDao<Integer, User> {
    int countUsers() throws DaoException;

    void addCompany(Company company) throws DaoException;

    boolean existsCompanyName(String companyName) throws DaoException;

    boolean existsCompanyHrLogin(String companyHrLogin) throws DaoException;

    void addSpecialty(Specialty specialty) throws DaoException;

    boolean existsSpecialtyName(String specialtyName) throws DaoException;
}