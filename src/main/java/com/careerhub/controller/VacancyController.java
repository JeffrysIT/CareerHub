package com.careerhub.controller;

import com.careerhub.dto.*;
import com.careerhub.service.ApplicationService;
import com.careerhub.service.CandidateService;
import com.careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/v1/vacancies")
public class VacancyController {

    @Autowired
    private VacancyService vacancyService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private CandidateService candidateService;

    @GetMapping
    public ResponseEntity<Page<VacancyDTO>> getVacancies(
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<VacancyDTO> vacancyDTOPage = vacancyService.getVacanciesDTO(sort, order, page, size);
        return ResponseEntity.ok(vacancyDTOPage);
    }

    @GetMapping("/{vacancyId}")
    public ResponseEntity<VacancyDTO> getVacancyById(@PathVariable("vacancyId") Long vacancyId) {
        VacancyDTO vacancyDTO = vacancyService.getVacancyDTOById(vacancyId);
        return ResponseEntity.ok(vacancyDTO);
    }

    @GetMapping("/search")
    public ResponseEntity<Page<VacancyDTO>> searchVacancies(
            @RequestParam(name = "query") String query,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<VacancyDTO> vacancyDTOPage = vacancyService.searchVacancies(query, sort, order, page, size);
        return ResponseEntity.ok(vacancyDTOPage);
    }

    @DeleteMapping("/{vacancyId}")
    public ResponseEntity<Void> deleteVacancy(
            @PathVariable("vacancyId") Long vacancyId
    ) {
        vacancyService.deleteVacancy(vacancyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{vacancyId}/applications")
    ResponseEntity<Page<ApplicationDTO>> getApplications(
            @PathVariable("vacancyId") Long vacancyId,
            @RequestParam(name = "statusPresent", defaultValue = "CONSIDERING") String statusPresent,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<ApplicationDTO> applicationDTOPage =
                applicationService.getApplicationDTOPage(vacancyId, statusPresent, sort, order, page, size);
        return ResponseEntity.ok(applicationDTOPage);
    }

    @PostMapping("/{vacancyId}/candidates/{candidateId}/applications")
    public ResponseEntity<ApplicationDTO> createApplication(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @RequestBody ApplicationCreateDTO applicationCreateDTO
    ) {
        ApplicationDTO applicationDTO =
                applicationService.createApplication(vacancyId, candidateId, applicationCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationDTO);
    }

    @PostMapping("/{vacancyId}/candidates/{candidateId}/applications/upload")
    public ResponseEntity<ApplicationDTO> createApplicationWithResume(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @RequestParam("file") MultipartFile file,
            @RequestBody ApplicationCreateDTO applicationCreateDTO
    ) {
        ApplicationDTO applicationDTO =
                applicationService.createApplication(vacancyId, candidateId, file, applicationCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationDTO);
    }

    @PutMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}")
    public ResponseEntity<ApplicationDTO> updateApplication(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId,
            @RequestBody ApplicationUpdateDTO applicationUpdateDTO
    ) {
        ApplicationDTO applicationDTO =
                applicationService.updateApplication(vacancyId, candidateId, applicationId, applicationUpdateDTO);
        return ResponseEntity.ok(applicationDTO);
    }

    @PutMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}/upload")
    public ResponseEntity<ApplicationDTO> updateApplicationWithResume(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId,
            @RequestParam("file") MultipartFile file,
            @RequestBody ApplicationUpdateDTO applicationUpdateDTO
    ) {
        ApplicationDTO applicationDTO =
                applicationService.updateApplication(vacancyId, candidateId, applicationId, file, applicationUpdateDTO);
        return ResponseEntity.ok(applicationDTO);
    }

    @GetMapping("/{vacancyId}/candidates")
    public ResponseEntity<Page<CandidateDTO>> getCandidates(@PathVariable("vacancyID") Long vacancyId) {
        Page<CandidateDTO> candidateDTOPage = candidateService.getCandidateDTOPage(vacancyId);
        return ResponseEntity.ok(candidateDTOPage);
    }

    @GetMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}")
    public ResponseEntity<ApplicationDTO> getApplication(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId
    ) {
        ApplicationDTO applicationDTO = applicationService.getApplicationDTO(vacancyId, candidateId, applicationId);
        return ResponseEntity.ok(applicationDTO);
    }

    @PutMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}")
    public ResponseEntity<VacancyDTO> addApplicationToVacancy(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId
    ) {
        VacancyDTO vacancyDTO = applicationService.addApplicationToVacancy(vacancyId, candidateId, applicationId);
        return ResponseEntity.ok(vacancyDTO);
    }

    @DeleteMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}")
    public ResponseEntity<Void> removeApplicationFromVacancy(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId
    ) {
        applicationService.deleteApplication(vacancyId, candidateId, applicationId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}")
    public ResponseEntity<Void> changeApplicationStatus(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId,
            @RequestBody String status) {
        applicationService.changeStatus(vacancyId, candidateId, applicationId, status);
        return ResponseEntity.ok().build();
    }

}
