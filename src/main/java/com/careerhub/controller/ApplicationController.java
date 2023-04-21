package com.careerhub.controller;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.dto.ApplicationUpdateDTO;
import com.careerhub.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getApplicationById(@PathVariable Long id) {
        ApplicationDTO applicationDTO = applicationService.getApplication(id);
        return ResponseEntity.ok(applicationDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationDTO> updateApplication(
            @PathVariable Long id,
            @RequestBody ApplicationUpdateDTO applicationUpdateDTO
    ) {
        ApplicationDTO applicationDTO = applicationService.updateApplication(id, applicationUpdateDTO);
        return ResponseEntity.ok(applicationDTO);
    }

    @PutMapping("/{id}/upload")
    public ResponseEntity<ApplicationDTO> updateApplicationWithUploadResume(
            @RequestParam("file") MultipartFile file,
            @PathVariable Long id,
            @RequestBody ApplicationUpdateDTO applicationUpdateDTO
    ) {
        ApplicationDTO applicationDTO = applicationService.updateApplication(file, id, applicationUpdateDTO);
        return ResponseEntity.ok(applicationDTO);
    }

    @PostMapping
    public ResponseEntity<ApplicationDTO> createApplication(@RequestBody ApplicationCreateDTO applicationCreateDTO) {
        ApplicationDTO applicationDTO = applicationService.createApplication(applicationCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationDTO);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApplicationDTO> createApplicationWithUploadResume(
            @RequestParam("file") MultipartFile file, @RequestBody ApplicationCreateDTO applicationCreateDTO) {
        ApplicationDTO applicationDTO = applicationService.createApplication(file, applicationCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(applicationDTO);
    }

    @GetMapping("/user-details/{userDetailsId}")
    public ResponseEntity<Page<ApplicationDTO>> getAllUserDetailsApplications(
            @RequestParam("userDetailsId") Long userDetailsId) {
        Page<ApplicationDTO> applicationResponseDTOPage = applicationService.getApplicationsByUserDetailsId(userDetailsId);
        return ResponseEntity.ok(applicationResponseDTOPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeApplicationStatus(@PathVariable Long id, @RequestBody String status) {
        applicationService.changeStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vacancy/{vacancyId}")
    ResponseEntity<Page<ApplicationDTO>> getApplications(
            @PathVariable("vacancyId") Long vacancyId,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "statusPresent", defaultValue = "CONSIDERING") String statusPresent,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<ApplicationDTO> applicationResponseDTOPage = applicationService.getApplications(vacancyId, sort, order, statusPresent, page, size);
        return ResponseEntity.ok(applicationResponseDTOPage);
    }

}
