package careerhub.service.impl;

import careerhub.exception.ResourceNotFoundException;
import careerhub.model.UserDetails;
import careerhub.repository.UserDetailsRepository;
import careerhub.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository) {
        this.userDetailsRepository = userDetailsRepository;
    }

    @Override
    public UserDetails getUserDetailsById(Long id) {
        return userDetailsRepository.getById(id);
    }

    @Override
    public UserDetails saveUserDetails(UserDetails user) {
        return userDetailsRepository.save(user);
    }

    @Override
    public void deleteUserDetails(Long id) {
        userDetailsRepository.deleteById(id);
    }

    @Override
    public List<UserDetails> getAllUserDetails() {
        return userDetailsRepository.findAll();
    }

    @Override
    public UserDetails updateUserDetails(UserDetails user) {
        Long userId = user.getId();
        UserDetails existingUser = userDetailsRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        existingUser.setName(user.getName());
        existingUser.setEmail(user.getEmail());
        existingUser.setResumeHtml(user.getResumeHtml());
        existingUser.setAppliedVacancies(user.getAppliedVacancies());

        return userDetailsRepository.save(existingUser);
    }
}

