package com.careerhub.repository;

import com.careerhub.model.Application;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Application findByIdAndDeletedIsNull(Long id);

}
