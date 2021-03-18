package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.ApplicationDao;
import edu.epam.project.model.dao.column.*;
import edu.epam.project.model.entity.*;
import edu.epam.project.model.pool.ConnectionPool;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.intellij.lang.annotations.Language;

import java.math.BigDecimal;
import java.sql.*;
import java.util.*;

/**
 * The ApplicationDao implementation.
 * @author Sargerion.
 */
public class ApplicationDaoImpl implements ApplicationDao {

    private static final Logger logger = LogManager.getLogger();

    @Language("SQL")
    private static final String INSERT_APPLICATION = "INSERT INTO applications(application_vacancy_id, application_finder_id) VALUES (?, ?);";

    @Language("SQL")
    private static final String CONTAINS_FINDER_VACANCY_CONNECT = "SELECT EXISTS(SELECT application_vacancy_id, application_finder_id FROM applications " +
            "WHERE application_vacancy_id = ? AND application_finder_id = ?) AS finder_vacancy_connect_existence;";

    @Language("SQL")
    private static final String SELECT_ALL_APPLICATIONS_WITH_LIMIT = "SELECT application_id, vacancy_id, vacancy_name, specialty_id, specialty_name, vacancy_salary_usd, " +
            "vacancy_need_work_experience, company_id, company_name, company_owner, company_addres, company_hr_login, vacancy_is_active, finder_id, finder_require_salary, " +
            "finder_work_experience, finder_work_status FROM applications " +
            "INNER JOIN vacancies ON applications.application_vacancy_id = vacancies.vacancy_id " +
            "INNER JOIN specialties ON vacancies.vacancy_specialty_id = specialties.specialty_id " +
            "INNER JOIN companies ON vacancies.vacancy_company_id = companies.company_id " +
            "INNER JOIN finders ON applications.application_finder_id = finders.finder_id ORDER BY application_id LIMIT ?, ?;";

    @Language("SQL")
    private static final String COUNT_APPLICATIONS = "SELECT COUNT(*) AS applications_count FROM applications;";

    @Language("SQL")
    private static final String DELETE_APPLICATION = "DELETE FROM applications WHERE application_id = ?;";

    @Language("SQL")
    private static final String CONTAINS_APPLICATION_ID = "SELECT EXISTS(SELECT application_id FROM applications WHERE application_id = ?) AS application_existence;";

    @Language("SQL")
    private static final String SELECT_VACANCY_AND_FINDER_ID = "SELECT application_vacancy_id, application_finder_id FROM applications WHERE application_id = ?;";

    @Language("SQL")
    private static final String SELECT_APPLICATION_BY_ID = "SELECT application_id, vacancy_id, vacancy_name, specialty_id, specialty_name, vacancy_salary_usd, " +
            "vacancy_need_work_experience, company_id, company_name, company_owner, company_addres, company_hr_login, vacancy_is_active, finder_id, finder_require_salary, " +
            "finder_work_experience, finder_work_status FROM applications " +
            "INNER JOIN vacancies ON applications.application_vacancy_id = vacancies.vacancy_id " +
            "INNER JOIN specialties ON vacancies.vacancy_specialty_id = specialties.specialty_id " +
            "INNER JOIN companies ON vacancies.vacancy_company_id = companies.company_id " +
            "INNER JOIN finders ON applications.application_finder_id = finders.finder_id WHERE application_id = ?;";

    @Override
    public void add(Application application) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_APPLICATION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, application.getApplicationVacancy().getEntityId());
            preparedStatement.setInt(2, application.getApplicationFinder().getEntityId());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            resultSet.next();
            int applicationId = resultSet.getInt(1);
            application.setEntityId(applicationId);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public Optional<Application> findById(Integer applicationId) throws DaoException {
        Optional<Application> application = Optional.empty();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_APPLICATION_BY_ID)) {
            preparedStatement.setInt(1, applicationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                application = Optional.of(buildApplication(resultSet));
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return application;
    }

    @Override
    public Map<Integer, Integer> findPairVacancyAndFinderId(Integer applicationId) throws DaoException {
        int vacancyId;
        int finderId;
        Map<Integer, Integer> result = new HashMap<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_VACANCY_AND_FINDER_ID)) {
            preparedStatement.setInt(1, applicationId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            vacancyId = resultSet.getInt(ApplicationsColumn.VACANCY_ID);
            finderId = resultSet.getInt(ApplicationsColumn.FINDER_ID);
            result.put(vacancyId, finderId);
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return result;
    }

    @Override
    public List<Application> findAll(int start, int end) throws DaoException {
        List<Application> applications = new ArrayList<>();
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_APPLICATIONS_WITH_LIMIT)) {
            preparedStatement.setInt(1, start);
            preparedStatement.setInt(2, end);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Application application = buildApplication(resultSet);
                applications.add(application);
            }
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return applications;
    }

    @Override
    public boolean isFinderApply(Integer vacancyId, Integer finderId) throws DaoException {
        boolean isExists;
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CONTAINS_FINDER_VACANCY_CONNECT)) {
            preparedStatement.setInt(1, vacancyId);
            preparedStatement.setInt(2, finderId);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            isExists = resultSet.getInt(1) != 0;
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
        return isExists;
    }

    @Override
    public void deleteById(Integer applicationId) throws DaoException {
        try (Connection connection = ConnectionPool.getInstance().getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_APPLICATION)) {
            preparedStatement.setInt(1, applicationId);
            preparedStatement.executeUpdate();
        } catch (ConnectionException | SQLException e) {
            logger.error(e);
            throw new DaoException(e);
        }
    }

    @Override
    public boolean isExistsApplicationId(Integer applicationId) throws DaoException {
        return isExistsId(applicationId, CONTAINS_APPLICATION_ID);
    }

    @Override
    public int countApplications() throws DaoException {
        return countEntities(COUNT_APPLICATIONS);
    }

    private Application buildApplication(ResultSet resultSet) throws SQLException {
        Integer applicationId = resultSet.getInt(ApplicationsColumn.ID);
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
        Vacancy vacancy = new Vacancy(vacancyId, vacancyName, specialty, vacancySalary, vacancyNeedWorkExperience, company, isVacancyActive);
        Integer finderId = resultSet.getInt(FindersColumn.ID);
        BigDecimal requireSalary = resultSet.getBigDecimal(FindersColumn.REQUIRE_SALARY);
        Integer workExperience = resultSet.getInt(FindersColumn.WORK_EXPERIENCE);
        String finderWorkStatus = resultSet.getString(FindersColumn.WORK_STATUS);
        Finder finder = new Finder(finderId, requireSalary, workExperience, specialty, finderWorkStatus);
        return new Application(applicationId, vacancy, finder);
    }

    @Override
    public void update(Application entity) throws DaoException {
        throw new UnsupportedOperationException();
    }
}