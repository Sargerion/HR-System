package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Company;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface CompanyService extends BaseService<Integer, Company> {
    Map<Optional<Company>, Map<List<String>, Map<String, String>>> addCompany(String companyName, String companyOwner, String companyAddress, String companyHrLogin) throws ServiceException;

    boolean isCompanyHr(String hrLogin) throws ServiceException;

    Optional<Company> findByHrLogin(String hrLogin) throws ServiceException;

    Optional<String> findCompanyNameByHrLogin(String hrLogin) throws ServiceException;

    Map<Optional<Company>, Optional<String>> findCompanyById(String companyId) throws ServiceException;
}