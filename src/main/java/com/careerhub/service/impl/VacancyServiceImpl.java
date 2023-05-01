package com.careerhub.service.impl;

import com.careerhub.dto.VacancyCreateDTO;
import com.careerhub.dto.VacancyUpdateDTO;
import com.careerhub.dto.VacancyDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Candidate;
import com.careerhub.model.Vacancy;
import com.careerhub.repository.VacancyRepository;
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
public class VacancyServiceImpl implements VacancyService {

    private final CandidateService candidateService;
    private final VacancyRepository vacancyRepository;
    private final MapStructMapper mapper;

    private final List<String> SORT_FIELDS =
            List.of("created", "updated", "title", "location", "viewed", "applied");
    public final static int MAX_QUERY_LEN = 1000;

    public VacancyServiceImpl(
            CandidateService candidateService, VacancyRepository vacancyRepository,
            MapStructMapper mapper
    ) {
        this.candidateService = candidateService;
        this.vacancyRepository = vacancyRepository;
        this.mapper = mapper;
    }

    @Override
    public VacancyDTO createVacancy(VacancyCreateDTO vacancyDto) {
        Vacancy vacancy = mapper.mapToVacancyEntity(vacancyDto);
        vacancy.setCreated(LocalDateTime.now());
        vacancy = vacancyRepository.save(vacancy);
        return mapper.mapToVacancyDTO(vacancy);
    }

    @Override
    public VacancyDTO updateVacancy(Long vacancyId, VacancyUpdateDTO vacancyUpdateDTO) {
        Vacancy existingVacancy = findVacancy(vacancyId);

        existingVacancy.setTitle(vacancyUpdateDTO.getTitle());
        existingVacancy.setDescription(vacancyUpdateDTO.getDescription());
        existingVacancy.setSalary(vacancyUpdateDTO.getSalary());
        existingVacancy.setLocation(vacancyUpdateDTO.getLocation());
        existingVacancy.setUpdated(LocalDateTime.now());

        Vacancy vacancyResponse = vacancyRepository.save(existingVacancy);
        return mapper.mapToVacancyDTO(vacancyResponse);
    }

    @Override
    public void deleteVacancy(Long id) {
        Vacancy existingVacancy = findVacancy(id);
        existingVacancy.setDeleted(LocalDateTime.now());
        vacancyRepository.save(existingVacancy);
    }

    @Override
    public VacancyDTO getVacancyDTO(Long id) {
        Vacancy existingVacancy = findVacancy(id);
        increaseView(existingVacancy);
        return mapper.mapToVacancyDTO(existingVacancy);
    }

    private void increaseView(Vacancy vacancy) {
        int view = vacancy.getViewed();
        vacancy.setViewed(++view);
    }

    @Override
    public Page<VacancyDTO> getVacanciesDTO(Long candidateId, String sort, String order, int page, int size) {
        Candidate candidate = candidateService.findCandidate(candidateId);
        Pageable pageRequest = createPageRequest(null, sort, order, page, size);
        return vacancyRepository.findAllByApplicationsCandidateAndDeletedIsNull(candidate, pageRequest)
                .map(mapper::mapToVacancyDTO);
    }

    @Override
    public Page<VacancyDTO> getVacanciesDTO(String sort, String order, int page, int size) {
        validateSearchRequest(null, sort, order, page, size);
        PageRequest pageRequest = createPageRequest(null, sort, order, page, size);
        return vacancyRepository.findByDeletedIsNull(pageRequest)
                .map(mapper::mapToVacancyDTO);
    }

    @Override
    public Page<VacancyDTO> searchVacancies(String query, String sort, String order, int page, int size) {
        PageRequest pageRequest = createPageRequest(query, sort, order, page, size);
        return vacancyRepository.searchByTitleContainingIgnoreCaseAndDeletedIsNull(query, pageRequest)
                .map(mapper::mapToVacancyDTO);
    }

    private void validateSearchRequest(String query, String sort, String order, int page, int size) {
        if (query != null && query.length() > MAX_QUERY_LEN)
            throw new IllegalArgumentException("query can't be more than " + MAX_QUERY_LEN);

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

    @Override
    public Vacancy findVacancy(Long vacancyId) {
        if (vacancyId == null || vacancyId < 0)
            throw new IllegalArgumentException("Invalid parameter vacancyId");
        return vacancyRepository.findByIdAndDeletedIsNull(vacancyId).orElseThrow(
                () -> new ResourceNotFoundException("Vacancy not found"));
    }

    private PageRequest createPageRequest(String query, String sort, String order, int page, int size) {
        validateSearchRequest(query, sort, order, page, size);
        Sort.Direction sortDirection = order.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDirection, sort);
        return PageRequest.of(page, size, sortBy);
    }

}
