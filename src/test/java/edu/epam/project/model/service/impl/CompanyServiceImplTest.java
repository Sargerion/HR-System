package edu.epam.project.model.service.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.CompanyDao;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.pool.ConnectionPool;
import edu.epam.project.model.service.CompanyService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class CompanyServiceImplTest {

    @Mock
    private CompanyDao companyDao;
    private CompanyService companyService;

    @BeforeClass
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        companyService = CompanyServiceImpl.getInstance();
    }

    @Test
    public void findByIdTest() {
        int companyId = 5;
        Optional<Company> expectedResult = Optional.of(new Company(companyId, "Galiano", "Sargerion", "Minsk", "hr_trier"));
        Optional<Company> actualResult = Optional.empty();
        try {
            given(companyDao.findById(companyId)).willReturn(expectedResult);
            actualResult = companyService.findById(companyId);
        } catch (DaoException | ServiceException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void findCompanyNameByHrLoginTest() {
        String hrLogin = "hr_trier";
        Optional<String> expectedResult = Optional.of("Galiano");
        Optional<String> actualResult = Optional.empty();
        try {
            given(companyDao.findCompanyNameByHrLogin(hrLogin)).willReturn(expectedResult);
            actualResult = companyService.findCompanyNameByHrLogin(hrLogin);
        } catch (DaoException | ServiceException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void isCompanyHrTest() {
        String hrLogin = "hr_trier";
        boolean actualResult = false;
        try {
            given(companyDao.isExistsCompanyHrLogin(hrLogin)).willReturn(true);
            actualResult = companyService.isCompanyHr(hrLogin);
        } catch (DaoException | ServiceException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertTrue(actualResult);
    }

    @AfterClass
    public void clear() {
        companyService = null;
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (ConnectionException e) {
            Assert.fail(e.getMessage());
        }
    }
}