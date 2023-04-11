package com.careerhub.service;

import com.careerhub.dto.VacancyRequestDTO;
import com.careerhub.model.Vacancy;

public interface VacancyService {
    Vacancy createVacancy(VacancyRequestDTO vacancy);

    Vacancy updateVacancy(Long id, VacancyRequestDTO vacancyRequestDTO);

    void deleteVacancy(Long id);

    Vacancy getVacancyById(Long id);
}
