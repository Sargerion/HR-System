package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.VacancyDao;
import edu.epam.project.model.dao.table.CompaniesColumn;
import edu.epam.project.model.dao.table.SpecialtiesColumn;
import edu.epam.project.model.dao.table.VacanciesColumn;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.entity.Vacancy;
import edu.epam.project.model.pool.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class VacancyDaoImpl implements VacancyDao {

    private static final Logger logger = LogManager.getLogger();

    @Language("SQL")
    private static final String INSERT_VACANCY = "INSERT INTO vacancies(vacancy_name, vacancy_specialty_id, vacancy_salary_usd, vacancy_need_work_experience, vacancy_company_id, vacancy_is_active) " +
            "VALUES (?, ?, ?, ?, ?, ?);";

    @Language("SQL")
    private static final String SELECT_VACANCY_BY_ID = "SELECT vacancy_id, vacancy_name, specialty_id, specialty_name, vacancy_salary_usd, " +
            "vacancy_need_work_experience, company_id, company_name, company_owner, company_addres, company_hr_login, vacancy_is_active FROM vacancies " +
            "INNER JOIN specialties ON vacancies.vacancy_specialty_id = specialties.specialty_id " +
            "INNER JOIN companies ON vacancies.vacancy_company_id = companies.company_id WHERE vacancy_id = ?;";

    @Language("SQL")
    private static final String SELECT_ALL_VACANCIES_WITH_LIMIT = "SELECT vacancy_id, vacancy_name, specialty_id, specialty_name, vacancy_salary_usd, " +
            "vacancy_need_work_experience, company_id, company_name, company_owner, company_addres, company_hr_login, vacancy_is_active FROM vacancies " +
            "INNER JOIN specialties ON vacancies.vacancy_specialty_id = specialties.specialty_id " +
            "INNER JOIN companies ON vacancies.vacancy_company_id = companies.company_id ORDER BY vacancy_id LIMIT ?, ?;";

    @Language("SQL")
    private static final String COUNT_VACANCIES = "SELECT COUNT(*) AS vacancies_count FROM vacancies";

    @Language("SQL")
    private static final String DELETE_VACANCY_BY_ID = "DELETE FROM vacancies WHERE vacancy_id = ?;";

    @Language("SQL")
    private static final String UPDATE_VACANCY_STATUS = "UPDATE vacancies SET vacancy_is_active = ? WHERE vacancy_id = ?;";

    @Override
    public void add(Vacancy vacancy) throws DaoException {
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
    public Optional<Vacancy> findById(Integer vacancyId) throws DaoException {
        Optional<Vacancy> vacancy = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VACANCY_BY_ID)) {
            preparedStatement.setInt(1, vacancyId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                vacancy = Optional.of(buildVacancy(resultSet));
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return vacancy;
    }

    @Override
    public void update(Vacancy entity) throws DaoException {

    }

    @Override
    public List<Vacancy> findAll(int start, int end) throws DaoException {
        List<Vacancy> vacancies = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_VACANCIES_WITH_LIMIT)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Vacancy vacancy = buildVacancy(resultSet);
                vacancies.add(vacancy);
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return vacancies;
    }

    @Override
    public void deleteById(Integer vacancyId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_VACANCY_BY_ID)) {
            preparedStatement.setInt(1, vacancyId);
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public void updateVacancyStatus(boolean isActive, Integer vacancyId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_VACANCY_STATUS)) {
            preparedStatement.setBoolean(1, isActive);
            preparedStatement.setInt(2, vacancyId);
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public int countVacancies() throws DaoException {
        return countEntities(COUNT_VACANCIES);
    }

    private Vacancy buildVacancy(ResultSet resultSet) throws SQLException {
        Integer vacancyId = resultSet.getInt(VacanciesColumn.ID);
        String vacancyName = resultSet.getString(VacanciesColumn.NAME);
        Integer specialtyId = resultSet.getInt(SpecialtiesColumn.ID);
        String specialtyName = resultSet.getString(SpecialtiesColumn.NAME);
        Specialty specialty = new Specialty(specialtyId, specialtyName);
        BigDecimal vacancySalary = resultSet.getBigDecimal(VacanciesColumn.SALARY);
        Integer vacancyNeedWorkExperience = resultSet.getInt(VacanciesColumn.NEED_WORK_EXPERIENCE);
        Integer companyId = resultSet.getInt(CompaniesColumn.ID);
        String companyName = resultSet.getString(CompaniesColumn.NAME);
        String companyOwner = resultSet.getString(CompaniesColumn.OWNER);
        String companyAddress = resultSet.getString(CompaniesColumn.ADDRESS);
        String companyHrLogin = resultSet.getString(CompaniesColumn.HR_UNIQUE_LOGIN);
        Company company = new Company(companyId, companyName, companyOwner, companyAddress, companyHrLogin);
        boolean isVacancyActive = resultSet.getBoolean(VacanciesColumn.VACANCY_IS_ACTIVE);
        return new Vacancy(vacancyId, vacancyName, specialty, vacancySalary, vacancyNeedWorkExperience, company, isVacancyActive);
    }
}