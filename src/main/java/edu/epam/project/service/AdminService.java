package edu.epam.project.service;

import edu.epam.project.entity.User;
import edu.epam.project.exception.ServiceException;

public interface AdminService extends BaseService<Integer, User> {

    boolean activateHR(User user) throws ServiceException;

}