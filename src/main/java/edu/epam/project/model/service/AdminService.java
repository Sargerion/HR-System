package edu.epam.project.model.service;

import edu.epam.project.model.entity.Company;
import edu.epam.project.model.entity.User;
import edu.epam.project.exception.ServiceException;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface AdminService extends BaseService<Integer, User> {

    boolean activateHR(User user) throws ServiceException;

    List<User> findNotActiveHRList(int start, int end) throws ServiceException;

    int countUsers() throws ServiceException;

    int countNotActiveHRs() throws ServiceException;

    Map<Optional<Company>, Map<List<String>, Map<String, String>>> addCompany(String companyName, String companyOwner, String companyAddress, String vacancyId, String companyHrLogin) throws ServiceException;
}