package com.careerhub.repository;

import com.careerhub.model.Candidate;
import com.careerhub.model.Vacancy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    Candidate findByIdAndDeletedIsNull(Long id);

    Page<Candidate> findAllByApplicationsVacancyAndDeletedIsNull(Vacancy vacancy, Pageable pageRequest);
}
