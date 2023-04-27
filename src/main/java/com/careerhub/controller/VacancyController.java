package com.careerhub.controller;

import com.careerhub.dto.*;
import com.careerhub.service.ApplicationService;
import com.careerhub.service.CandidateService;
import com.careerhub.service.ResumeService;
import com.careerhub.service.VacancyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private ResumeService resumeService;

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
        VacancyDTO vacancyDTO = vacancyService.getVacancyDTO(vacancyId);
        return ResponseEntity.ok(vacancyDTO);
    }

    @PostMapping
    public ResponseEntity<VacancyDTO> createVacancy(@RequestBody VacancyCreateDTO vacancyCreateDTO) {
        VacancyDTO vacancyDTO = vacancyService.createVacancy(vacancyCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(vacancyDTO);
    }

    @PutMapping("/{vacancyId}")
    public ResponseEntity<VacancyDTO> updateVacancy(
            @PathVariable("vacancyId") Long vacancyId,
            @RequestBody VacancyUpdateDTO vacancyUpdateDTO
    ) {
        VacancyDTO vacancyDTO = vacancyService.updateVacancy(vacancyId, vacancyUpdateDTO);
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
    public ResponseEntity<Void> deleteVacancy(@PathVariable("vacancyId") Long vacancyId) {
        vacancyService.deleteVacancy(vacancyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{vacancyId}/candidates")
    public ResponseEntity<Page<CandidateDTO>> getVacancyCandidates(
            @PathVariable("vacancyID") Long vacancyId,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size) {
        Page<CandidateDTO> candidateDTOPage = candidateService.getCandidateDTOPage(vacancyId, sort, order, page, size);
        return ResponseEntity.ok(candidateDTOPage);
    }

    @GetMapping("/{vacancyId}/applications")
    public ResponseEntity<Page<ApplicationDTO>> getApplications(
            @PathVariable("vacancyId") Long vacancyId,
            @RequestParam(name = "status", defaultValue = "CONSIDERING") String status,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<ApplicationDTO> applicationDTOPage =
                applicationService.getApplicationDTOPage(vacancyId, status, sort, order, page, size);
        return ResponseEntity.ok(applicationDTOPage);
    }

    @PostMapping("/{vacancyId}/candidates/{candidateId}/applications")
    public ResponseEntity<ApplicationDTO> createApplication(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @RequestBody ApplicationCreateDTO applicationCreateDTO,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        ApplicationDTO applicationDTO =
                applicationService.createApplication(vacancyId, candidateId, applicationCreateDTO, file);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationDTO);
    }

    @PutMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}")
    public ResponseEntity<ApplicationDTO> updateApplication(
            @PathVariable("vacancyId") Long vacancyId,
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId,
            @RequestBody ApplicationUpdateDTO applicationUpdateDTO,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        ApplicationDTO applicationDTO =
                applicationService.updateApplication(vacancyId, candidateId, applicationId, applicationUpdateDTO, file);
        return ResponseEntity.ok(applicationDTO);
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

    @DeleteMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}")
    public ResponseEntity<Void> deleteApplicationFromVacancy(
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
            @RequestBody String status
    ) {
        applicationService.changeStatus(vacancyId, candidateId, applicationId, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{vacancyId}/candidates/{candidateId}/applications/{applicationId}/resumes/{resumeId}/download")
    public ResponseEntity<Resource> downloadResume(@PathVariable Long vacancyId, @PathVariable Long candidateId,
                                                   @PathVariable Long applicationId, @PathVariable Long resumeId
    ) {
        Resource resumeResource = resumeService.download(vacancyId, candidateId, applicationId, resumeId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resumeResource.getFilename() + "\"")
                .body(resumeResource);
    }

}
