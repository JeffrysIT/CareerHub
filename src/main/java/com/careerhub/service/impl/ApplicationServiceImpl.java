package com.careerhub.service.impl;

import com.careerhub.dto.ApplicationRequestDTO;
import com.careerhub.dto.ApplicationResponseDTO;
import com.careerhub.repository.ApplicationRepository;
import com.careerhub.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;

    @Autowired
    public ApplicationServiceImpl(ApplicationRepository applicationRepository) {
        this.applicationRepository = applicationRepository;
    }

    @Override
    public ApplicationResponseDTO createApplication(ApplicationRequestDTO applicationRequestDTO) {
        return null;
    }

    @Override
    public ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO applicationRequestDTO) {
        return null;
    }

    @Override
    public void deleteApplication(Long id) {

    }

    @Override
    public ApplicationResponseDTO getApplicationById(Long id) {
        return null;
    }

    @Override
    public void changeStatusToRejected(Long id) {

    }

    @Override
    public void changeStatusToAccepted(Long id) {

    }

    @Override
    public void changeStatusToConsidering(Long id) {

    }

    @Override
    public Page<ApplicationResponseDTO> getApplications(Long vacancyId, String sort, String order, String statusPresent, int page, int size) {
        return null;
    }

    @Override
    public ApplicationResponseDTO createApplication(MultipartFile file, ApplicationRequestDTO applicationRequestDTO) {
        return null;
    }

    @Override
    public Page<ApplicationResponseDTO> getApplicationByUserDetailsId(Long userDetailsId) {
        return null;
    }
}
