package careerhub.service;

import careerhub.model.Company;

import java.util.List;

public interface CompanyService {

    Company createCompany(Company company);

    Company updateCompany(Company company);

    Company updateCompany(Long id, Company company);

    void deleteCompany(Long id);

    Company getCompanyById(Long id);

    List<Company> getAllCompanies();
}
