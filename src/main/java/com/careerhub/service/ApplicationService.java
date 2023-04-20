package com.careerhub.service;

import com.careerhub.dto.ApplicationRequestDTO;
import com.careerhub.dto.ApplicationResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {
    ApplicationResponseDTO createApplication(ApplicationRequestDTO applicationRequestDTO);

    ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO applicationRequestDTO);

    void deleteApplication(Long id);

    ApplicationResponseDTO getApplicationById(Long id);

    void changeStatusToRejected(Long id);

    void changeStatusToAccepted(Long id);

    void changeStatusToConsidering(Long id);

    Page<ApplicationResponseDTO> getApplications(Long vacancyId, String sort, String order, String statusPresent, int page, int size);

    ApplicationResponseDTO createApplication(MultipartFile file, ApplicationRequestDTO applicationRequestDTO);

    Page<ApplicationResponseDTO> getApplicationByUserDetailsId(Long userDetailsId);

}
