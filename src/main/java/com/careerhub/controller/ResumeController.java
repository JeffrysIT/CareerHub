package com.careerhub.controller;

import com.careerhub.dto.ResumeDTO;
import com.careerhub.dto.ResumeUpdateDTO;
import com.careerhub.model.Resume;
import com.careerhub.service.ResumeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/resume")
public class ResumeController {

    @Autowired
    private ResumeService resumeService;



}

