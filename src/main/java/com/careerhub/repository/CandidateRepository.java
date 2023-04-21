package com.careerhub.repository;

import com.careerhub.model.Candidate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CandidateRepository extends CrudRepository<Candidate, Long> {
    Candidate findByIdAndDeletedIsNull(Long id);
}
