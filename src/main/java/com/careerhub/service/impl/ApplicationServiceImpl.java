package com.careerhub.service.impl;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceAlreadyExistException;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.Application;
import com.careerhub.model.ApplicationStatus;
import com.careerhub.model.Resume;
import com.careerhub.model.UserDetails;
import com.careerhub.repository.ApplicationRepository;
import com.careerhub.repository.ResumeRepository;
import com.careerhub.repository.UserDetailsRepository;
import com.careerhub.service.ApplicationService;
import com.careerhub.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ResumeService resumeService;

    private final UserDetailsRepository userDetailsRepository;
    private final ApplicationRepository applicationRepository;
    private final ResumeRepository resumeRepository;

    private final MapStructMapper mapper;

    @Autowired
    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            ResumeService resumeService, ResumeRepository resumeRepository, UserDetailsRepository userDetailsRepository, MapStructMapper mapper) {
        this.applicationRepository = applicationRepository;
        this.resumeService = resumeService;
        this.resumeRepository = resumeRepository;
        this.userDetailsRepository = userDetailsRepository;
        this.mapper = mapper;
    }

    @Override
    public ApplicationDTO createApplication(ApplicationCreateDTO applicationCreateDTO) {
        Application application = mapper.applicationCreateDTOtoApplication(applicationCreateDTO);
        if (applicationRepository.existsById(application.getId())) {
            throw new ResourceAlreadyExistException("Application with id: " + application.getId() + " already exist");
        }

        application.setCreated(LocalDateTime.now());
        application.setStatus(ApplicationStatus.CONSIDERATION);

        Application savedApplication = applicationRepository.save(application);
        return mapper.applicationToApplicationDTO(savedApplication);
    }

    @Override
    @Transactional
    public ApplicationDTO createApplication(MultipartFile file, ApplicationCreateDTO applicationCreateDTO) {

        Application application = mapper.applicationCreateDTOtoApplication(applicationCreateDTO);
        Long applicationId = application.getId();
        if (applicationRepository.existsById(applicationId)) {
            throw new ResourceAlreadyExistException("Application with id: " + applicationId + " already exist");
        }


        Long userDetailsId = applicationCreateDTO.getUserDetailsId();
        UserDetails userDetails = userDetailsRepository.findByIdAndDeletedIsNull(userDetailsId);
        if (userDetails == null || userDetails.getDeleted() != null) {
            throw new ResourceNotFoundException("User Details not found by id: " + userDetailsId);
        }


        Long resumeId = resumeService.upload(file, userDetailsId);
        Resume resume = resumeRepository.findById(resumeId).orElseThrow(
                () -> new ResourceNotFoundException("Resume not found by id: " + resumeId));

        application.setUserDetails(userDetails);
        application.setResume(resume);
        application.setCreated(LocalDateTime.now());
        application.setStatus(ApplicationStatus.CONSIDERATION);

        Application savedApplication = applicationRepository.save(application);
        return mapper.applicationToApplicationDTO(savedApplication);
    }

    @Override
    public ApplicationResponseDTO updateApplication(Long id, ApplicationRequestDTO applicationRequestDTO) {

        Application existingApplication = applicationRepository.findByIdAndDeletedIsNull(id);
        if (existingApplication == null || existingApplication.getDeleted() != null) {
            throw new ResourceNotFoundException("Application not found by provided id: " + id);
        }

        Long resumeId = applicationRequestDTO.getResumeId();
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found by id: " + resumeId));

        existingApplication.setCoverLetter(applicationRequestDTO.getCoverLetter());
        existingApplication.setResume(resume);
        existingApplication.setUpdated(LocalDateTime.now());

        return mapper.applicationToApplicationResponseDTO(existingApplication);
    }

    @Override
    public void deleteApplication(Long id) {
        Application existingApplication = applicationRepository.findByIdAndDeletedIsNull(id);
        if (existingApplication == null || existingApplication.getDeleted() != null) {
            throw new ResourceNotFoundException("Application not found by provided id: " + id);
        }
        existingApplication.setDeleted(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }

    @Override
    public ApplicationDTO getApplication(Long id) {
        Application existingApplication = applicationRepository.findByIdAndDeletedIsNull(id);
        if (existingApplication == null || existingApplication.getDeleted() != null) {
            throw new ResourceNotFoundException("Application not found by provided id: " + id);
        }
        return mapper.applicationToApplicationResponseDTO(existingApplication);
    }

    @Override
    public void changeStatusToRejected(Long id) {
        Application existingApplication = applicationRepository.findByIdAndDeletedIsNull(id);
        if (existingApplication == null || existingApplication.getDeleted() != null) {
            throw new ResourceNotFoundException("Application not found by provided id: " + id);
        }
        existingApplication.setStatus(ApplicationStatus.REJECTED);
        existingApplication.setUpdated(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }

    @Override
    public void changeStatusToAccepted(Long id) {
        Application existingApplication = applicationRepository.findByIdAndDeletedIsNull(id);
        if (existingApplication == null || existingApplication.getDeleted() != null) {
            throw new ResourceNotFoundException("Application not found by provided id: " + id);
        }
        existingApplication.setStatus(ApplicationStatus.ACCEPTED);
        existingApplication.setUpdated(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }

    @Override
    public void changeStatusToConsideration(Long id) {
        Application existingApplication = applicationRepository.findByIdAndDeletedIsNull(id);
        if (existingApplication == null || existingApplication.getDeleted() != null) {
            throw new ResourceNotFoundException("Application not found by provided id: " + id);
        }
        existingApplication.setStatus(ApplicationStatus.CONSIDERATION);
        existingApplication.setUpdated(LocalDateTime.now());
        applicationRepository.save(existingApplication);

    }

    @Override
    public Page<ApplicationDTO> getApplications(Long vacancyId, String sort, String order, String statusPresent, int page, int size) {
        return null;
    }

    @Override
    public Page<ApplicationDTO> getApplicationByUserDetailsId(Long userDetailsId) {
        return null;
    }
}
