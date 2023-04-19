package com.careerhub.service;

import com.careerhub.dto.ApplicationRequestDTO;
import com.careerhub.dto.ApplicationResponseDTO;
import org.springframework.data.domain.Page;

public interface ApplicationService {
    ApplicationResponseDTO createApplication(ApplicationRequestDTO applicationRequestDTO);

    ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO applicationRequestDTO);

    void deleteApplication(Long id);

    ApplicationResponseDTO getApplicationById(Long id);

    void changeStatusToRejected(Long id);

    void changeStatusToAccepted(Long id);

    void changeStatusToConsidering(Long id);

    Page<ApplicationResponseDTO> getApplications(String sort, String order, String statusPresent, int page, int size);
}
