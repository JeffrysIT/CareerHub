package com.careerhub.service;

import com.careerhub.dto.VacancyCreateDTO;
import com.careerhub.dto.VacancyUpdateDTO;
import com.careerhub.dto.VacancyDTO;
import com.careerhub.model.Vacancy;
import org.springframework.data.domain.Page;


public interface VacancyService {

    VacancyDTO createVacancy(VacancyCreateDTO vacancyCreateDTO);

    VacancyDTO updateVacancy(Long vacancyId, VacancyUpdateDTO vacancyUpdateDTO);

    void deleteVacancy(Long id);

    VacancyDTO getVacancyDTO(Long id);

    Page<VacancyDTO> getVacanciesDTO(Long candidateId, String sort, String order, int page, int size);

    Page<VacancyDTO> getVacanciesDTO(String sortBy, String order, int page, int size);

    Page<VacancyDTO> searchVacancies(String query, String sortBy, String order, int page, int size);

    Vacancy findVacancy(Long id);

}
