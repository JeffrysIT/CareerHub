package com.careerhub.controller;

import com.careerhub.model.Resume;
import com.careerhub.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;

    @PostMapping("/upload")
    public ResponseEntity<Void> uploadResume(@RequestParam("file") MultipartFile file,
                                             @RequestParam("userDetailsId") Long userDetailsId) throws IOException {

        resumeService.upload(file, userDetailsId);
        return ResponseEntity.ok().build();
    }

}

