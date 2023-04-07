package com.careerhub.service.impl;

import com.careerhub.exception.ResourceAlreadyDeletedException;
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

    @Autowired
    public VacancyServiceImpl(VacancyRepository vacancyRepository) {
        this.vacancyRepository = vacancyRepository;
    }

    @Override
    public Vacancy createVacancy(Vacancy vacancy) {
        if (vacancyRepository.existsById(vacancy.getId())) {
            throw new ResourceAlreadyExistException("Vacancy with id: " + vacancy.getId() + " already exist");
        }
        return vacancyRepository.save(vacancy);
    }

    @Override
    public Vacancy updateVacancy(Vacancy vacancy) {
        Long vacancyId = vacancy.getId();
        Vacancy existingVacancy = vacancyRepository.findById(vacancyId)
                .orElseThrow(()
                        -> new ResourceNotFoundException("Vacancy not found with id: " + vacancyId));
        existingVacancy.setTitle(vacancy.getTitle());
        existingVacancy.setDescription(vacancy.getDescription());
        existingVacancy.setViewed(vacancy.getViewed());
        existingVacancy.setApplicants(vacancy.getApplicants());
        existingVacancy.setUpdated(LocalDateTime.now());
        return vacancyRepository.save(vacancy);
    }

    @Override
    public void deleteVacancy(Long id) {
        Vacancy existingVacancy = vacancyRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Vacancy not found with id: " + id));
        if (existingVacancy.getDeleted() != null) {
            existingVacancy.setDeleted(LocalDateTime.now());
        } else {
            throw new ResourceAlreadyDeletedException("Vacancy with id: " + id + " already deleted");
        }
    }

    @Override
    public Vacancy getVacancyById(Long id) {
        return vacancyRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Vacancy not found by provided id: " + id));
    }
}
