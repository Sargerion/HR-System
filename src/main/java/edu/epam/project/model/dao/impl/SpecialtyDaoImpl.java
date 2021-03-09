package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.SpecialtyDao;
import edu.epam.project.model.dao.builder.EntityBuilder;
import edu.epam.project.model.dao.builder.impl.SpecialtyBuilder;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.pool.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SpecialtyDaoImpl implements SpecialtyDao {

    private static final Logger logger = LogManager.getLogger();
    private final EntityBuilder<Specialty> specialtyBuilder = new SpecialtyBuilder();

    @Language("SQL")
    private static final String INSERT_SPECIALTY = "INSERT INTO specialties (specialty_name) VALUES (?);";

    @Language("SQL")
    private static final String SELECT_SPECIALTY_BY_ID = "SELECT specialty_id, specialty_name FROM specialties WHERE specialty_id = ?;";

    @Language("SQL")
    private static final String SELECT_ALL_SPECIALTIES = "SELECT specialty_id, specialty_name FROM specialties;";

    @Language("SQL")
    private static final String CONTAINS_SPECIALTY_NAME = "SELECT EXISTS(SELECT specialty_name FROM specialties WHERE specialty_name = ?) AS specialty_name_existence;";

    @Override
    public void add(Specialty specialty) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SPECIALTY, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, specialty.getSpecialtyName());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int specialtyId = resultSet.getInt(1);
            specialty.setEntityId(specialtyId);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Specialty> findById(Integer specialtyId) throws DaoException {
        Optional<Specialty> specialty;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_SPECIALTY_BY_ID)) {
            preparedStatement.setInt(1, specialtyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            specialty = Optional.ofNullable(specialtyBuilder.build(resultSet));
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return specialty;
    }

    @Override
    public List<Specialty> findAll(int start, int end) throws DaoException {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Specialty> findAllSpecialties() throws DaoException {
        List<Specialty> specialties = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_SPECIALTIES)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Specialty specialty = specialtyBuilder.build(resultSet);
                specialties.add(specialty);
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return specialties;
    }

    @Override
    public void update(Specialty entity) throws DaoException {

    }

    @Override
    public void deleteById(Integer entityId) throws DaoException {

    }

    @Override
    public boolean isExistsSpecialtyName(String specialtyName) throws DaoException {
        boolean isExists = isExistsStringValue(specialtyName, CONTAINS_SPECIALTY_NAME);
        return isExists;
    }
}