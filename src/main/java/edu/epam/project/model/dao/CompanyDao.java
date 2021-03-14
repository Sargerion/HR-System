package edu.epam.project.model.dao;

import edu.epam.project.exception.DaoException;
import edu.epam.project.model.entity.Company;

import java.util.Optional;

/**
 * The interface CompanyDao used to interact with database.
 * @author Sargerion.
 */
public interface CompanyDao extends BaseDao<Integer, Company>{
    /**
     * Is exists company name boolean.
     *
     * @param companyName the company name.
     * @return the boolean, which means company name existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    boolean isExistsCompanyName(String companyName) throws DaoException;

    /**
     * Is exists company hr login boolean.
     *
     * @param hrLogin the hr login.
     * @return the boolean, which means company hr login existence.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    boolean isExistsCompanyHrLogin(String hrLogin) throws DaoException;

    /**
     * Find company name by hr login optional.
     *
     * @param hrLogin the hr login.
     * @return the optional of company hr login.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    Optional<String> findCompanyNameByHrLogin(String hrLogin) throws DaoException;

    /**
     * Find company by hr login optional.
     *
     * @param hrLogin the hr login.
     * @return the optional of company.
     * @throws DaoException if database throws SQLException or Connection pool throws ConnectionException.
     */
    Optional<Company> findCompanyByHrLogin(String hrLogin) throws DaoException;
}