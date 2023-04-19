package com.careerhub.controller;


import com.careerhub.dto.ApplicationRequestDTO;
import com.careerhub.dto.ApplicationResponseDTO;
import com.careerhub.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/applications")
public class ApplicationAdminController {

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

    @GetMapping
    ResponseEntity<Page<ApplicationResponseDTO>> getApplications(
            @RequestParam(name = "sort", defaultValue = "created") String sort,
            @RequestParam(name = "order", defaultValue = "DESC") String order,
            @RequestParam(name = "statusPresent", defaultValue = "CONSIDERING") String statusPresent,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "size", defaultValue = "10") int size
    ) {
        Page<ApplicationResponseDTO> applicationResponseDTOPage = applicationService.getApplications(sort, order, statusPresent, page, size);
        return ResponseEntity.ok(applicationResponseDTOPage);

    }
}
