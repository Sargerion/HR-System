package edu.epam.project.model.service.impl;

import edu.epam.project.exception.ConnectionException;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.dao.SpecialtyDao;
import edu.epam.project.model.entity.Specialty;
import edu.epam.project.model.pool.ConnectionPool;
import edu.epam.project.model.service.SpecialtyService;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.BDDMockito.given;

public class SpecialtyServiceImplTest {

    @Mock
    private SpecialtyDao specialtyDao;
    private SpecialtyService specialtyService;

    @BeforeClass
    public void initialize() {
        MockitoAnnotations.initMocks(this);
        specialtyService = SpecialtyServiceImpl.getInstance();
    }

    @Test
    public void findByIdTest() {
        int specialtyId = 5;
        Optional<Specialty> expectedResult = Optional.of(new Specialty(specialtyId, "big_dater"));
        Optional<Specialty> actualResult = Optional.empty();
        try {
            given(specialtyDao.findById(specialtyId)).willReturn(expectedResult);
            actualResult = specialtyService.findById(specialtyId);
        } catch (DaoException | ServiceException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(actualResult, expectedResult);
    }

    @Test
    public void findAllSpecialtiesTest() {
        List<Specialty> expectedResult = List.of(
                new Specialty(5, "big_dater"),
                new Specialty(7, "frontend"),
                new Specialty(8, "backend"),
                new Specialty(9, "qa"),
                new Specialty(10, "data-engineer"),
                new Specialty(11, "project-manager")
        );
        List<Specialty> actualResult = new ArrayList<>();
        try {
            given(specialtyDao.findAllSpecialties()).willReturn(expectedResult);
            actualResult = specialtyService.findAllSpecialties();
        } catch (DaoException | ServiceException e) {
            Assert.fail(e.getMessage());
        }
        Assert.assertEquals(actualResult, expectedResult);
    }

    @AfterClass
    public void clear() {
        specialtyService = null;
        try {
            ConnectionPool.getInstance().destroyPool();
        } catch (ConnectionException e) {
            Assert.fail(e.getMessage());
        }
    }
}