package com.careerhub.service.impl;

import com.careerhub.dto.VacancyCreateDTO;
import com.careerhub.dto.VacancyUpdateDTO;
import com.careerhub.dto.VacancyDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Vacancy;
import com.careerhub.repository.VacancyRepository;
import com.careerhub.service.VacancyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final MapStructMapper mapper;

    private final List<String> SORT_FIELDS =
            List.of("created", "updated", "title", "location", "viewed", "applied");
    public final static int MAX_QUERY_LEN = 1000;

    public VacancyServiceImpl(
            VacancyRepository vacancyRepository,
            MapStructMapper mapper
    ) {
        this.vacancyRepository = vacancyRepository;
        this.mapper = mapper;
    }

    @Override
    public VacancyDTO createVacancy(VacancyCreateDTO vacancyDto) {
        Vacancy vacancy = mapper.mapToVacancyEntity(vacancyDto);
        vacancy.setCreated(LocalDateTime.now());
        Vacancy vacancyResult = vacancyRepository.save(vacancy);
        return mapper.mapToVacancyDTO(vacancyResult);
    }

    @Override
    public VacancyDTO updateVacancy(Long id, VacancyUpdateDTO vacancyUpdateDTO) {
        Vacancy existingVacancy = findVacancy(id);

        existingVacancy.setTitle(vacancyUpdateDTO.getTitle());
        existingVacancy.setDescription(vacancyUpdateDTO.getDescription());
        existingVacancy.setSalary(vacancyUpdateDTO.getSalary());
        existingVacancy.setLocation(vacancyUpdateDTO.getLocation());
        existingVacancy.setUpdated(LocalDateTime.now());

        Vacancy vacancyResponse = vacancyRepository.save(existingVacancy);
        return mapper.mapToVacancyDTO(vacancyResponse);
    }

    @Override
    public Vacancy updateVacancy(Vacancy vacancy) {
        Long vacancyId = vacancy.getId();
        Vacancy existingVacancy = findVacancy(vacancyId);
        existingVacancy = vacancyRepository.save(vacancy);
        return existingVacancy;
    }

    public void refreshApplied(Vacancy vacancy) {
        int applied = 0;
        if (vacancy.getApplications() != null) {
            applied = vacancy.getApplications().size();
        }
        vacancy.setApplied(applied);
    }

    @Override
    public void deleteVacancy(Long id) {
        Vacancy existingVacancy = findVacancy(id);
        existingVacancy.setDeleted(LocalDateTime.now());
        vacancyRepository.save(existingVacancy);
    }

    @Override
    public VacancyDTO getVacancyById(Long id) {
        Vacancy existingVacancy = findVacancy(id);
        increaseView(existingVacancy);
        return mapper.mapToVacancyDTO(existingVacancy);
    }

    private void increaseView(Vacancy vacancy) {
        int view = vacancy.getViewed();
        vacancy.setViewed(++view);
    }

    @Override
    public Page<VacancyDTO> getVacancies(String sort, String order, int page, int size) {
        validateSearchRequest(null, sort, order, page, size);
        PageRequest pageRequest = createPageRequestBy(sort, order, page, size);
        return vacancyRepository.findByDeletedIsNull(pageRequest)
                .map(mapper::mapToVacancyDTO);
    }

    @Override
    public Page<VacancyDTO> searchVacancies(String query, String sort, String order, int page, int size) {
        validateSearchRequest(query, sort, order, page, size);
        PageRequest pageRequest = createPageRequestBy(sort, order, page, size);
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
            throw new IllegalArgumentException("vacancyId can't be null or less than 0");
        return vacancyRepository.findByIdAndDeletedIsNull(vacancyId).orElseThrow(
                () -> new ResourceNotFoundException("Vacancy not found by id: " + vacancyId));
    }

    @Override
    public boolean isExist(Long vacancyId) {
        if (vacancyId == null || vacancyId < 0)
            throw new IllegalArgumentException("vacancy id can't be null or less than 0");
        return vacancyRepository.existsById(vacancyId);
    }

    private PageRequest createPageRequestBy(String sortBy, String order, int page, int size) {
        Sort.Direction orderBy = order.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(orderBy, sortBy);
        return PageRequest.of(page, size, sort);
    }

}
