package com.careerhub.repository;

import com.careerhub.model.Application;
import com.careerhub.model.Vacancy;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Application findByIdAndDeletedIsNull(Long id);

    Application findByVacancyAndStatus(Vacancy vacancy, String statusPresent, PageRequest pageRequest);
}
