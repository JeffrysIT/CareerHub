package com.careerhub.service.impl;

import com.careerhub.dto.CandidateCreateDTO;
import com.careerhub.dto.CandidateDTO;
import com.careerhub.dto.CandidateUpdateDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Candidate;
import com.careerhub.model.Vacancy;
import com.careerhub.repository.CandidateRepository;
import com.careerhub.service.CandidateService;
import com.careerhub.service.VacancyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CandidateServiceImpl implements CandidateService {

    private final VacancyService vacancyService;
    private final CandidateRepository candidateRepository;
    private final MapStructMapper mapper;

    private final List<String> SORT_FIELDS = List.of(
            "created", "first_name", "last_name", "email", "phone"
    );

    public CandidateServiceImpl(
            VacancyService vacancyService,
            CandidateRepository candidateRepository,
            MapStructMapper mapper) {
        this.vacancyService = vacancyService;
        this.candidateRepository = candidateRepository;
        this.mapper = mapper;
    }

    @Override
    public CandidateDTO createCandidate(CandidateCreateDTO candidateCreateDTO) {
        Candidate candidate = mapper.mapToCandidateEntity(candidateCreateDTO);
        candidate.setCreated(LocalDateTime.now());
        Candidate savedCandidate = candidateRepository.save(candidate);
        return mapper.mapToCandidateDTO(savedCandidate);
    }

    @Override
    public void deleteCandidate(Long candidateId) {
        Candidate existingCandidate = findCandidate(candidateId);
        existingCandidate.setDeleted(LocalDateTime.now());
        candidateRepository.save(existingCandidate);
    }

    @Override
    public CandidateDTO updateCandidate(Long candidateId, CandidateUpdateDTO candidateUpdateDTO) {
        Candidate existingCandidate = findCandidate(candidateId);

        existingCandidate.setFirstName(candidateUpdateDTO.getFirstName());
        existingCandidate.setLastName(candidateUpdateDTO.getLastName());
        existingCandidate.setEmail(candidateUpdateDTO.getEmail());
        existingCandidate.setPhoneNumber(candidateUpdateDTO.getPhoneNumber());
        existingCandidate.setUpdated(LocalDateTime.now());

        Candidate savedCandidate = candidateRepository.save(existingCandidate);
        return mapper.mapToCandidateDTO(savedCandidate);
    }

    @Override
    public Page<CandidateDTO> getCandidateDTOPage(Long vacancyId, String sort, String order, int page, int size) {
        Vacancy vacancy = vacancyService.findVacancy(vacancyId);
        validateRequestParameters(sort, order, page, size);
        Pageable pageRequest = createPageRequest(sort, order, page, size);
        return candidateRepository.findAllByVacancyAndDeletedIsNull(vacancy, pageRequest)
                .map(mapper::mapToCandidateDTO);
    }

    @Override
    public CandidateDTO getCandidateDTO(Long candidateId) {
        return mapper.mapToCandidateDTO(findCandidate(candidateId));
    }

    private void validateRequestParameters(String sort, String order, int page, int size) {
        if (sort != null && !SORT_FIELDS.contains(sort))
            throw new IllegalArgumentException("invalid sort type");

        if (!Sort.Direction.ASC.name().equalsIgnoreCase(order) &&
                !Sort.Direction.DESC.name().equalsIgnoreCase(order))
            throw new IllegalArgumentException("invalid order type");

        if (page < 0)
            throw new IllegalArgumentException("invalid page size");

        if (size < 0)
            throw new IllegalArgumentException("invalid size");
    }

    private PageRequest createPageRequest(String sortBy, String order, int page, int size) {
        Sort.Direction orderBy = order.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(orderBy, sortBy);
        return PageRequest.of(page, size, sort);
    }

    @Override
    public Candidate findCandidate(Long candidateId) {
        if (candidateId == null) throw new IllegalArgumentException("Invalid parameter candidateId");
        Candidate candidate = candidateRepository.findByIdAndDeletedIsNull(candidateId);
        if (candidate == null) {
            throw new ResourceNotFoundException("Candidate not found");
        }
        return candidate;
    }
}
