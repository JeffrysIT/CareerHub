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

import java.util.List;

@RestController
@RequestMapping("/api/v1/candidates")
public class CandidateController {

    @Autowired
    private CandidateService candidateService;

    @Autowired
    private ResumeService resumeService;

    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private VacancyService vacancyService;

    @PostMapping
    public ResponseEntity<CandidateDTO> createCandidate(@RequestBody CandidateCreateDTO candidateCreateDTO) {
        CandidateDTO candidateDTO = candidateService.createCandidate(candidateCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(candidateDTO);
    }

    @GetMapping("/{candidateId}")
    public ResponseEntity<CandidateDTO> getCandidate(@PathVariable("candidateId") Long candidateId) {
        CandidateDTO candidateDTO = candidateService.getCandidateDTO(candidateId);
        return ResponseEntity.ok(candidateDTO);
    }

    @PutMapping("/{candidateId}")
    public ResponseEntity<CandidateDTO> updateCandidate(
            @PathVariable("candidateId") Long candidateId,
            @RequestBody CandidateUpdateDTO candidateUpdateDTO
    ) {
        CandidateDTO candidateDTO = candidateService.updateCandidate(candidateId, candidateUpdateDTO);
        return ResponseEntity.ok(candidateDTO);
    }

    @DeleteMapping("/{candidateId}")
    public ResponseEntity<Void> deleteCandidate(@PathVariable("candidateId") Long candidateId) {
        candidateService.deleteCandidate(candidateId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{candidateId}/applications")
    public ResponseEntity<Page<ApplicationDTO>> getCandidateApplications(
            @PathVariable("candidateId") Long candidateId,
            @RequestParam(name = "status", defaultValue = "CONSIDERING") String status,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<ApplicationDTO> applicationDTOPage =
                applicationService.getCandidateApplicationDTOPage(candidateId, status, sort, order, page, size);
        return ResponseEntity.ok(applicationDTOPage);
    }

    @GetMapping("/{candidateId}/applications/{applicationId}")
    public ResponseEntity<ApplicationDTO> getCandidateApplication(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId
    ) {
        ApplicationDTO applicationDTO = applicationService.getApplicationDTO(candidateId, applicationId);
        return ResponseEntity.ok(applicationDTO);
    }

    @PutMapping("/{candidateId}/applications/{applicationId}")
    public ResponseEntity<ApplicationDTO> updateCandidateApplication(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId,
            @RequestBody ApplicationUpdateDTO applicationUpdateDTO,
            @RequestParam(value = "file", required = false) MultipartFile file
    ) {
        ApplicationDTO applicationDTO = applicationService.updateApplication(candidateId, applicationId, applicationUpdateDTO, file);
        return ResponseEntity.ok(applicationDTO);
    }

    @DeleteMapping("/{candidateId}/applications/{applicationId}")
    public ResponseEntity<Void> deleteCandidateApplication(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("applicationId") Long applicationId
    ) {
        applicationService.deleteApplication(candidateId, applicationId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{candidateId}/vacancies")
    public ResponseEntity<Page<VacancyDTO>> getCandidateVacancies(
            @PathVariable("candidateId") Long candidateId,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<VacancyDTO> vacancyDTOPage = vacancyService.getVacanciesDTO(candidateId, sort, order, page, size);
        return ResponseEntity.ok(vacancyDTOPage);
    }

    @GetMapping("/{candidateId}/resumes")
    public ResponseEntity<List<ResumeDTO>> getCandidateResumes(
            @PathVariable("candidateId") Long candidateId,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order
    ) {
        List<ResumeDTO> resumeDTOList = resumeService.getResumeList(candidateId, sort, order);
        return ResponseEntity.ok(resumeDTOList);
    }

    @DeleteMapping("/{candidateId}/resumes/{resumeId}")
    public ResponseEntity<Void> deleteCandidateResume(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("resumeId") Long resumeId
    ) {
        resumeService.delete(candidateId, resumeId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{candidateId}/resumes")
    public ResponseEntity<ResumeDTO> uploadCandidateResume(
            @PathVariable("candidateId") Long candidateId,
            @RequestParam("file") MultipartFile file) {
        ResumeDTO resumeDTO = resumeService.upload(file, candidateId);
        return ResponseEntity.ok(resumeDTO);
    }

    @GetMapping("/{candidateId}/resumes/{resumeId}/download")
    public ResponseEntity<Resource> downloadCandidateResume(
            @PathVariable("candidateId") Long candidateId,
            @PathVariable("resumeId") Long resumeId
    ) {
        Resource resumeResource = resumeService.download(candidateId, resumeId);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resumeResource.getFilename() + "\"")
                .body(resumeResource);
    }

}
