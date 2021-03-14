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
    public void findByIdTest() throws DaoException, ServiceException {
        int companyId = 5;
        Optional<Company> expectedResult = Optional.of(new Company(companyId, "Galiano", "Sargerion", "Minsk", "hr_trier"));
        given(companyDao.findById(companyId)).willReturn(expectedResult);
        Optional<Company> actualResult = companyService.findById(companyId);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void findCompanyNameByHrLoginTest() throws DaoException, ServiceException {
        String hrLogin = "hr_trier";
        Optional<String> expectedResult = Optional.of("Galiano");
        given(companyDao.findCompanyNameByHrLogin(hrLogin)).willReturn(expectedResult);
        Optional<String> actualResult = companyService.findCompanyNameByHrLogin(hrLogin);
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void isCompanyHrTest() throws DaoException, ServiceException {
        String hrLogin = "hr_trier";
        given(companyDao.isExistsCompanyHrLogin(hrLogin)).willReturn(true);
        boolean actualResult = companyService.isCompanyHr(hrLogin);
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