package com.careerhub.service.impl;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.dto.ApplicationUpdateDTO;
import com.careerhub.dto.VacancyDTO;
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
    public ApplicationDTO createApplication(
            Long vacancyId, Long candidateId, ApplicationCreateDTO applicationCreateDTO) {
        return createApplication(vacancyId, candidateId, null, applicationCreateDTO);
    }

    @Override
    @Transactional
    public ApplicationDTO createApplication(
            Long vacancyId, Long candidateId, MultipartFile file, ApplicationCreateDTO applicationCreateDTO) {
        Candidate candidate = candidateService.findCandidate(candidateId);

        Vacancy vacancy = vacancyService.findVacancy(vacancyId);

        Long resumeId = file != null ? resumeService.upload(file, candidateId) : applicationCreateDTO.getResumeId();
        Resume resume = resumeService.findResume(resumeId);

        Application application = mapper.mapToApplicationEntity(applicationCreateDTO);
        application.setResume(resume);
        application.setCandidate(candidate);
        application.setVacancy(vacancy);
        application.setCreated(LocalDateTime.now());
        application.setStatus(ApplicationStatus.CONSIDERATION);

        Application savedApplication = applicationRepository.save(application);
        return mapper.mapToApplicationDTO(savedApplication);
    }

    @Override
    public ApplicationDTO updateApplication(
            Long vacancyId, Long candidateId, Long applicationId, ApplicationUpdateDTO applicationUpdateDTO) {
        return updateApplication(vacancyId, candidateId, applicationId, null, applicationUpdateDTO);
    }

    @Override
    public ApplicationDTO updateApplication(Long vacancyId, Long candidateId, Long applicationId,
                                            MultipartFile file, ApplicationUpdateDTO applicationUpdateDTO) {

        Application existingApplication = findApplication(applicationId);

        Long resumeId = file != null ? resumeService.upload(file, candidateId) : applicationUpdateDTO.getResumeId();
        Resume resume = resumeService.findResume(resumeId);

        existingApplication.setCoverLetter(applicationUpdateDTO.getCoverLetter());
        existingApplication.setResume(resume);
        existingApplication.setUpdated(LocalDateTime.now());

        return mapper.mapToApplicationDTO(existingApplication);
    }

    @Override
    public Application findApplication(Long id) {
        if (id == null || id < 0)
            throw new IllegalArgumentException("id can't be null or less than 0");

        Application existingApplication = applicationRepository.findByIdAndDeletedIsNull(id);
        if (existingApplication == null || existingApplication.getDeleted() != null)
            throw new ResourceNotFoundException("Application not found by provided id: " + id);

        return existingApplication;
    }

    @Override
    public VacancyDTO addApplicationToVacancy(Long vacancyId, Long candidateId, Long applicationId) {
        Application application = findApplication(applicationId);
        Vacancy vacancy = vacancyService.findVacancy(vacancyId);
        vacancy.getApplications().add(application);
        vacancyService.refreshApplied(vacancy);
        Vacancy vacancyResponse = vacancyService.updateVacancy(vacancy);
        return mapper.mapToVacancyDTO(vacancyResponse);
    }

    @Override
    public void deleteApplication(Long vacancyId, Long candidateId, Long applicationId) {
        Application existingApplication = findApplication(vacancyId);
        existingApplication.setDeleted(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }

    @Override
    public ApplicationDTO getApplicationDTO(Long vacancyId, Long candidateId, Long applicationId) {
        Application existingApplication = findApplication(applicationId);
        return mapper.mapToApplicationDTO(existingApplication);
    }

    @Override
    public Page<ApplicationDTO> getApplicationDTOPage(
            Long vacancyId, String statusPresent, String sort, String order, int page, int size
    ) {
        Vacancy vacancy = vacancyService.findVacancy(vacancyId);
        validateRequestParameters(sort, order, statusPresent, page, size);
        Pageable pageRequest = createPageRequestBy(sort, order, page, size);

        if (statusPresent != null) {
            validateApplicationStatus(statusPresent);
            return applicationRepository.findAllByVacancyAndStatusAndDeletedIsNull(vacancy, statusPresent, pageRequest)
                    .map(mapper::mapToApplicationDTO);
        }

        return applicationRepository.findAllByVacancyIdAndDeletedIsNull(vacancyId, pageRequest)
                .map(mapper::mapToApplicationDTO);
    }

    private void validateApplicationStatus(String statusPresent) {
        try {
            ApplicationStatus.valueOf(statusPresent.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status present incorrect");
        }
    }

    private void validateRequestParameters(String sort, String order, String statusPresent, int page, int size
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

    private PageRequest createPageRequestBy(String sortBy, String order, int page, int size) {
        Sort.Direction orderBy = order.equalsIgnoreCase("ASC") ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(orderBy, sortBy);
        return PageRequest.of(page, size, sort);
    }

    @Override
    public Page<ApplicationDTO> getApplicationsByCandidate(Long candidateId) {
        throw new ResourceNotFoundException("not implemented");
    }

    @Override
    public void changeStatus(Long vacancyId, Long candidateId, Long applicationId, String status) {
        ApplicationStatus applicationStatus = mapStatus(status);
        switch (applicationStatus) {
            case ACCEPTED:
                changeStatusToAccepted(applicationId);
                break;
            case REJECTED:
                changeStatusToRejected(applicationId);
                break;
            case CONSIDERATION:
                changeStatusToConsideration(applicationId);
                break;
            default:
                throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    private ApplicationStatus mapStatus(String status) {
        try {
            return ApplicationStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid status: " + status);
        }
    }

    private void changeStatusToAccepted(Long id) {
        Application existingApplication = findApplication(id);
        existingApplication.setStatus(ApplicationStatus.ACCEPTED);
        existingApplication.setUpdated(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }

    private void changeStatusToRejected(Long id) {
        Application existingApplication = findApplication(id);
        existingApplication.setStatus(ApplicationStatus.REJECTED);
        existingApplication.setUpdated(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }

    private void changeStatusToConsideration(Long id) {
        Application existingApplication = findApplication(id);
        existingApplication.setStatus(ApplicationStatus.CONSIDERATION);
        existingApplication.setUpdated(LocalDateTime.now());
        applicationRepository.save(existingApplication);
    }
}
