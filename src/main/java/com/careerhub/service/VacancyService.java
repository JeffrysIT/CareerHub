package com.careerhub.service;

import com.careerhub.dto.VacancyUpdateDTO;
import com.careerhub.dto.VacancyDTO;
import com.careerhub.model.Vacancy;
import org.springframework.data.domain.Page;


public interface VacancyService {
    VacancyDTO createVacancy(VacancyUpdateDTO vacancy);

    VacancyDTO updateVacancy(Long id, VacancyUpdateDTO vacancyUpdateDTO);

    void deleteVacancy(Long id);

    VacancyDTO getVacancyById(Long id);

    VacancyDTO addApplicationToVacancy(Long vacancyId, Long applicationId);

    Page<VacancyDTO> getVacancies(String sortBy, String order, int page, int size);

    Page<VacancyDTO> searchVacancies(String query, String sortBy, String order, int page, int size);

    Vacancy findVacancy(Long id);

}
