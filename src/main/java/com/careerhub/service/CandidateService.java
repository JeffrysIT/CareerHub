package com.careerhub.service;

import com.careerhub.dto.CandidateCreateDTO;
import com.careerhub.dto.CandidateDTO;
import com.careerhub.dto.CandidateUpdateDTO;
import com.careerhub.model.Candidate;

public interface CandidateService {

    CandidateDTO getCandidateById(Long id);

    CandidateDTO createCandidate(CandidateCreateDTO candidateCreateDTO);

    void deleteCandidate(Long id);

    CandidateDTO updateCandidate(Long id, CandidateUpdateDTO candidateUpdateDTO);

    Candidate findCandidate(Long candidateId);

}
