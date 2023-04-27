package com.careerhub.service;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.dto.ApplicationUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {

    ApplicationDTO createApplication(Long vacancyId, Long candidateId, ApplicationCreateDTO applicationCreateDTO, MultipartFile file);

    ApplicationDTO updateApplication(Long vacancyId, Long candidateId, Long applicationId, ApplicationUpdateDTO applicationUpdateDTO, MultipartFile file);

    ApplicationDTO updateApplication(Long candidateId, Long applicationId, ApplicationUpdateDTO applicationUpdateDTO, MultipartFile file);

    ApplicationDTO getApplicationDTO(Long vacancyId, Long candidateId, Long applicationId);

    ApplicationDTO getApplicationDTO(Long candidateId, Long applicationId);

    Page<ApplicationDTO> getApplicationDTOPage(Long vacancyId, String status, String sort, String order, int page, int size);

    Page<ApplicationDTO> getCandidateApplicationDTOPage(Long candidateId, String status, String sort, String order, int page, int size);

    void deleteApplication(Long vacancyId, Long candidateId, Long applicationId);

    void deleteApplication(Long candidateId, Long applicationId);

    void changeStatus(Long vacancyId, Long candidateId, Long applicationId, String status);


}
