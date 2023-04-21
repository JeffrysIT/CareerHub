package com.careerhub.service.impl;

import com.careerhub.dto.CandidateCreateDTO;
import com.careerhub.dto.CandidateDTO;
import com.careerhub.dto.CandidateUpdateDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Candidate;
import com.careerhub.repository.CandidateRepository;
import com.careerhub.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final CandidateRepository candidateRepository;
    private final MapStructMapper mapper;

    @Autowired
    public CandidateServiceImpl(CandidateRepository candidateRepository, MapStructMapper mapper) {
        this.candidateRepository = candidateRepository;
        this.mapper = mapper;
    }

    @Override
    public CandidateDTO getCandidateById(Long id) {
        Candidate candidate = findCandidate(id);
        return mapper.mapToCandidateDTO(candidate);
    }

    @Override
    public CandidateDTO saveCandidate(CandidateCreateDTO candidateCreateDTO) {
        return null;
    }

    @Override
    public void deleteCandidate(Long id) {

    }

    @Override
    public CandidateDTO updateCandidate(Long id, CandidateUpdateDTO candidateUpdateDTO) {
        return null;
    }

    @Override
    public Candidate findCandidate(Long candidateId) {
        if (candidateId == null) throw new IllegalArgumentException("candidateId can't be null or less than 0");
        Candidate candidate = candidateRepository.findByIdAndDeletedIsNull(candidateId);
        if (candidate == null) {
            throw new ResourceNotFoundException("Candidate not found by id: " + candidateId);
        }
        return candidate;
    }
}
