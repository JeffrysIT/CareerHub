package com.careerhub.repository;

import com.careerhub.model.Candidate;
import com.careerhub.model.Resume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends CrudRepository<Resume, Long> {

    Resume findByIdAndDeletedIsNull(Long resumeId);

    List<Resume> findAllByCandidate(Candidate candidateId);

    Resume findByCandidateIdAndIdAndApplicationsVacancyIdAndApplicationsId(Long candidateId, Long resumeId, Long vacancyId, Long applicationId);

    Resume findByCandidateIdAndId(Long candidateId, Long resumeId);
}
