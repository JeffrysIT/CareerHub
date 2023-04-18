package com.careerhub.service;

import com.careerhub.dto.VacancyRequestDTO;
import com.careerhub.dto.VacancyResponseDTO;
import org.springframework.data.domain.Page;


public interface VacancyService {
    VacancyResponseDTO createVacancy(VacancyRequestDTO vacancy);

    VacancyResponseDTO updateVacancy(Long id, VacancyRequestDTO vacancyRequestDTO);

    void deleteVacancy(Long id);

    VacancyResponseDTO getVacancyById(Long id);

    VacancyResponseDTO addApplicationToVacancy(Long vacancyId, Long applicationId);

    Page<VacancyResponseDTO> getVacancies(String sortBy, String order, int page, int size);

    Page<VacancyResponseDTO> searchVacancies(String query, String sortBy, String order, int page, int size);
}
