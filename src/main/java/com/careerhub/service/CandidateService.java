package com.careerhub.service;

import com.careerhub.dto.CandidateCreateDTO;
import com.careerhub.dto.CandidateDTO;
import com.careerhub.dto.CandidateUpdateDTO;
import com.careerhub.model.Candidate;
import org.springframework.data.domain.Page;

public interface CandidateService {

    CandidateDTO createCandidate(CandidateCreateDTO candidateCreateDTO);

    void deleteCandidate(Long candidateId);

    CandidateDTO updateCandidate(Long candidateId, CandidateUpdateDTO candidateUpdateDTO);

    Candidate findCandidate(Long candidateId);

    Page<CandidateDTO> getCandidateDTOPage(Long vacancyId, String sort, String order, int page, int size);

    CandidateDTO getCandidateDTO(Long candidateId);
}
