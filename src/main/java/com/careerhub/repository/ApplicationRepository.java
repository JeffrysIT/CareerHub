package com.careerhub.repository;

import com.careerhub.dto.ApplicationDTO;
import com.careerhub.model.Application;
import com.careerhub.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Application findByIdAndDeletedIsNull(Long id);

    Page<Application> findAllByVacancyAndStatusAndDeletedIsNull(Vacancy vacancy, String statusPresent, Pageable pageRequest);

    Page<Application> findAllByVacancyIdAndDeletedIsNull(Long vacancyId, Pageable pageRequest);
}
