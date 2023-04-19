package com.careerhub.controller;

import com.careerhub.dto.ApplicationRequestDTO;
import com.careerhub.dto.ApplicationResponseDTO;
import com.careerhub.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponseDTO> createApplication(@RequestBody ApplicationRequestDTO applicationRequestDTO) {
        ApplicationResponseDTO savedApplication = applicationService.createApplication(applicationRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedApplication);
    }
}
