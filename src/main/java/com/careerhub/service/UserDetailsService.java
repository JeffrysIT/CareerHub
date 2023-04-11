package com.careerhub.service;

import com.careerhub.dto.UserDetailsRequestDTO;
import com.careerhub.model.UserDetails;

public interface UserDetailsService {

    UserDetails getUserDetailsById(Long id);

    UserDetails saveUserDetails(UserDetailsRequestDTO userDetailsRequestDTO);

    void deleteUserDetails(Long id);

    UserDetails updateUserDetails(Long id, UserDetailsRequestDTO userDetailsRequestDTO);

}
