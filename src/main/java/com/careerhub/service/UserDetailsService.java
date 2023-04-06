package com.careerhub.service;

import com.careerhub.model.UserDetails;

public interface UserDetailsService {

    UserDetails getUserDetailsById(Long id);

    UserDetails saveUserDetails(UserDetails user);

    void deleteUserDetails(Long id);

    UserDetails updateUserDetails(UserDetails user);

}
