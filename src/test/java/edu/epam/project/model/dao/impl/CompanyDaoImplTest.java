package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.CompanyDao;
import edu.epam.project.model.entity.Company;
import edu.epam.project.model.pool.ConnectionPool;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Optional;

public class CompanyDaoImplTest {

    private CompanyDao companyDao;

    @BeforeClass
    public void initialize() {
        companyDao = new CompanyDaoImpl();
    }

    @Test
    public void addTest() throws DaoException {
        Company addingCompany = new Company(0, "TestInc", "TestOwner", "TestTown", "TestLogin");
        companyDao.add(addingCompany);
        Optional<Company> actualResult = companyDao.findById(addingCompany.getEntityId());
        Assert.assertTrue(actualResult.isPresent());
        companyDao.deleteById(actualResult.get().getEntityId());
    }

    @Test
    public void isExistsCompanyNameTest() throws DaoException {
        Company tempCompany = new Company(0, "TestInc", "TestOwner", "TestTown", "TestLogin");
        companyDao.add(tempCompany);
        boolean result = companyDao.isExistsCompanyName(tempCompany.getName());
        Assert.assertTrue(result);
        companyDao.deleteById(tempCompany.getEntityId());
    }

    @Test
    public void isExistsCompanyHrLoginTest() throws DaoException {
        Company tempCompany = new Company(0, "TestInc", "TestOwner", "TestTown", "TestLogin");
        companyDao.add(tempCompany);
        boolean result = companyDao.isExistsCompanyHrLogin(tempCompany.getHrLogin());
        Assert.assertTrue(result);
        companyDao.deleteById(tempCompany.getEntityId());
    }

    @AfterClass
    public void clear() {
        companyDao = null;
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (ConnectionException e) {
            Assert.fail(e.getMessage());
        }
    }
}