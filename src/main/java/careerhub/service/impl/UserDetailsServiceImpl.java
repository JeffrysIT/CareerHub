package careerhub.service.impl;

import careerhub.exception.ResourceNotFoundException;
import careerhub.model.Company;
import careerhub.model.UserDetails;
import careerhub.repository.UserRepository;
import careerhub.service.CompanyService;
import careerhub.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final CompanyService companyService;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, CompanyService companyService) {
        this.userRepository = userRepository;
        this.companyService = companyService;
    }

    @Override
    public UserDetails getUserDetailsById(Long id) {
        return userRepository.getById(id);
    }

    @Override
    public UserDetails saveUserDetails(UserDetails user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUserDetails(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<UserDetails> getAllUserDetails() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails updateUserDetails(Long id, UserDetails user) {
        UserDetails existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setResumeHtml(user.getResumeHtml());

        Company existingCompany = companyService.getCompanyById(user.getCompanyId());
        existingUser.setCompanyId(existingCompany.getId());

        return userRepository.save(existingUser);
    }
}

