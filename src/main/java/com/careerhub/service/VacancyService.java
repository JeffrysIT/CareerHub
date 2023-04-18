package com.careerhub.service;

import com.careerhub.dto.VacancyRequestDTO;
import com.careerhub.model.Vacancy;
import org.springframework.data.domain.Page;


public interface VacancyService {
    Vacancy createVacancy(VacancyRequestDTO vacancy);

    Vacancy updateVacancy(Long id, VacancyRequestDTO vacancyRequestDTO);

    void deleteVacancy(Long id);

    Vacancy getVacancyById(Long id);

    Vacancy addUserToVacancy(Long vacancyId, Long userId);

    Page<Vacancy> getVacancies(String sortBy, String order, int page, int size);

    Page<Vacancy> searchVacancies(String query, String sortBy, String order, int page, int size);
}
