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
    public void addTest() {
        Specialty addingSpecialty = new Specialty(0, "TestSpecialty");
        try {
            specialtyDao.add(addingSpecialty);
            Optional<Specialty> actualResult = specialtyDao.findById(addingSpecialty.getEntityId());
            Assert.assertTrue(actualResult.isPresent());
            specialtyDao.deleteById(addingSpecialty.getEntityId());
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void isExistsSpecialtyNameTest() {
        Specialty tempSpecialty = new Specialty(0, "TestSpecialty");
        try {
            specialtyDao.add(tempSpecialty);
            boolean result = specialtyDao.isExistsSpecialtyName(tempSpecialty.getSpecialtyName());
            Assert.assertTrue(result);
            specialtyDao.deleteById(tempSpecialty.getEntityId());
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
    }

    @Test
    public void findAllSpecialtiesTest() {
        Specialty tempSpecialty = new Specialty(0, "TestSpecialty");
        try {
            List<Specialty> currentSpecialties = specialtyDao.findAllSpecialties();
            specialtyDao.add(tempSpecialty);
            currentSpecialties.add(tempSpecialty);
            List<Specialty> newSpecialties = specialtyDao.findAllSpecialties();
            specialtyDao.deleteById(tempSpecialty.getEntityId());
            Assert.assertEquals(currentSpecialties, newSpecialties);
        } catch (DaoException e) {
            Assert.fail(e.getMessage());
        }
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