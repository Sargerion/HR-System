package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.HrDao;
import edu.epam.project.model.dao.table.CompaniesColumn;
import edu.epam.project.model.entity.User;
import edu.epam.project.model.entity.Vacancy;

import edu.epam.project.model.pool.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class HrDaoImpl implements HrDao {

    private static final HrDaoImpl instance = new HrDaoImpl();
    private static final Logger logger = LogManager.getLogger();

    @Language("SQL")
    private static final String INSERT_VACANCY = "INSERT INTO vacancies(vacancy_name, vacancy_specialty_id, vacancy_salary_usd, vacancy_need_work_experience, vacancy_company_id, vacancy_is_active) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String SELECT_COMPANY_NAME_BY_LOGIN = "SELECT company_name FROM companies WHERE company_hr_login = ?;";

    private HrDaoImpl() {
    }

    public static HrDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void add(User entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Optional<User> findById(Integer entityId) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<User> findAll(int start, int end) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void update(User entity) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public void addVacancy(Vacancy vacancy) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VACANCY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, vacancy.getName());
            preparedStatement.setInt(2, vacancy.getSpecialty().getEntityId());
            preparedStatement.setBigDecimal(3, vacancy.getSalary());
            preparedStatement.setInt(4, vacancy.getNeedWorkExperience());
            preparedStatement.setInt(5, vacancy.getVacancyCompany().getEntityId());
            preparedStatement.setBoolean(6, vacancy.isVacancyActive());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int vacancyId = resultSet.getInt(1);
            vacancy.setEntityId(vacancyId);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public String findCompanyNameByLogin(String hrLogin) throws DaoException {
        String companyName;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COMPANY_NAME_BY_LOGIN)) {
            preparedStatement.setString(1, hrLogin);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            companyName = resultSet.getString(CompaniesColumn.NAME);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return companyName;
    }
}