package com.careerhub.service;

import com.careerhub.dto.UserDetailsCreateDTO;
import com.careerhub.model.UserDetails;

public interface UserDetailsService {

    UserDetails getUserDetailsById(Long id);

    UserDetails saveUserDetails(UserDetailsCreateDTO userDetailsCreateDTO);

    void deleteUserDetails(Long id);

    UserDetails updateUserDetails(Long id, UserDetailsCreateDTO userDetailsCreateDTO);

    UserDetails findUserDetails(Long userDetailsId);

}
