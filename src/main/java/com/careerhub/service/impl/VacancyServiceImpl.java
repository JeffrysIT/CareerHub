package com.careerhub.service.impl;

import com.careerhub.dto.VacancyRequestDTO;
import com.careerhub.dto.VacancyResponseDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceAlreadyExistException;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.UserDetails;
import com.careerhub.model.Vacancy;
import com.careerhub.repository.VacancyRepository;
import com.careerhub.service.UserDetailsService;
import com.careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;
    private final UserDetailsService userDetailsService;

    private final MapStructMapper mapper;

    private final List<String> SORT_FIELDS = List.of("created", "updated", "title", "salary", "location", "viewed", "applied");
    private final int MAX_QUERY_LEN = 1000;


    @Autowired
    public VacancyServiceImpl(
            VacancyRepository vacancyRepository,
            UserDetailsService userDetailsService,
            MapStructMapper mapper
    ) {
        this.vacancyRepository = vacancyRepository;
        this.userDetailsService = userDetailsService;
        this.mapper = mapper;
    }

    @Override
    public VacancyResponseDTO createVacancy(VacancyRequestDTO vacancyDto) {
        Vacancy vacancy = mapper.vacancyRequestDTOtoVacancy(vacancyDto);
        if (vacancyRepository.existsById(vacancy.getId())) {
            throw new ResourceAlreadyExistException("Vacancy with id: " + vacancy.getId() + " already exist");
        }
        vacancy.setCreated(LocalDateTime.now());
        Vacancy vacancyResult = vacancyRepository.save(vacancy);
        return mapper.vacancyToVacancyResponseDTO(vacancyResult);
    }

    @Override
    public VacancyResponseDTO updateVacancy(Long id, VacancyRequestDTO vacancyRequestDTO) {
        Vacancy vacancy = mapper.vacancyRequestDTOtoVacancy(vacancyRequestDTO);
        Vacancy existingVacancy = vacancyRepository.findById(id)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Vacancy not found with id: " + id));
        if (existingVacancy.getDeleted() != null) {
            throw new ResourceNotFoundException("Vacancy not found by id: " + id);
        }

        existingVacancy.setTitle(vacancy.getTitle());
        existingVacancy.setDescription(vacancy.getDescription());
        existingVacancy.setViewed(vacancy.getViewed());
        existingVacancy.setApplicants(vacancy.getApplicants());
        existingVacancy.setSalary(vacancy.getSalary());
        existingVacancy.setLocation(vacancy.getLocation());
        existingVacancy.setUpdated(LocalDateTime.now());
        refreshApplied(existingVacancy);

        Vacancy vacancyResult = vacancyRepository.save(existingVacancy);
        return mapper.vacancyToVacancyResponseDTO(vacancyResult);
    }

    @Override
    public void deleteVacancy(Long id) {
        Vacancy existingVacancy = vacancyRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Vacancy not found by id: " + id));
        if (existingVacancy.getDeleted() != null) {
            throw new ResourceNotFoundException("Vacancy not found by id: " + id);
        } else {
            existingVacancy.setDeleted(LocalDateTime.now());
        }
    }

    @Override
    public VacancyResponseDTO getVacancyById(Long id) {
        Vacancy vacancy = vacancyRepository.findByIdAndDeletedIsNull(id);
        if (vacancy == null || vacancy.getDeleted() != null) {
            throw new ResourceNotFoundException("Vacancy not found by provided id: " + id);
        }
        increaseView(vacancy);
        return mapper.vacancyToVacancyResponseDTO(vacancy);
    }

    @Override
    public VacancyResponseDTO addUserToVacancy(Long vacancyId, Long userId) {
        Vacancy vacancy = vacancyRepository.findByIdAndDeletedIsNull(vacancyId);
        UserDetails user = userDetailsService.getUserDetailsById(userId);
        if (vacancy == null || vacancy.getDeleted() != null) {
            throw new ResourceNotFoundException("Vacancy not found by provided id: " + vacancyId);
        }
        if (user == null) {
            throw new ResourceNotFoundException("User Details not found by provided id: " + userId);
        }

        vacancy.getApplicants().add(user);
        Vacancy vacancyResponse = vacancyRepository.save(vacancy);

        return mapper.vacancyToVacancyResponseDTO(vacancyResponse);
    }

    @Override
    public Page<VacancyResponseDTO> getVacancies(String sort, String order, int page, int size) {
        validateSearchRequest(null, sort, order, page, size);
        PageRequest pageRequest = createPageRequestBy(sort, order, page, size);
        return vacancyRepository.findAllAndDeletedIsNull(pageRequest)
                .map(mapper::vacancyToVacancyResponseDTO);
    }

    @Override
    public Page<VacancyResponseDTO> searchVacancies(String query, String sort, String order, int page, int size) {
        validateSearchRequest(query, sort, order, page, size);
        PageRequest pageRequest = createPageRequestBy(sort, order, page, size);
        return vacancyRepository.searchByTitleContainingIgnoreCaseAndDeletedIsNull(query, pageRequest)
                .map(mapper::vacancyToVacancyResponseDTO);
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

    private PageRequest createPageRequestBy(String sortBy, String order, int page, int size) {
        Sort.Direction orderBy = order.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(orderBy, sortBy);
        return PageRequest.of(page, size, sort);
    }

    private void increaseView(Vacancy vacancy) {
        int view = vacancy.getViewed();
        vacancy.setViewed(++view);
    }

    private void refreshApplied(Vacancy vacancy) {
        int applied = vacancy.getApplicants().size();
        vacancy.setApplied(applied);
    }

}
