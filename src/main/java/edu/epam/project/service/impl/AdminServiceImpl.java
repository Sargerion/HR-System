package edu.epam.project.service.impl;

import edu.epam.project.dao.AdminDao;
import edu.epam.project.dao.UserDao;
import edu.epam.project.dao.impl.AdminDaoImpl;
import edu.epam.project.dao.impl.UsersDaoImpl;
import edu.epam.project.entity.User;
import edu.epam.project.entity.UserStatus;
import edu.epam.project.exception.DaoException;
import edu.epam.project.exception.ServiceException;
import edu.epam.project.service.AdminService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public enum AdminServiceImpl implements AdminService {
    INSTANCE;

    private static final Logger logger = LogManager.getLogger();
    private static final UserDao userDao = new UsersDaoImpl();
    private static final AdminDao adminDao = new AdminDaoImpl();

    @Override
    public boolean activateHR(User user) throws ServiceException {
        boolean activateResult = false;
        User tryActivateUser;
        try {
            Optional<User> foundUser = userDao.findUserByLogin(user.getLogin());
            if (foundUser.isPresent()) {
                tryActivateUser = foundUser.get();
                tryActivateUser.setStatus(UserStatus.ACTIVE);
                userDao.updateStatus(tryActivateUser);
                activateResult = true;
            }
        } catch (DaoException e) {
            logger.error("Can't activate HR");
            throw new ServiceException(e);
        }
        logger.info("HR activate");
        return activateResult;
    }

    @Override
    public List<User> findNotActiveHRList() throws ServiceException {
        List<User> notActiveHRList;
        try {
            notActiveHRList = new ArrayList<>(adminDao.findNotActiveHRs());
        } catch (DaoException e) {
            logger.error(e);
            throw new ServiceException(e);
        }
        return notActiveHRList;
    }


    @Override
    public boolean add(User entity) throws ServiceException {
        return false;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        return null;
    }

    @Override
    public Optional<User> findById(Integer entityId) throws ServiceException {
        return Optional.empty();
    }

    @Override
    public void update(User entity) throws ServiceException {

    }

    @Override
    public boolean delete(User entity) throws ServiceException {
        return false;
    }

    @Override
    public boolean deleteById(Integer entityId) throws ServiceException {
        return false;
    }
}
