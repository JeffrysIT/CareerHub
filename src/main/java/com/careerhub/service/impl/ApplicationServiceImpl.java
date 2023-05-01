package com.careerhub.service.impl;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.dto.ApplicationUpdateDTO;
import com.careerhub.dto.mapper.MapStructMapper;
import com.careerhub.exception.ResourceNotFoundException;
import com.careerhub.model.*;
import com.careerhub.repository.ApplicationRepository;
import com.careerhub.service.ApplicationService;
import com.careerhub.service.ResumeService;
import com.careerhub.service.CandidateService;
import com.careerhub.service.VacancyService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {
    private final ResumeService resumeService;
    private final VacancyService vacancyService;
    private final CandidateService candidateService;
    private final ApplicationRepository applicationRepository;
    private final MapStructMapper mapper;

    private final List<String> SORT_FIELDS = List.of(
            "created"
    );

    public ApplicationServiceImpl(
            ApplicationRepository applicationRepository,
            ResumeService resumeService,
            VacancyService vacancyService,
            CandidateService candidateService,
            MapStructMapper mapper
    ) {
        this.applicationRepository = applicationRepository;
        this.resumeService = resumeService;
        this.vacancyService = vacancyService;
        this.candidateService = candidateService;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ApplicationDTO createApplication(
            Long vacancyId, Long candidateId, ApplicationCreateDTO applicationCreateDTO, MultipartFile file) {
        Application application = mapper.mapToApplicationEntity(applicationCreateDTO);

        Vacancy vacancy = vacancyService.findVacancy(vacancyId);
        refreshApplied(vacancy);
        application.setVacancy(vacancy);

        Candidate candidate = candidateService.findCandidate(candidateId);
        application.setCandidate(candidate);

        Long resumeId = (file != null)
                ? resumeService.upload(file, candidateId).getId()
                : applicationCreateDTO.getResumeId();
        Resume resume = resumeService.findResume(resumeId);
        application.setResume(resume);

        application.setCreated(LocalDateTime.now());
        application.setStatus(ApplicationStatus.CONSIDERATION);

        Application savedApplication = applicationRepository.save(application);
        return mapper.mapToApplicationDTO(savedApplication);
    }

    private void refreshApplied(Vacancy vacancy) {
        int applied = (vacancy.getApplications() != null) ? vacancy.getApplications().size() : 0;
        vacancy.setApplied(applied);
    }

    @Override
    @Transactional
    public ApplicationDTO updateApplication(Long vacancyId, Long candidateId, Long applicationId,
                                            ApplicationUpdateDTO applicationUpdateDTO, MultipartFile file) {

        Application existingApplication = (vacancyId != null)
                ? findApplication(vacancyId, candidateId, applicationId)
                : findApplication(candidateId, applicationId);

        Long resumeId = (file != null)
                ? resumeService.upload(file, candidateId).getId()
                : applicationUpdateDTO.getResumeId();
        Resume resume = resumeService.findResume(resumeId);
        existingApplication.setResume(resume);

        String coverLetter = applicationUpdateDTO.getCoverLetter();
        existingApplication.setCoverLetter(coverLetter);

        existingApplication.setUpdated(LocalDateTime.now());
        Application savedApplication = applicationRepository.save(existingApplication);

        return mapper.mapToApplicationDTO(savedApplication);
    }

    @Override
    public ApplicationDTO updateApplication(Long candidateId, Long applicationId, ApplicationUpdateDTO applicationUpdateDTO, MultipartFile file) {
        return updateApplication(null, candidateId, applicationId, applicationUpdateDTO, file);
    }

    private Application findApplication(Long vacancyId, Long candidateId, Long applicationId) {
        if (vacancyId == null || vacancyId < 0)
            throw new IllegalArgumentException("Invalid parameter vacancyId");

        if (candidateId == null || candidateId < 0)
            throw new IllegalArgumentException("Invalid parameter candidateId");

        if (applicationId == null || applicationId < 0)
            throw new IllegalArgumentException("Invalid parameter applicationId");

        Application existingApplication = applicationRepository
                .findByVacancyIdAndCandidateIdAndIdAndDeletedIsNull(vacancyId, candidateId, applicationId);
        if (existingApplication == null)
            throw new ResourceNotFoundException("Application not found");

        return existingApplication;
    }

    @Override
    public void deleteApplication(Long vacancyId, Long candidateId, Long applicationId) {
        Application existingApplication = (vacancyId != null)
                ? findApplication(vacancyId, candidateId, applicationId)
                : findApplication(candidateId, applicationId);

        existingApplication.setDeleted(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }

    @Override
    public void deleteApplication(Long candidateId, Long applicationId) {
        deleteApplication(null, candidateId, applicationId);
    }

    @Override
    public ApplicationDTO getApplicationDTO(Long vacancyId, Long candidateId, Long applicationId) {
        Application existingApplication = findApplication(vacancyId, candidateId, applicationId);
        return mapper.mapToApplicationDTO(existingApplication);
    }

    @Override
    public ApplicationDTO getApplicationDTO(Long candidateId, Long applicationId) {
        Application existingApplication = findApplication(candidateId, applicationId);
        return mapper.mapToApplicationDTO(existingApplication);
    }

    private Application findApplication(Long candidateId, Long applicationId) {
        if (candidateId == null || candidateId < 0)
            throw new IllegalArgumentException("Invalid parameter candidateId");

        if (applicationId == null || applicationId < 0)
            throw new IllegalArgumentException("Invalid parameter applicationId");

        Application existingApplication = applicationRepository
                .findByCandidateIdAndIdAndDeletedIsNull(candidateId, applicationId);
        if (existingApplication == null)
            throw new ResourceNotFoundException("Application not found");

        return existingApplication;
    }

    @Override
    public Page<ApplicationDTO> getApplicationDTOPage(
            Long vacancyId, String status, String sort, String order, int page, int size
    ) {
        Vacancy vacancy = vacancyService.findVacancy(vacancyId);
        Pageable pageRequest = createPageRequest(sort, order, page, size);

        if (status != null) {
            String validatedStatus = mapStatus(status).name();
            return applicationRepository.findAllByVacancyAndStatusAndDeletedIsNull(vacancy, validatedStatus, pageRequest)
                    .map(mapper::mapToApplicationDTO);
        }

        return applicationRepository.findAllByVacancyAndDeletedIsNull(vacancy, pageRequest)
                .map(mapper::mapToApplicationDTO);
    }

    @Override
    public Page<ApplicationDTO> getCandidateApplicationDTOPage(
            Long candidateId, String status, String sort, String order, int page, int size
    ) {
        Candidate existingCandidate = candidateService.findCandidate(candidateId);
        Pageable pageRequest = createPageRequest(sort, order, page, size);

        if (status != null) {
            String validatedStatus = mapStatus(status).name();
            return applicationRepository.findAllByCandidateAndStatusAndDeletedIsNull(existingCandidate, validatedStatus, pageRequest)
                    .map(mapper::mapToApplicationDTO);
        }

        return applicationRepository.findAllByCandidateAndDeletedIsNull(existingCandidate, pageRequest)
                .map(mapper::mapToApplicationDTO);
    }

    private void validateRequestParameters(String sort, String order, int page, int size
    ) {
        if (sort != null && !SORT_FIELDS.contains(sort))
            throw new IllegalArgumentException("invalid sort type");

        if (!Sort.Direction.ASC.name().equalsIgnoreCase(order) &&
                !Sort.Direction.DESC.name().equalsIgnoreCase(order))
            throw new IllegalArgumentException("invalid order type");

        if (page < 0)
            throw new IllegalArgumentException("invalid page size");

        if (size < 0)
            throw new IllegalArgumentException("invalid size");
    }

    private PageRequest createPageRequest(String sort, String order, int page, int size) {
        validateRequestParameters(sort, order, page, size);
        Sort.Direction sortDir = order.equalsIgnoreCase(Sort.Direction.ASC.name())
                ? Sort.Direction.ASC
                : Sort.Direction.DESC;
        Sort sortBy = Sort.by(sortDir, sort);
        return PageRequest.of(page, size, sortBy);
    }

    @Override
    public void changeStatus(Long vacancyId, Long candidateId, Long applicationId, String status) {
        ApplicationStatus applicationStatus = mapStatus(status);
        Application existingApplication = findApplication(vacancyId, candidateId, applicationId);
        existingApplication.setStatus(applicationStatus);
        existingApplication.setUpdated(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }

    private ApplicationStatus mapStatus(String status) {
        try {
            return ApplicationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status value");
        }
    }

}
