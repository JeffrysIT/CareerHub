package careerhub.service;

import careerhub.model.UserDetails;

import java.util.List;

public interface UserDetailsService {

    UserDetails getUserDetailsById(Long id);

    UserDetails saveUserDetails(UserDetails user);

    void deleteUserDetails(Long id);

    UserDetails updateUserDetails(UserDetails user);

}
