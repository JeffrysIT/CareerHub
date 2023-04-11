package com.careerhub.service.impl;

import com.careerhub.dto.VacancyRequestDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceAlreadyExistException;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Vacancy;
import com.careerhub.repository.VacancyRepository;
import com.careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
public class VacancyServiceImpl implements VacancyService {

    private final VacancyRepository vacancyRepository;

    private final MapStructMapper mapper;

    @Autowired
    public VacancyServiceImpl(VacancyRepository vacancyRepository, MapStructMapper mapper) {
        this.vacancyRepository = vacancyRepository;
        this.mapper = mapper;
    }

    @Override
    public Vacancy createVacancy(VacancyRequestDTO vacancyDto) {
        Vacancy vacancy = mapper.vacancyRequestDTOtoVacancy(vacancyDto);
        if (vacancyRepository.existsById(vacancy.getId())) {
            throw new ResourceAlreadyExistException("Vacancy with id: " + vacancy.getId() + " already exist");
        }
        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy updateVacancy(Long id, VacancyRequestDTO vacancyRequestDTO) {
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

        return vacancyRepository.save(vacancy);
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
    public Vacancy getVacancyById(Long id) {
        Vacancy vacancy = vacancyRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Vacancy not found by provided id: " + id));
        if (vacancy.getDeleted() != null) {
            throw new ResourceNotFoundException("Vacancy not found by provided id: " + id);
        }
        return vacancy;
    }
}
