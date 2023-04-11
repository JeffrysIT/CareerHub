package com.careerhub.service.impl;

import com.careerhub.dto.UserDetailsRequestDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.UserDetails;
import com.careerhub.repository.UserDetailsRepository;
import com.careerhub.service.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsRepository userDetailsRepository;
    private final MapStructMapper mapper;

    @Autowired
    public UserDetailsServiceImpl(UserDetailsRepository userDetailsRepository, MapStructMapper mapper) {
        this.userDetailsRepository = userDetailsRepository;
        this.mapper = mapper;
    }

    @Override
    public UserDetails getUserDetailsById(Long id) {
        return userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserDetails not found with id: " + id));
    }

    @Override
    public UserDetails saveUserDetails(UserDetailsRequestDTO userDetailsRequestDTO) {
        UserDetails userDetails = mapper.userDetailsRequestDTOtoUserDetails(userDetailsRequestDTO);
        return userDetailsRepository.save(userDetails);
    }

    @Override
    public void deleteUserDetails(Long id) {
        userDetailsRepository.deleteById(id);
    }

    @Override
    public UserDetails updateUserDetails(Long id, UserDetailsRequestDTO userDetailsRequestDTO) {
        UserDetails userDetails = mapper.userDetailsRequestDTOtoUserDetails(userDetailsRequestDTO);
        UserDetails existingUser = userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserDetails not found with id: " + id));

        existingUser.setName(userDetails.getName());
        existingUser.setEmail(userDetails.getEmail());
        existingUser.setResumeHtml(userDetails.getResumeHtml());
        existingUser.setAppliedVacancies(userDetails.getAppliedVacancies());

        return userDetailsRepository.save(existingUser);
    }
}
