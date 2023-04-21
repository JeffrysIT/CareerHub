package com.careerhub.service.impl;

import com.careerhub.dto.UserDetailsCreateDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.UserDetails;
import com.careerhub.model.Vacancy;
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
    public UserDetails saveUserDetails(UserDetailsCreateDTO userDetailsCreateDTO) {
        UserDetails userDetails = mapper.userDetailsRequestDTOtoUserDetails(userDetailsCreateDTO);
        return userDetailsRepository.save(userDetails);
    }

    @Override
    public void deleteUserDetails(Long id) {
        userDetailsRepository.deleteById(id);
    }

    @Override
    public UserDetails updateUserDetails(Long id, UserDetailsCreateDTO userDetailsCreateDTO) {
        UserDetails userDetails = mapper.userDetailsRequestDTOtoUserDetails(userDetailsCreateDTO);
        UserDetails existingUser = userDetailsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("UserDetails not found with id: " + id));

        return userDetailsRepository.save(existingUser);
    }

    @Override
    public UserDetails findUserDetails(Long userDetailsId) {
        if (userDetailsId == null) throw new IllegalArgumentException("userDetailsId can't be null or less than 0");
        UserDetails userDetails = userDetailsRepository.findByIdAndDeletedIsNull(userDetailsId);
        if (userDetails == null) {
            throw new ResourceNotFoundException("User Details not found by id: " + userDetailsId);
        }
        return userDetails;
    }
}
