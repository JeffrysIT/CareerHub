package com.careerhub.service;

import com.careerhub.model.Vacancy;

public interface VacancyService {
    Vacancy createVacancy(Vacancy vacancy);

    Vacancy updateVacancy(Vacancy vacancy);

    void deleteVacancy(Long id);

    Vacancy getVacancyById(Long id);
}
