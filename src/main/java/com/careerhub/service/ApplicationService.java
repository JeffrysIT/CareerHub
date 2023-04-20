package com.careerhub.service;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.dto.ApplicationUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.web.multipart.MultipartFile;

public interface ApplicationService {

    ApplicationDTO createApplication(ApplicationCreateDTO applicationCreateDTO);

    ApplicationDTO createApplication(MultipartFile file, ApplicationCreateDTO applicationCreateDTO);

    ApplicationDTO updateApplication(Long id, ApplicationUpdateDTO applicationUpdateDTO);

    ApplicationDTO getApplication(Long id);

    Page<ApplicationDTO> getApplications(Long vacancyId, String sort, String order, String statusPresent, int page, int size);

    Page<ApplicationDTO> getApplicationByUserDetailsId(Long userDetailsId);

    void deleteApplication(Long id);

    void changeStatusToRejected(Long id);

    void changeStatusToAccepted(Long id);

    void changeStatusToConsideration(Long id);
}
