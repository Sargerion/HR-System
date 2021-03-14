package edu.epam.project.model.service;

import edu.epam.project.exception.ServiceException;
import edu.epam.project.model.entity.Company;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * The interface CompanyService used to give functionality to Company object and interact with dao.
 * @author Sargerion.
 */
public interface CompanyService extends BaseService<Integer, Company> {
    /**
     * Add company.
     *
     * @param companyName    the company name.
     * @param companyOwner   the company owner.
     * @param companyAddress the company address.
     * @param companyHrLogin the company hr login.
     * @return the map of optional Company object and map of error messages list and map of correct fields.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<Company>, Map<List<String>, Map<String, String>>> addCompany(String companyName, String companyOwner, String companyAddress, String companyHrLogin) throws ServiceException;

    /**
     * Is company hr boolean.
     *
     * @param hrLogin the hr login.
     * @return the boolean, which means that is a company hr.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    boolean isCompanyHr(String hrLogin) throws ServiceException;

    /**
     * Find by hr login optional.
     *
     * @param hrLogin the hr login.
     * @return the optional of Company object.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Optional<Company> findByHrLogin(String hrLogin) throws ServiceException;

    /**
     * Find company name by hr login optional.
     *
     * @param hrLogin the hr login.
     * @return the optional of String object, which means company name.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Optional<String> findCompanyNameByHrLogin(String hrLogin) throws ServiceException;

    /**
     * Find company.
     *
     * @param companyId the company id
     * @return the map of optional Company object and optional of String object, which describe error message.
     * @throws ServiceException if was errors when calling the dao methods.
     */
    Map<Optional<Company>, Optional<String>> findCompanyById(String companyId) throws ServiceException;
}