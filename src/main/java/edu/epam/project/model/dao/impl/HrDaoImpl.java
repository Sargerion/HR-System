package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.HrDao;
import edu.epam.project.model.entity.Hr;
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
    private static final String INSERT_VACANCY = "INSERT INTO vacancies(vacancy_name, vacancy_specialty_id, vacancy_salary_usd, vacancy_need_work_experience) " +
            "VALUES (?, ?, ?, ?)";

    private HrDaoImpl() {
    }

    public static HrDaoImpl getInstance() {
        return instance;
    }

    @Override
    public void add(Hr entity) throws DaoException {

    }

    @Override
    public Optional<Hr> findById(Integer entityId) throws DaoException {
        return Optional.empty();
    }

    @Override
    public List<Hr> findAll(int start, int end) throws DaoException {
        return null;
    }

    @Override
    public void update(Hr entity) throws DaoException {

    }

    @Override
    public boolean delete(Hr entity) throws DaoException {
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) throws DaoException {
        return false;
    }

    @Override
    public void addVacancy(Vacancy vacancy) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_VACANCY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, vacancy.getName());
            preparedStatement.setInt(2, vacancy.getSpecialty().getEntityId());
            preparedStatement.setBigDecimal(3, vacancy.getSalary());
            preparedStatement.setInt(4, vacancy.getNeedWorkExperience());
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
}