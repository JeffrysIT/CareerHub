package careerhub.service.impl;

import careerhub.exception.ResourceNotFoundException;
import careerhub.model.Company;
import careerhub.repository.CompanyRepository;
import careerhub.service.CompanyService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public Company createCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Company company) {
        return companyRepository.save(company);
    }

    @Override
    public Company updateCompany(Long id, Company company) {
        Company originalCompany = companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found with id " + id));

        if (company != null) {
            originalCompany.setName(company.getName());
            originalCompany.setContactInfo(company.getContactInfo());
            originalCompany.setDescription(company.getDescription());
            originalCompany.setEmployees(company.getEmployees());
            originalCompany.setVacancies(company.getVacancies());
        }

        return companyRepository.save(originalCompany);
    }

    @Override
    public void deleteCompany(Long id) {
        companyRepository.deleteById(id);
    }

    @Override
    public Company getCompanyById(Long id) {
        return companyRepository.findById(id).orElseThrow(() -> new RuntimeException("Company not found"));
    }

    @Override
    public List<Company> getAllCompanies() {
        return companyRepository.findAll();
    }
}