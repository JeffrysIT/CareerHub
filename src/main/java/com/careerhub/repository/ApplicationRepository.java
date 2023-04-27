package com.careerhub.repository;

import com.careerhub.model.Application;
import com.careerhub.model.Candidate;
import com.careerhub.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRepository extends CrudRepository<Application, Long> {

    Page<Application> findAllByVacancyAndStatusAndDeletedIsNull(Vacancy vacancy, String statusPresent, Pageable pageRequest);

    Page<Application> findAllByVacancyAndDeletedIsNull(Vacancy vacancy, Pageable pageRequest);

    Application findByVacancyIdAndCandidateIdAndApplicationIdAndDeletedIsNull(Long vacancyId, Long candidateId, Long applicationId);

    Page<Application> findAllByCandidateAndStatusAndDeletedIsNull(Candidate existingCandidate, String validatedStatus, Pageable pageRequest);

    Page<Application> findAllByCandidateAndDeletedIsNull(Candidate existingCandidate, Pageable pageRequest);

    Application findByCandidateIdAndApplicationIdAndDeletedIsNull(Long candidateId, Long applicationId);

}
