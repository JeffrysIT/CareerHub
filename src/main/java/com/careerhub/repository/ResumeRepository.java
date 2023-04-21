package com.careerhub.repository;

import com.careerhub.dto.ResumeDTO;
import com.careerhub.model.Resume;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResumeRepository extends CrudRepository<Resume, Long> {
    Resume findByIdAndDeleteIsNull(Long resumeId);

    List<Resume> findAllByCandidate(Long candidateId);
}
