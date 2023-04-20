package com.careerhub.repository;

import com.careerhub.model.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDetailsRepository extends CrudRepository<UserDetails, Long> {
    UserDetails findByIdAndDeletedIsNull(Long id);
}
