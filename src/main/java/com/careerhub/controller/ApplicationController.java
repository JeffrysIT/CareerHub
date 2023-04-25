package com.careerhub.controller;

import com.careerhub.dto.ApplicationCreateDTO;
import com.careerhub.dto.ApplicationDTO;
import com.careerhub.dto.ApplicationUpdateDTO;
import com.careerhub.dto.VacancyDTO;
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



}
