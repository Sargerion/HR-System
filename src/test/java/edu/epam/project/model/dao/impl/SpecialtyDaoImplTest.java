package edu.epam.project.model.dao.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.model.dao.SpecialtyDao;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.pool.ConnectionPool;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Optional;

public class SpecialtyDaoImplTest {

    private SpecialtyDao specialtyDao;

    @BeforeClass
    public void initialize() {
        specialtyDao = new SpecialtyDaoImpl();
    }

    @Test
    public void addTest() throws DaoException {
        Specialty addingSpecialty = new Specialty(0, "TestSpecialty");
        specialtyDao.add(addingSpecialty);
        Optional<Specialty> actualResult = specialtyDao.findById(addingSpecialty.getEntityId());
        Assert.assertTrue(actualResult.isPresent());
        specialtyDao.deleteById(addingSpecialty.getEntityId());
    }

    @Test
    public void isExistsSpecialtyNameTest() throws DaoException {
        Specialty tempSpecialty = new Specialty(0, "TestSpecialty");
        specialtyDao.add(tempSpecialty);
        boolean result = specialtyDao.isExistsSpecialtyName(tempSpecialty.getSpecialtyName());
        Assert.assertTrue(result);
        specialtyDao.deleteById(tempSpecialty.getEntityId());
    }

    @Test
    public void findAllSpecialtiesTest() throws DaoException {
        Specialty tempSpecialty = new Specialty(0, "TestSpecialty");
        List<Specialty> currentSpecialties = specialtyDao.findAllSpecialties();
        specialtyDao.add(tempSpecialty);
        currentSpecialties.add(tempSpecialty);
        List<Specialty> newSpecialties = specialtyDao.findAllSpecialties();
        specialtyDao.deleteById(tempSpecialty.getEntityId());
        Assert.assertEquals(currentSpecialties, newSpecialties);
    }

    @AfterClass
    public void clear() {
        specialtyDao = null;
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (ConnectionException e) {
            Assert.fail(e.getMessage());
        }
    }
}