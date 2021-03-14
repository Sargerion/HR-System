package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.CompanyDao;
import edu.epam.project.model.dao.table.CompaniesColumn;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.pool.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.List;
import java.util.Optional;

/**
 * The CompanyDao implementation.
 * @author Sargerion.
 */
public class CompanyDaoImpl implements CompanyDao {

    private static final Logger logger = LogManager.getLogger();

    @Language("SQL")
    private static final String INSERT_COMPANY = "INSERT INTO companies (company_name, company_owner, company_addres, company_hr_login) " +
            "VALUES (?, ?, ?, ?);";

    @Language("SQL")
    private static final String SELECT_COMPANY_BY_ID = "SELECT company_id, company_name, company_owner, company_addres, company_hr_login FROM companies WHERE company_id = ?;";

    @Language("SQL")
    private static final String CONTAINS_COMPANY_NAME = "SELECT EXISTS(SELECT company_name FROM companies WHERE company_name = ?) AS company_name_existence;";

    @Language("SQL")
    private static final String CONTAINS_COMPANY_HR_LOGIN = "SELECT EXISTS(SELECT company_hr_login FROM companies WHERE company_hr_login = ?) AS company_hr_login_existence;";

    @Language("SQL")
    private static final String SELECT_COMPANY_NAME_BY_HR_LOGIN = "SELECT company_name FROM companies WHERE company_hr_login = ?;";

    @Language("SQL")
    private static final String SELECT_COMPANY_BY_HR_LOGIN = "SELECT company_id, company_name, company_owner, company_addres, company_hr_login FROM companies WHERE company_hr_login = ?;";

    @Language("SQL")
    private static final String UPDATE_COMPANY = "UPDATE companies SET company_name = ?, company_owner = ?, company_addres = ? WHERE company_id = ?;";

    @Language("SQL")
    private static final String DELETE_COMPANY = "DELETE FROM companies WHERE company_id = ?;";

    @Override
    public void add(Company company) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_COMPANY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getOwner());
            preparedStatement.setString(3, company.getAddress());
            preparedStatement.setString(4, company.getHrLogin());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int companyId = resultSet.getInt(1);
            company.setEntityId(companyId);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Company> findById(Integer companyId) throws DaoException {
        Optional<Company> company = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMPANY_BY_ID)) {
            preparedStatement.setInt(1, companyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company = Optional.of(buildCompany(resultSet));
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return company;
    }

    @Override
    public void update(Company company) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_COMPANY)) {
            preparedStatement.setString(1, company.getName());
            preparedStatement.setString(2, company.getOwner());
            preparedStatement.setString(3, company.getAddress());
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<String> findCompanyNameByHrLogin(String hrLogin) throws DaoException {
        Optional<String> companyName = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMPANY_NAME_BY_HR_LOGIN)) {
            preparedStatement.setString(1, hrLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                companyName = Optional.of(resultSet.getString(CompaniesColumn.NAME));
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return companyName;
    }

    @Override
    public Optional<Company> findCompanyByHrLogin(String hrLogin) throws DaoException {
        Optional<Company> company = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMPANY_BY_HR_LOGIN)) {
            preparedStatement.setString(1, hrLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                company = Optional.of(buildCompany(resultSet));
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return company;
    }

    @Override
    public void deleteById(Integer companyId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_COMPANY)) {
            preparedStatement.setInt(1, companyId);
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isExistsCompanyName(String companyName) throws DaoException {
        return isExistsStringValue(companyName, CONTAINS_COMPANY_NAME);
    }

    @Override
    public boolean isExistsCompanyHrLogin(String hrLogin) throws DaoException {
        return isExistsStringValue(hrLogin, CONTAINS_COMPANY_HR_LOGIN);
    }

    private Company buildCompany(ResultSet resultSet) throws SQLException {
        Integer companyId = resultSet.getInt(CompaniesColumn.ID);
        String companyName = resultSet.getString(CompaniesColumn.NAME);
        String companyOwner = resultSet.getString(CompaniesColumn.OWNER);
        String companyAddress = resultSet.getString(CompaniesColumn.ADDRESS);
        String companyHrLogin = resultSet.getString(CompaniesColumn.HR_UNIQUE_LOGIN);
        return new Company(companyId, companyName, companyOwner, companyAddress, companyHrLogin);
    }

    @Override
    public List<Company> findAll(int start, int end) throws DaoException {
        throw new UnsupportedOperationException();
    }
}