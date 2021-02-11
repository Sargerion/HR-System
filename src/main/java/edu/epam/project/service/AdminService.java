package edu.epam.project.service;

import edu.epam.project.entity.User;
import edu.epam.project.exception.ServiceException;

import java.util.List;

public interface AdminService extends BaseService<Integer, User> {

    boolean activateHR(User user) throws ServiceException;

    List<User> findNotActiveHRList() throws ServiceException;
}