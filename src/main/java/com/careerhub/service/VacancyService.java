package com.careerhub.service;

import com.careerhub.dto.VacancyCreateDTO;
import com.careerhub.dto.VacancyUpdateDTO;
import com.careerhub.dto.VacancyDTO;
import com.careerhub.model.Vacancy;
import org.springframework.data.domain.Page;


public interface VacancyService {
    VacancyDTO createVacancy(VacancyCreateDTO vacancyCreateDTO);

    VacancyDTO updateVacancy(Long id, VacancyUpdateDTO vacancyUpdateDTO);

    Vacancy updateVacancy(Vacancy vacancy);

    void deleteVacancy(Long id);

    VacancyDTO getVacancyById(Long id);

    Page<VacancyDTO> getVacancies(String sortBy, String order, int page, int size);

    Page<VacancyDTO> searchVacancies(String query, String sortBy, String order, int page, int size);

    Vacancy findVacancy(Long id);

    void refreshApplied(Vacancy vacancy);

    boolean isExist(Long vacancyId);

}
