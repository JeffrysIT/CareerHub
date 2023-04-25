package com.careerhub.service;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.dto.ApplicationUpdateDTO;
import com.careerhub.dto.VacancyDTO;
import com.careerhub.model.Application;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {

    ApplicationDTO createApplication(Long vacancyId, Long candidateId, ApplicationCreateDTO applicationCreateDTO);

    ApplicationDTO createApplication(Long vacancyId, Long candidateId, MultipartFile file, ApplicationCreateDTO applicationCreateDTO);

    ApplicationDTO updateApplication(Long vacancyId, Long candidateId, Long applicationId, ApplicationUpdateDTO applicationUpdateDTO);

    ApplicationDTO updateApplication(Long vacancyId, Long candidateId, Long applicationId, MultipartFile file, ApplicationUpdateDTO applicationUpdateDTO);

    ApplicationDTO getApplicationDTO(Long vacancyId, Long candidateId, Long applicationId);

    Page<ApplicationDTO> getApplicationDTOPage(Long vacancyId, String statusPresent, String sort, String order, int page, int size);

    Page<ApplicationDTO> getApplicationsByCandidate(Long candidateId);

    VacancyDTO addApplicationToVacancy(Long vacancyId, Long candidateId, Long applicationId);

    Application findApplication(Long applicationId);

    void deleteApplication(Long vacancyId, Long candidateId, Long applicationId);

    void changeStatus(Long vacancyId, Long candidateId, Long applicationId, String status);

}
