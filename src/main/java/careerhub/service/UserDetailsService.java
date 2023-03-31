package careerhub.service;

import careerhub.model.UserDetails;

import java.util.List;

public interface UserDetailsService {

    UserDetails getUserDetailsById(Long id);

    UserDetails saveUserDetails(UserDetails user);

    void deleteUserDetails(Long id);

    List<UserDetails> getAllUserDetails();

    UserDetails updateUserDetails(Long id, UserDetails user);

}
