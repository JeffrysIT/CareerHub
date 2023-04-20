package com.careerhub.controller;

import com.careerhub.dto.ApplicationRequestDTO;
import com.careerhub.dto.ApplicationResponseDTO;
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
    public ResponseEntity<ApplicationResponseDTO> getApplicationById(@PathVariable Long id) {
        ApplicationResponseDTO application = applicationService.getApplicationById(id);
        return ResponseEntity.ok(application);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApplicationResponseDTO> updateApplication(@PathVariable Long id, @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO updatedApplication = applicationService.updateApplication(id, applicationRequestDTO);
        return ResponseEntity.ok(updatedApplication);
    }

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> createApplication(@RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO savedApplication = applicationService.createApplication(applicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    @PostMapping("/upload")
    public ResponseEntity<ApplicationResponseDTO> createApplicationWithUploadResume(
            @RequestParam("file") MultipartFile file, @RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO savedApplication = applicationService.createApplication(file, applicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }

    @GetMapping("/user-details/{userDetailsId}")
    public ResponseEntity<Page<ApplicationResponseDTO>> getAllUserDetailsApplications(
            @RequestParam("userDetailsId") Long userDetailsId) {
        Page<ApplicationResponseDTO> applicationResponseDTOPage = applicationService.getApplicationByUserDetailsId(userDetailsId);
        return ResponseEntity.ok(applicationResponseDTOPage);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteApplication(@PathVariable Long id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status/reject")
    public ResponseEntity<Void> rejectApplication(@PathVariable Long id) {
        applicationService.changeStatusToRejected(id);
        return ResponseEntity.ok().build();

    }

    @PatchMapping("/{id}/status/accept")
    public ResponseEntity<Void> acceptApplication(@PathVariable Long id) {
        applicationService.changeStatusToAccepted(id);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("{id}/status/considering")
    public ResponseEntity<Void> consideringApplication(@PathVariable Long id) {
        applicationService.changeStatusToConsidering(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/vacancy/{vacancyId}")
    ResponseEntity<Page<ApplicationResponseDTO>> getApplications(
            @PathVariable("vacancyId") Long vacancyId,
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "statusPresent", defaultValue = "CONSIDERING") String statusPresent,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<ApplicationResponseDTO> applicationResponseDTOPage = applicationService.getApplications(vacancyId, sort, order, statusPresent, page, size);
        return ResponseEntity.ok(applicationResponseDTOPage);
    }

}
